package com.example.langchain4jlessons.services.chat;

public class LLMModelFactory {
    public static LLMModel createLLMService(String key) {
        return key.equals("OLLAMA") ? new LLMModelOllama() : new LLMModelGPT();
    }
}
