package com.github.andradenathan.documentprocessor.infrastructure.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {
  @Value("${application.queue.name}")
  private String message;

  public Queue queue() {
    return new Queue(message, true);
  }
}
