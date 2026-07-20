package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.dto.request.StockMovementRequest;
import com.inventra.inventra_backend.dto.response.StockMovementResponse;
import com.inventra.inventra_backend.service.StockMovementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock-movements")
public class StockMovementController {

    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockMovementResponse createMovement(
            @Valid @RequestBody StockMovementRequest request) {

        return stockMovementService.createMovement(request);
    }

    @GetMapping
    public List<StockMovementResponse> getAllMovements() {
        return stockMovementService.getAllMovements();
    }

    @GetMapping("/{movementId}")
    public StockMovementResponse getMovementById(
            @PathVariable String movementId) {

        return stockMovementService.getMovementById(movementId);
    }

    @GetMapping("/product/{productId}")
    public List<StockMovementResponse> getProductHistory(
            @PathVariable String productId) {

        return stockMovementService.getProductHistory(productId);
    }

    @GetMapping("/inventory/{inventoryId}")
    public List<StockMovementResponse> getInventoryHistory(
            @PathVariable String inventoryId) {

        return stockMovementService.getInventoryHistory(inventoryId);
    }
}
