package com.example.langchain4jlessons.services.chat;

import com.example.langchain4jlessons.services.astradb.AstraDBConnection;
import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;

public class LLMModelOllama implements LLMModel{

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
                .chatLanguageModel(getOllamaChatModel())
                .retriever(EmbeddingStoreRetriever.from(astraDbConnection.astraDbEmbeddingStore(), astraDbConnection.embeddingModel(),4))
                .promptTemplate(DEFAULT_PROMPT_TEMPLATE)
                .build();
    }

    @Override
    public String testQuestion(String question) {
        return getOllamaChatModel().generate(question);
    }

    private ChatLanguageModel getOllamaChatModel() {
        return OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("llama2")
                .temperature(0.8)
                .format("json")
                .maxRetries(1)
                .build();
    }
}
