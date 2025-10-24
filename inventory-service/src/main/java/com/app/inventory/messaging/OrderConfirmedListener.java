package com.app.inventory.messaging;

import com.app.inventory.events.OrderConfirmedEvent;
import com.app.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderConfirmedListener {

    private final InventoryService inventoryService;

    @Value("${kafka.topics.order-confirmed}")
    private String topic;

    public OrderConfirmedListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "#{'${kafka.topics.order-confirmed}'}", groupId = "inventory-service")
    public void onOrderConfirmed(@Payload OrderConfirmedEvent event) {
        if (event.getSku() != null) {
            inventoryService.adjustQuantity(event.getSku(), -event.getQuantity());
        }
    }
}
