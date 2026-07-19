package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.dto.request.InventoryRequest;
import com.inventra.inventra_backend.dto.response.InventoryResponse;
import com.inventra.inventra_backend.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse createInventory(@Valid @RequestBody InventoryRequest request) {
        return inventoryService.createInventory(request);
    }

    @PutMapping("/{inventoryId}")
    public InventoryResponse updateInventory(
            @PathVariable String inventoryId,
            @Valid @RequestBody InventoryRequest request) {

        return inventoryService.updateInventory(inventoryId, request);
    }

    @GetMapping("/{inventoryId}")
    public InventoryResponse getInventoryById(@PathVariable String inventoryId) {
        return inventoryService.getInventoryById(inventoryId);
    }

    @GetMapping("/product/{productId}")
    public InventoryResponse getInventoryByProduct(@PathVariable String productId) {
        return inventoryService.getInventoryByProduct(productId);
    }

    @GetMapping
    public List<InventoryResponse> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @PatchMapping("/{inventoryId}/activate")
    public InventoryResponse activateInventory(@PathVariable String inventoryId) {
        return inventoryService.activateInventory(inventoryId);
    }

    @PatchMapping("/{inventoryId}/deactivate")
    public InventoryResponse deactivateInventory(@PathVariable String inventoryId) {
        return inventoryService.deactivateInventory(inventoryId);
    }

    @DeleteMapping("/{inventoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable String inventoryId) {
        inventoryService.deleteInventory(inventoryId);
    }
}
