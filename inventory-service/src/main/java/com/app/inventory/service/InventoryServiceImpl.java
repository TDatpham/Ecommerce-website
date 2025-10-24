package com.app.inventory.service;

import com.app.inventory.model.InventoryItem;
import com.app.inventory.repo.InventoryItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryItemRepository repository;

    public InventoryServiceImpl(InventoryItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public InventoryItem createItem(InventoryItem item) {
        if (item.getQuantity() == null) {
            item.setQuantity(0);
        }
        return repository.save(item);
    }

    @Override
    public Optional<InventoryItem> getBySku(String sku) {
        return repository.findBySku(sku);
    }

    @Override
    public List<InventoryItem> listAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public InventoryItem adjustQuantity(String sku, int delta) {
        InventoryItem item = repository.findBySku(sku)
                .orElseThrow(() -> new IllegalArgumentException("SKU not found: " + sku));
        int newQty = Math.max(0, item.getQuantity() + delta);
        item.setQuantity(newQty);
        return item;
    }
}
