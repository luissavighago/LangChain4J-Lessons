package com.example.langchain4jlessons.controllers;

import com.example.langchain4jlessons.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/file")
    public ResponseEntity<Object> storeFile() {
        return fileService.storeFile();
    }
}
