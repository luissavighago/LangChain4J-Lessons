package com.example.langchain4jlessons.services.chat;

import com.example.langchain4jlessons.dtos.ChatRecordDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    public ResponseEntity<Object> sendMessage(ChatRecordDto chatRecordDto, String key) {
        LLMModel llmModel = LLMModelFactory.createLLMService(key);
        String answer = llmModel.conversationalRetrievalChain().execute(chatRecordDto.question());
        return ResponseEntity.ok(answer);
    }

    public ResponseEntity<Object> testMessage(ChatRecordDto chatRecordDto, String key) {
        LLMModel llmModel = LLMModelFactory.createLLMService(key);
        String answer = llmModel.testQuestion(chatRecordDto.question());
        return ResponseEntity.ok(answer);
    }
}
