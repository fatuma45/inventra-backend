package com.inventra.inventra_backend.service;

import com.inventra.inventra_backend.dto.request.ProductRequest;
import com.inventra.inventra_backend.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(String productId, ProductRequest request);

    ProductResponse getProductById(String productId);

    List<ProductResponse> getAllProducts();

    ProductResponse activateProduct(String productId);

    ProductResponse deactivateProduct(String productId);

    void deleteProduct(String productId);
}
