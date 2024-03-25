package com.example.langchain4jlessons.services.file;

import com.example.langchain4jlessons.services.astradb.AstraDBConnection;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@Service
public class FileService {

    @Autowired
    private AstraDBConnection astraDBConnection;

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
        astraDBConnection.embeddingStoreIngestor().ingest(cleanedDocument);
    }

    private Document removeWhiteSpace(Document document) {
        String text = document.text();
        text = text.replaceAll("\\s+", " ");
        return new Document(text);
    }
}
