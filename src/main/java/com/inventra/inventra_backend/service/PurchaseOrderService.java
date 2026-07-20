package com.inventra.inventra_backend.service;

import com.inventra.inventra_backend.dto.request.PurchaseOrderRequest;
import com.inventra.inventra_backend.dto.response.PurchaseOrderResponse;

import java.util.List;

public interface PurchaseOrderService {

    PurchaseOrderResponse createPurchaseOrder(PurchaseOrderRequest request);

    PurchaseOrderResponse updatePurchaseOrder(String purchaseOrderId, PurchaseOrderRequest request);

    PurchaseOrderResponse getPurchaseOrderById(String purchaseOrderId);

    List<PurchaseOrderResponse> getAllPurchaseOrders();

    List<PurchaseOrderResponse> getPurchaseOrdersBySupplier(String supplierId);

    PurchaseOrderResponse activatePurchaseOrder(String purchaseOrderId);

    PurchaseOrderResponse deactivatePurchaseOrder(String purchaseOrderId);

    void deletePurchaseOrder(String purchaseOrderId);
}