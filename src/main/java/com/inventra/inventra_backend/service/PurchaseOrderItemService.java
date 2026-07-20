package com.inventra.inventra_backend.service;

import com.inventra.inventra_backend.dto.request.PurchaseOrderItemRequest;
import com.inventra.inventra_backend.dto.response.PurchaseOrderItemResponse;

import java.util.List;

public interface PurchaseOrderItemService {

    PurchaseOrderItemResponse createPurchaseOrderItem(PurchaseOrderItemRequest request);

    PurchaseOrderItemResponse updatePurchaseOrderItem(String purchaseOrderItemId,
                                                      PurchaseOrderItemRequest request);

    PurchaseOrderItemResponse getPurchaseOrderItemById(String purchaseOrderItemId);

    List<PurchaseOrderItemResponse> getAllPurchaseOrderItems();

    List<PurchaseOrderItemResponse> getPurchaseOrderItemsByPurchaseOrder(String purchaseOrderId);

    List<PurchaseOrderItemResponse> getPurchaseOrderItemsByProduct(String productId);

    PurchaseOrderItemResponse activatePurchaseOrderItem(String purchaseOrderItemId);

    PurchaseOrderItemResponse deactivatePurchaseOrderItem(String purchaseOrderItemId);

    void deletePurchaseOrderItem(String purchaseOrderItemId);
}