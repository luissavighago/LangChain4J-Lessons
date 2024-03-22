package com.example.langchain4jlessons.controllers;

import com.example.langchain4jlessons.dtos.ChatRecordDto;
import com.example.langchain4jlessons.services.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    ChatService chatService;

    @PostMapping("/chat")
    public ResponseEntity<Object> sendMessage(@RequestBody @Valid ChatRecordDto chatRecordDto){
        return chatService.sendMessage(chatRecordDto);
    }
}
