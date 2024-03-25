package com.example.langchain4jlessons.services.chat;

import dev.langchain4j.chain.ConversationalRetrievalChain;
public interface LLMModel {
    public ConversationalRetrievalChain conversationalRetrievalChain();

    public String testQuestion(String question);
}
