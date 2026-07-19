package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.request.ProductRequest;
import com.inventra.inventra_backend.dto.response.ProductResponse;
import com.inventra.inventra_backend.entity.Category;
import com.inventra.inventra_backend.entity.Product;
import com.inventra.inventra_backend.entity.Supplier;
import com.inventra.inventra_backend.exception.DuplicateResourceException;
import com.inventra.inventra_backend.exception.ResourceNotFoundException;
import com.inventra.inventra_backend.mapper.ProductMapper;
import com.inventra.inventra_backend.repository.CategoryRepository;
import com.inventra.inventra_backend.repository.ProductRepository;
import com.inventra.inventra_backend.repository.SupplierRepository;
import com.inventra.inventra_backend.sequence.SequenceGeneratorService;
import com.inventra.inventra_backend.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public ProductServiceImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            SupplierRepository supplierRepository,
            SequenceGeneratorService sequenceGeneratorService) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        if (productRepository.existsByProductName(request.getProductName())) {
            throw new DuplicateResourceException("Product name already exists.");
        }

        if (request.getSku() != null &&
                !request.getSku().isBlank() &&
                productRepository.existsBySku(request.getSku())) {

            throw new DuplicateResourceException("SKU already exists.");
        }

        if (request.getBarcode() != null &&
                !request.getBarcode().isBlank() &&
                productRepository.existsByBarcode(request.getBarcode())) {

            throw new DuplicateResourceException("Barcode already exists.");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found."));

        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier not found."));

        Product product = new Product();

        product.setProductId(sequenceGeneratorService.generateId("PROD"));
        product.setProductName(request.getProductName());
        product.setSku(request.getSku());
        product.setBarcode(request.getBarcode());
        product.setDescription(request.getDescription());

        product.setCategory(category);
        product.setSupplier(supplier);

        product.setSellingPrice(request.getSellingPrice());
        product.setCostPrice(request.getCostPrice());
        product.setReorderLevel(request.getReorderLevel());
        product.setUnitOfMeasure(request.getUnitOfMeasure());
        product.setActive(request.getActive());

        productRepository.save(product);

        return ProductMapper.toResponse(product);
    }

    @Override
    public ProductResponse updateProduct(String productId, ProductRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found."));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found."));

        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier not found."));

        product.setProductName(request.getProductName());
        product.setSku(request.getSku());
        product.setBarcode(request.getBarcode());
        product.setDescription(request.getDescription());

        product.setCategory(category);
        product.setSupplier(supplier);

        product.setSellingPrice(request.getSellingPrice());
        product.setCostPrice(request.getCostPrice());
        product.setReorderLevel(request.getReorderLevel());
        product.setUnitOfMeasure(request.getUnitOfMeasure());
        product.setActive(request.getActive());

        productRepository.save(product);

        return ProductMapper.toResponse(product);
    }

    @Override
    public ProductResponse getProductById(String productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found."));

        return ProductMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .filter(product -> !product.getDeleted())
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse activateProduct(String productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found."));

        product.setActive(true);

        productRepository.save(product);

        return ProductMapper.toResponse(product);
    }

    @Override
    public ProductResponse deactivateProduct(String productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found."));

        product.setActive(false);

        productRepository.save(product);

        return ProductMapper.toResponse(product);
    }

    @Override
    public void deleteProduct(String productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found."));

        product.setDeleted(true);
        product.setActive(false);

        productRepository.save(product);
    }
}
