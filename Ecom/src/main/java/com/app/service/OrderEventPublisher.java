package com.app.service;

import com.app.events.OrderConfirmedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    private final KafkaTemplate<String, OrderConfirmedEvent> kafkaTemplate;

    @Value("${kafka.topics.order-confirmed:order.confirmed}")
    private String topic;

    public OrderEventPublisher(KafkaTemplate<String, OrderConfirmedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(OrderConfirmedEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
