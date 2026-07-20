package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.dto.request.PurchaseOrderRequest;
import com.inventra.inventra_backend.dto.response.PurchaseOrderResponse;
import com.inventra.inventra_backend.service.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseOrderResponse createPurchaseOrder(
            @Valid @RequestBody PurchaseOrderRequest request) {

        return purchaseOrderService.createPurchaseOrder(request);
    }

    @PutMapping("/{purchaseOrderId}")
    public PurchaseOrderResponse updatePurchaseOrder(
            @PathVariable String purchaseOrderId,
            @Valid @RequestBody PurchaseOrderRequest request) {

        return purchaseOrderService.updatePurchaseOrder(purchaseOrderId, request);
    }

    @GetMapping("/{purchaseOrderId}")
    public PurchaseOrderResponse getPurchaseOrderById(
            @PathVariable String purchaseOrderId) {

        return purchaseOrderService.getPurchaseOrderById(purchaseOrderId);
    }

    @GetMapping
    public List<PurchaseOrderResponse> getAllPurchaseOrders() {
        return purchaseOrderService.getAllPurchaseOrders();
    }

    @GetMapping("/supplier/{supplierId}")
    public List<PurchaseOrderResponse> getPurchaseOrdersBySupplier(
            @PathVariable String supplierId) {

        return purchaseOrderService.getPurchaseOrdersBySupplier(supplierId);
    }

    @PatchMapping("/{purchaseOrderId}/activate")
    public PurchaseOrderResponse activatePurchaseOrder(
            @PathVariable String purchaseOrderId) {

        return purchaseOrderService.activatePurchaseOrder(purchaseOrderId);
    }

    @PatchMapping("/{purchaseOrderId}/deactivate")
    public PurchaseOrderResponse deactivatePurchaseOrder(
            @PathVariable String purchaseOrderId) {

        return purchaseOrderService.deactivatePurchaseOrder(purchaseOrderId);
    }

    @DeleteMapping("/{purchaseOrderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePurchaseOrder(
            @PathVariable String purchaseOrderId) {

        purchaseOrderService.deletePurchaseOrder(purchaseOrderId);
    }
}
