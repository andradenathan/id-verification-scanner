package com.github.andradenathan.documentprocessor.infrastructure.messaging.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DocumentProcessingConsumer {
  @RabbitListener(queues = {"${application.queue.name}"})
  public void receive(@Payload String message) {}
}
