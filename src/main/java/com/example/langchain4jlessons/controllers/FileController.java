package com.example.langchain4jlessons.controllers;

import com.example.langchain4jlessons.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/files")
@RestController
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/store")
    public ResponseEntity<Object> storeFile() {
        return fileService.storeFile();
    }
}
