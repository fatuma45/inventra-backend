package com.inventra.inventra_backend.mapper;

import com.inventra.inventra_backend.dto.response.InventoryResponse;
import com.inventra.inventra_backend.entity.Inventory;

public class InventoryMapper {

    private InventoryMapper() {
    }

    public static InventoryResponse toResponse(Inventory inventory) {

        InventoryResponse response = new InventoryResponse();

        response.setInventoryId(inventory.getInventoryId());

        if (inventory.getProduct() != null) {
            response.setProductId(inventory.getProduct().getProductId());
            response.setProductName(inventory.getProduct().getProductName());
        }

        response.setQuantityOnHand(inventory.getQuantityOnHand());
        response.setReservedQuantity(inventory.getReservedQuantity());
        response.setAvailableQuantity(inventory.getAvailableQuantity());
        response.setMinimumStock(inventory.getMinimumStock());
        response.setMaximumStock(inventory.getMaximumStock());
        response.setWarehouseLocation(inventory.getWarehouseLocation());
        response.setActive(inventory.getActive());

        return response;
    }
}
