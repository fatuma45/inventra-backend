package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.dto.request.PurchaseOrderItemRequest;
import com.inventra.inventra_backend.dto.response.PurchaseOrderItemResponse;
import com.inventra.inventra_backend.service.PurchaseOrderItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase-order-items")
public class PurchaseOrderItemController {

    private final PurchaseOrderItemService purchaseOrderItemService;

    public PurchaseOrderItemController(PurchaseOrderItemService purchaseOrderItemService) {
        this.purchaseOrderItemService = purchaseOrderItemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseOrderItemResponse createPurchaseOrderItem(
            @Valid @RequestBody PurchaseOrderItemRequest request) {

        return purchaseOrderItemService.createPurchaseOrderItem(request);
    }

    @PutMapping("/{purchaseOrderItemId}")
    public PurchaseOrderItemResponse updatePurchaseOrderItem(
            @PathVariable String purchaseOrderItemId,
            @Valid @RequestBody PurchaseOrderItemRequest request) {

        return purchaseOrderItemService.updatePurchaseOrderItem(
                purchaseOrderItemId,
                request
        );
    }

    @GetMapping("/{purchaseOrderItemId}")
    public PurchaseOrderItemResponse getPurchaseOrderItemById(
            @PathVariable String purchaseOrderItemId) {

        return purchaseOrderItemService.getPurchaseOrderItemById(purchaseOrderItemId);
    }

    @GetMapping
    public List<PurchaseOrderItemResponse> getAllPurchaseOrderItems() {
        return purchaseOrderItemService.getAllPurchaseOrderItems();
    }

    @GetMapping("/purchase-order/{purchaseOrderId}")
    public List<PurchaseOrderItemResponse> getByPurchaseOrder(
            @PathVariable String purchaseOrderId) {

        return purchaseOrderItemService.getPurchaseOrderItemsByPurchaseOrder(
                purchaseOrderId
        );
    }

    @GetMapping("/product/{productId}")
    public List<PurchaseOrderItemResponse> getByProduct(
            @PathVariable String productId) {

        return purchaseOrderItemService.getPurchaseOrderItemsByProduct(productId);
    }

    @PatchMapping("/{purchaseOrderItemId}/activate")
    public PurchaseOrderItemResponse activate(
            @PathVariable String purchaseOrderItemId) {

        return purchaseOrderItemService.activatePurchaseOrderItem(
                purchaseOrderItemId
        );
    }

    @PatchMapping("/{purchaseOrderItemId}/deactivate")
    public PurchaseOrderItemResponse deactivate(
            @PathVariable String purchaseOrderItemId) {

        return purchaseOrderItemService.deactivatePurchaseOrderItem(
                purchaseOrderItemId
        );
    }

    @DeleteMapping("/{purchaseOrderItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable String purchaseOrderItemId) {

        purchaseOrderItemService.deletePurchaseOrderItem(
                purchaseOrderItemId
        );
    }
}
