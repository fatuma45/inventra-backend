package com.inventra.inventra_backend.mapper;

import com.inventra.inventra_backend.dto.response.StockMovementResponse;
import com.inventra.inventra_backend.entity.StockMovement;

public class StockMovementMapper {

    private StockMovementMapper() {
    }

    public static StockMovementResponse toResponse(StockMovement movement) {

        StockMovementResponse response = new StockMovementResponse();

        response.setMovementId(movement.getMovementId());

        if (movement.getInventory() != null) {
            response.setInventoryId(movement.getInventory().getInventoryId());
        }

        if (movement.getProduct() != null) {
            response.setProductId(movement.getProduct().getProductId());
            response.setProductName(movement.getProduct().getProductName());
        }

        if (movement.getMovementType() != null) {
            response.setMovementType(movement.getMovementType().name());
        }

        response.setQuantity(movement.getQuantity());
        response.setReferenceNumber(movement.getReferenceNumber());
        response.setRemarks(movement.getRemarks());

        if (movement.getPerformedBy() != null) {
            response.setPerformedBy(movement.getPerformedBy().getUserId());
        }

        response.setMovementDate(movement.getMovementDate());

        return response;
    }
}