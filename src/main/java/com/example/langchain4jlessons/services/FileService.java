package com.example.langchain4jlessons.services;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.annotation.PostConstruct;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@Service
public class FileService {

    private final EmbeddingStoreIngestor embeddingStoreIngestor;

    public FileService(EmbeddingStoreIngestor embeddingStoreIngestor) {
        this.embeddingStoreIngestor = embeddingStoreIngestor;
    }

    public ResponseEntity<Object> storeFile(){
        try{
            ingest();
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(422).body(e.getMessage());
        }
    }

    private static Path toPath(String fileName) {
        try {
            URL fileUrl = FileService.class.getClassLoader().getResource(fileName);
            return Paths.get(fileUrl.toURI());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void ingest() {
        Document document = loadDocument(toPath("regulamento.pdf"), new ApachePdfBoxDocumentParser());
        Document cleanedDocument = removeWhiteSpace(document);
        embeddingStoreIngestor.ingest(cleanedDocument);
    }

    private Document removeWhiteSpace(Document document) {
        String text = document.text();
        text = text.replaceAll("\\s+", " ");
        return new Document(text);
    }
}
