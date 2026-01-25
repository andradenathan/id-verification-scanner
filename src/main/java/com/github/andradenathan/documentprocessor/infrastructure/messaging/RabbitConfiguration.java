package com.github.andradenathan.documentprocessor.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
  public static final String DOCUMENT_PROCESSING_DLX = "document.processing.dlx";
  public static final String DOCUMENT_PROCESSING_DLQ_ROUTING_KEY = "document.processing.dlq";
  public static String DOCUMENT_PROCESSING_EXCHANGE = "document.processing.exchange";
  public static String DOCUMENT_PROCESSING_ROUTING_KEY = "document.processing";

  @Value("${application.queue.name}")
  public String queueName;

  @Bean
  public Binding mrzProcessBinding(
      Queue mrzProcessQueue, TopicExchange documentProcessingExchange) {
    return BindingBuilder.bind(mrzProcessQueue)
        .to(documentProcessingExchange)
        .with(DOCUMENT_PROCESSING_ROUTING_KEY);
  }

  @Bean
  public TopicExchange documentProcessingExchange() {
    return new TopicExchange(DOCUMENT_PROCESSING_EXCHANGE);
  }

  @Bean
  public DirectExchange documentProcessingDlx() {
    return new DirectExchange(DOCUMENT_PROCESSING_DLX);
  }

  @Bean
  public Queue mrzProcessQueue() {
    return QueueBuilder.durable(queueName)
        .withArgument("x-dead-letter-exchange", DOCUMENT_PROCESSING_DLX)
        .withArgument("x-dead-letter-routing-key", DOCUMENT_PROCESSING_DLQ_ROUTING_KEY)
        .build();
  }
}
