package com.inventra.inventra_backend.mapper;

import com.inventra.inventra_backend.dto.response.PurchaseOrderItemResponse;
import com.inventra.inventra_backend.entity.PurchaseOrderItem;

public class PurchaseOrderItemMapper {

    private PurchaseOrderItemMapper() {
    }

    public static PurchaseOrderItemResponse toResponse(PurchaseOrderItem item) {

        PurchaseOrderItemResponse response = new PurchaseOrderItemResponse();

        response.setPurchaseOrderItemId(item.getPurchaseOrderItemId());

        if (item.getPurchaseOrder() != null) {
            response.setPurchaseOrderId(item.getPurchaseOrder().getPurchaseOrderId());
        }

        if (item.getProduct() != null) {
            response.setProductId(item.getProduct().getProductId());
            response.setProductName(item.getProduct().getProductName());
        }

        response.setQuantity(item.getQuantity());
        response.setUnitPrice(item.getUnitPrice());
        response.setTotalPrice(item.getTotalPrice());
        response.setActive(item.getActive());

        return response;
    }
}
