package com.example.langchain4jlessons.services;

import com.example.langchain4jlessons.dtos.ChatRecordDto;
import dev.langchain4j.chain.ConversationalRetrievalChain;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ConversationalRetrievalChain conversationalRetrievalChain;

    public ChatService(ConversationalRetrievalChain conversationalRetrievalChain) {
        this.conversationalRetrievalChain = conversationalRetrievalChain;
    }

    public ResponseEntity<Object> sendMessage(ChatRecordDto chatRecordDto) {
        String answer = conversationalRetrievalChain.execute(chatRecordDto.question());
        return ResponseEntity.ok(answer);
    }

}
