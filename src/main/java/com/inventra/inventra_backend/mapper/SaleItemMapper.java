package com.inventra.inventra_backend.mapper;

import com.inventra.inventra_backend.dto.response.SaleItemResponse;
import com.inventra.inventra_backend.entity.SaleItem;

public class SaleItemMapper {

    private SaleItemMapper() {
    }

    public static SaleItemResponse toResponse(SaleItem saleItem) {

        SaleItemResponse response = new SaleItemResponse();

        response.setSaleItemId(saleItem.getSaleItemId());

        response.setProductId(
                saleItem.getProduct().getProductId()
        );

        response.setProductName(
                saleItem.getProduct().getProductName()
        );

        response.setQuantity(
                saleItem.getQuantity()
        );

        response.setUnitPrice(
                saleItem.getUnitPrice()
        );

        response.setTotalPrice(
                saleItem.getTotalPrice()
        );

        response.setRemarks(
                saleItem.getRemarks()
        );

        return response;
    }

}
