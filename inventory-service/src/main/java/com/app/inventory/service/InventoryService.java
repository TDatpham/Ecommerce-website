package com.app.inventory.service;

import com.app.inventory.model.InventoryItem;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    InventoryItem createItem(InventoryItem item);
    Optional<InventoryItem> getBySku(String sku);
    List<InventoryItem> listAll();
    InventoryItem adjustQuantity(String sku, int delta);
}
