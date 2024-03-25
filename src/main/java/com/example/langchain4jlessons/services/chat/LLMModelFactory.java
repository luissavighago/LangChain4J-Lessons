package com.example.langchain4jlessons.services.chat;

public class LLMModelFactory {
    public static LLMModel createLLMService() {
        return new LLMModelGPT();
    }
}
