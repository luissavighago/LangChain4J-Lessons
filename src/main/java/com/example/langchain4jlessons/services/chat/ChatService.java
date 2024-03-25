package com.example.langchain4jlessons.services.chat;

import com.example.langchain4jlessons.dtos.ChatRecordDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    public ResponseEntity<Object> sendMessageGPT(ChatRecordDto chatRecordDto) {
        LLMModelGPT llmServiceGPT = (LLMModelGPT) LLMModelFactory.createLLMService();
        String answer = llmServiceGPT.conversationalRetrievalChain().execute(chatRecordDto.question());
        return ResponseEntity.ok(answer);
    }

}
