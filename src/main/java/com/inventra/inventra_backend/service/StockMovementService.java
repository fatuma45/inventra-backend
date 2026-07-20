package com.inventra.inventra_backend.service;

import com.inventra.inventra_backend.dto.request.StockMovementRequest;
import com.inventra.inventra_backend.dto.response.StockMovementResponse;

import java.util.List;

public interface StockMovementService {

    StockMovementResponse createMovement(StockMovementRequest request);

    List<StockMovementResponse> getAllMovements();

    StockMovementResponse getMovementById(String movementId);

    List<StockMovementResponse> getProductHistory(String productId);

    List<StockMovementResponse> getInventoryHistory(String inventoryId);
}