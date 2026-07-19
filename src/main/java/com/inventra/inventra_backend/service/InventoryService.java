package com.inventra.inventra_backend.service;

import com.inventra.inventra_backend.dto.request.InventoryRequest;
import com.inventra.inventra_backend.dto.response.InventoryResponse;

import java.util.List;

public interface InventoryService {

    InventoryResponse createInventory(InventoryRequest request);

    InventoryResponse updateInventory(String inventoryId, InventoryRequest request);

    InventoryResponse getInventoryById(String inventoryId);

    InventoryResponse getInventoryByProduct(String productId);

    List<InventoryResponse> getAllInventory();

    InventoryResponse activateInventory(String inventoryId);

    InventoryResponse deactivateInventory(String inventoryId);

    void deleteInventory(String inventoryId);
}
