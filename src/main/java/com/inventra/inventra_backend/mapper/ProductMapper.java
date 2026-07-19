package com.inventra.inventra_backend.mapper;

import com.inventra.inventra_backend.dto.response.ProductResponse;
import com.inventra.inventra_backend.entity.Product;

public class ProductMapper {

    private ProductMapper() {
    }

    public static ProductResponse toResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setProductId(product.getProductId());
        response.setProductName(product.getProductName());
        response.setSku(product.getSku());
        response.setBarcode(product.getBarcode());
        response.setDescription(product.getDescription());

        if (product.getCategory() != null) {
            response.setCategoryId(product.getCategory().getCategoryId());
            response.setCategoryName(product.getCategory().getCategoryName());
        }

        if (product.getSupplier() != null) {
            response.setSupplierId(product.getSupplier().getSupplierId());
            response.setSupplierName(product.getSupplier().getSupplierName());
        }

        response.setSellingPrice(product.getSellingPrice());
        response.setCostPrice(product.getCostPrice());
        response.setReorderLevel(product.getReorderLevel());
        response.setUnitOfMeasure(product.getUnitOfMeasure());
        response.setActive(product.getActive());

        return response;
    }
}
