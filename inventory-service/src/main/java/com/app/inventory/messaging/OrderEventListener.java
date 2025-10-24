package com.app.inventory.messaging;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.app.inventory.service.InventoryService;

@Component
@ConditionalOnProperty(prefix = "spring.kafka", name = "bootstrap-servers")
public class OrderEventListener {
    private final InventoryService inventoryService;

    public OrderEventListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "${app.kafka.topic.order-confirmed:order.confirmed}", groupId = "${spring.kafka.consumer.group-id:inventory-service}")
    public void onOrderConfirmed(String payload) {
        // Very simple parser to avoid extra dependency; payload like {"orderItemId":1,"productId":2,"quantity":3}
        try {
            Long productId = extractLong(payload, "productId");
            Integer quantity = extractInt(payload, "quantity");
            if (productId != null && quantity != null) {
                // In this demo, we just log or could adjust stock via SKU mapping if available
                // inventoryService.adjustQuantityByProductId(productId, -quantity);
            }
        } catch (Exception ignored) {}
    }

    private Long extractLong(String json, String key) {
        String pattern = "\"" + key + "\":";
        int idx = json.indexOf(pattern);
        if (idx == -1) return null;
        int start = idx + pattern.length();
        int end = start;
        while (end < json.length() && Character.isDigit(json.charAt(end))) end++;
        return Long.parseLong(json.substring(start, end));
    }

    private Integer extractInt(String json, String key) {
        Long val = extractLong(json, key);
        return val == null ? null : val.intValue();
        
    }
}
