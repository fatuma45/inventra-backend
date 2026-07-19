package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.dto.request.ProductRequest;
import com.inventra.inventra_backend.dto.response.ProductResponse;
import com.inventra.inventra_backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @PutMapping("/{productId}")
    public ProductResponse updateProduct(
            @PathVariable String productId,
            @Valid @RequestBody ProductRequest request) {

        return productService.updateProduct(productId, request);
    }

    @GetMapping("/{productId}")
    public ProductResponse getProductById(@PathVariable String productId) {
        return productService.getProductById(productId);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @PatchMapping("/{productId}/activate")
    public ProductResponse activateProduct(@PathVariable String productId) {
        return productService.activateProduct(productId);
    }

    @PatchMapping("/{productId}/deactivate")
    public ProductResponse deactivateProduct(@PathVariable String productId) {
        return productService.deactivateProduct(productId);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
    }
}
