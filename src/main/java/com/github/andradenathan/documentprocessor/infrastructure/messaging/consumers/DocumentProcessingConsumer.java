package com.github.andradenathan.documentprocessor.infrastructure.messaging.consumers;

import com.github.andradenathan.documentprocessor.domain.document.responses.ProcessDocumentResponse;
import com.github.andradenathan.documentprocessor.domain.document.service.DocumentProcessorService;
import com.github.andradenathan.documentprocessor.domain.document.service.MrzDocumentService;
import com.github.andradenathan.documentprocessor.infrastructure.messaging.model.DocumentProcessingMessage;
import com.github.andradenathan.documentprocessor.infrastructure.storage.DocumentStorage;
import java.io.File;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DocumentProcessingConsumer {
  private final DocumentStorage documentStorage;
  private final DocumentProcessorService documentProcessorService;
  private final MrzDocumentService mrzDocumentService;

  public DocumentProcessingConsumer(
      DocumentStorage documentStorage,
      DocumentProcessorService documentProcessorService,
      MrzDocumentService mrzDocumentService) {
    this.documentStorage = documentStorage;
    this.documentProcessorService = documentProcessorService;
    this.mrzDocumentService = mrzDocumentService;
  }

  @RabbitListener(queues = {"${application.queue.name}"})
  public void onMessage(@Payload DocumentProcessingMessage message) {
    UUID documentId = message.documentId();

    File file =
        documentStorage
            .load(documentId)
            .orElseThrow(() -> new RuntimeException("Document not found: " + documentId));

    List<ProcessDocumentResponse> responses =
        documentProcessorService.process(file).stream()
            .filter(ProcessDocumentResponse::isNotEmpty)
            .toList();

    mrzDocumentService.saveAll(responses);

    log.info("Finished MRZ processing for documentId={}", documentId);
  }
}
