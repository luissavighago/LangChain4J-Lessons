package com.example.langchain4jlessons.controllers;

import com.example.langchain4jlessons.dtos.ChatRecordDto;
import com.example.langchain4jlessons.services.chat.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/chat")
@RestController
public class ChatController {

    @Autowired
    ChatService chatService;

    @PostMapping("/gpt")
    public ResponseEntity<Object> sendMessageGPT(@RequestBody @Valid ChatRecordDto chatRecordDto){
        return chatService.sendMessage(chatRecordDto, "GPT");
    }

    @PostMapping("/ollama")
    public ResponseEntity<Object> sendMessage(@RequestBody @Valid ChatRecordDto chatRecordDto){
        return chatService.testMessage(chatRecordDto, "OLLAMA");
    }
}
