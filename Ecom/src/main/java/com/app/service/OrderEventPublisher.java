package com.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.app.events.OrderEvents;

@Component
public class OrderEventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public OrderEventPublisher(KafkaTemplate<String, String> kafkaTemplate,
                               @Value("${app.kafka.topic.order-confirmed:" + OrderEvents.TOPIC_ORDER_CONFIRMED + "}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void publishOrderConfirmed(Long orderItemId, Long productId, int quantity) {
        try {
            String payload = String.format("{\"orderItemId\":%d,\"productId\":%d,\"quantity\":%d}",
                    orderItemId, productId, quantity);
            kafkaTemplate.send(topic, String.valueOf(orderItemId), payload);
        } catch (Exception ex) {
            // ignore
        }
    }
}
