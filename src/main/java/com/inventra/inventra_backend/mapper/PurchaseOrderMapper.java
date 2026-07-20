package com.inventra.inventra_backend.mapper;

import com.inventra.inventra_backend.dto.response.PurchaseOrderResponse;
import com.inventra.inventra_backend.entity.PurchaseOrder;

public class PurchaseOrderMapper {

    private PurchaseOrderMapper() {
    }

    public static PurchaseOrderResponse toResponse(PurchaseOrder purchaseOrder) {

        PurchaseOrderResponse response = new PurchaseOrderResponse();

        response.setPurchaseOrderId(purchaseOrder.getPurchaseOrderId());

        if (purchaseOrder.getSupplier() != null) {
            response.setSupplierId(purchaseOrder.getSupplier().getSupplierId());
            response.setSupplierName(purchaseOrder.getSupplier().getSupplierName());
        }

        response.setOrderDate(purchaseOrder.getOrderDate());
        response.setExpectedDeliveryDate(purchaseOrder.getExpectedDeliveryDate());

        if (purchaseOrder.getStatus() != null) {
            response.setStatus(purchaseOrder.getStatus().name());
        }

        response.setTotalAmount(purchaseOrder.getTotalAmount());
        response.setNotes(purchaseOrder.getNotes());

        if (purchaseOrder.getCreatedBy() != null) {
            response.setCreatedBy(purchaseOrder.getCreatedBy().getUserId());
        }

        response.setActive(purchaseOrder.getActive());

        return response;
    }
}