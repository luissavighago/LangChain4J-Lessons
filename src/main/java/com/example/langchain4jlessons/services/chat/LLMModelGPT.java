package com.example.langchain4jlessons.services.chat;

import com.example.langchain4jlessons.services.astradb.AstraDBConnection;
import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import org.springframework.stereotype.Component;

@Component
public class LLMModelGPT implements LLMModel {

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

    @Override
    public ConversationalRetrievalChain conversationalRetrievalChain() {
        AstraDBConnection astraDbConnection = new AstraDBConnection();

        return ConversationalRetrievalChain.builder()
                .chatLanguageModel(OpenAiChatModel.withApiKey(System.getenv("GPT_TOKEN")))
                .retriever(EmbeddingStoreRetriever.from(astraDbConnection.astraDbEmbeddingStore(), astraDbConnection.embeddingModel(),4))
                .promptTemplate(DEFAULT_PROMPT_TEMPLATE)
                .build();
    }
}
