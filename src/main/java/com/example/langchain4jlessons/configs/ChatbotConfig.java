package com.example.langchain4jlessons.configs;

import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.cassandra.AstraDbEmbeddingConfiguration;
import dev.langchain4j.store.embedding.cassandra.AstraDbEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatbotConfig {

    @Value("${astradb.token}")
    private String astraDbToken;

    @Value("${astradb.id}")
    private String astraDbID;

    @Value("${astradb.region}")
    private String astraRegion;

    @Value("${gpt.token}")
    private String gptToken;

    private static final PromptTemplate DEFAULT_PROMPT_TEMPLATE = PromptTemplate.from("""
        Você responde perguntas sobre o regulamento de estágios de uma universidade.
        O usuario acessou o site da universidade buscando informações sobre o regulamento.
        Use o conteúdo do regulamento abaixo para responder as perguntas do usuario.
        Se a resposta não for encontrada no regulamento, responda que você não sabe, não tente inventar uma resposta.
        
        Regulamento:
        {{information}}
        
        Pergunta:
        {{question}}
    """);

    @Bean
    public EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }

    @Bean
    public AstraDbEmbeddingStore astraDbEmbeddingStore() {
        return new AstraDbEmbeddingStore(AstraDbEmbeddingConfiguration
                .builder()
                .token(astraDbToken)
                .databaseId(astraDbID)
                .databaseRegion(astraRegion)
                .keyspace("default_keyspace")
                .table("tb_embedding")
                .dimension(600)
                .build());
    }

    @Bean
    public EmbeddingStoreIngestor embeddingStoreIngestor() {
        return EmbeddingStoreIngestor.builder()
                .documentSplitter(DocumentSplitters.recursive(600, 0))
                .embeddingModel(embeddingModel())
                .embeddingStore(astraDbEmbeddingStore())
                .build();
    }

    @Bean
    public ConversationalRetrievalChain conversationalRetrievalChain() {
        return ConversationalRetrievalChain.builder()
                .chatLanguageModel(OpenAiChatModel.withApiKey(gptToken))
                .retriever(EmbeddingStoreRetriever.from(astraDbEmbeddingStore(), embeddingModel()))
                .promptTemplate(DEFAULT_PROMPT_TEMPLATE)
                .build();
    }
}
