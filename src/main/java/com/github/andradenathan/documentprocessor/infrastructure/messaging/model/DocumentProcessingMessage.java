package com.github.andradenathan.documentprocessor.infrastructure.messaging.model;

import java.time.Instant;
import java.util.UUID;

public record DocumentProcessingMessage(UUID documentId, Instant occurredAt) {
  public static DocumentProcessingMessage of(UUID documentId) {
    return new DocumentProcessingMessage(documentId, Instant.now());
  }
}
