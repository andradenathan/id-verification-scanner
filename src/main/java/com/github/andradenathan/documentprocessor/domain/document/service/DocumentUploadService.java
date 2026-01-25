package com.github.andradenathan.documentprocessor.domain.document.service;

import com.github.andradenathan.documentprocessor.infrastructure.messaging.publisher.DocumentProcessingPublisher;
import com.github.andradenathan.documentprocessor.infrastructure.storage.DocumentStorage;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentUploadService {
  private final DocumentStorage documentStorage;
  private final DocumentProcessingPublisher documentProcessingPublisher;

  public DocumentUploadService(
      DocumentStorage documentStorage, DocumentProcessingPublisher documentProcessingPublisher) {
    this.documentStorage = documentStorage;
    this.documentProcessingPublisher = documentProcessingPublisher;
  }

  public void process(List<MultipartFile> documents) {
    if (documents == null || documents.isEmpty()) {
      throw new IllegalArgumentException("No documents provided for processing");
    }

    for (MultipartFile document : documents) {
      UUID documentId = UUID.randomUUID();

      try {
        documentStorage.save(documentId, document.getOriginalFilename(), document.getInputStream());
        documentProcessingPublisher.publish(documentId);
      } catch (IOException exception) {
        throw new RuntimeException(
            "Failed to process document: " + document.getOriginalFilename(), exception);
      }
    }
  }
}
