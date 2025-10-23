package com.app.inventory.controller;

import com.app.inventory.model.InventoryItem;
import com.app.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InventoryItem> create(@Valid @RequestBody InventoryItem item) {
        InventoryItem created = service.createItem(item);
        return ResponseEntity.created(URI.create("/inventory/" + created.getSku())).body(created);
    }

    @GetMapping
    public List<InventoryItem> list() {
        return service.listAll();
    }

    @GetMapping("/{sku}")
    public ResponseEntity<InventoryItem> get(@PathVariable String sku) {
        return service.getBySku(sku)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{sku}/adjust")
    public ResponseEntity<InventoryItem> adjust(@PathVariable String sku, @RequestParam int delta) {
        try {
            return ResponseEntity.ok(service.adjustQuantity(sku, delta));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
