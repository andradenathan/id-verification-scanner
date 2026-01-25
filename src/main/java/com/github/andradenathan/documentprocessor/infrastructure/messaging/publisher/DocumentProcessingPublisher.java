package com.github.andradenathan.documentprocessor.infrastructure.messaging.publisher;

import static com.github.andradenathan.documentprocessor.infrastructure.messaging.RabbitConfiguration.DOCUMENT_PROCESSING_EXCHANGE;
import static com.github.andradenathan.documentprocessor.infrastructure.messaging.RabbitConfiguration.DOCUMENT_PROCESSING_ROUTING_KEY;

import com.github.andradenathan.documentprocessor.infrastructure.messaging.model.DocumentProcessingMessage;
import java.util.UUID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class DocumentProcessingPublisher {
  private final RabbitTemplate rabbitTemplate;

  public DocumentProcessingPublisher(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void publish(UUID documentId) {
    rabbitTemplate.convertAndSend(
        DOCUMENT_PROCESSING_EXCHANGE,
        DOCUMENT_PROCESSING_ROUTING_KEY,
        DocumentProcessingMessage.of(documentId));
  }
}
