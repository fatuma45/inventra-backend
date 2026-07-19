package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.request.InventoryRequest;
import com.inventra.inventra_backend.dto.response.InventoryResponse;
import com.inventra.inventra_backend.entity.Inventory;
import com.inventra.inventra_backend.entity.Product;
import com.inventra.inventra_backend.exception.DuplicateResourceException;
import com.inventra.inventra_backend.exception.ResourceNotFoundException;
import com.inventra.inventra_backend.mapper.InventoryMapper;
import com.inventra.inventra_backend.repository.InventoryRepository;
import com.inventra.inventra_backend.repository.ProductRepository;
import com.inventra.inventra_backend.sequence.SequenceGeneratorService;
import com.inventra.inventra_backend.service.InventoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public InventoryServiceImpl(
            InventoryRepository inventoryRepository,
            ProductRepository productRepository,
            SequenceGeneratorService sequenceGeneratorService) {

        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public InventoryResponse createInventory(InventoryRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found."));

        if (inventoryRepository.existsByProduct_ProductId(request.getProductId())) {
            throw new DuplicateResourceException("Inventory already exists for this product.");
        }

        Inventory inventory = new Inventory();

        inventory.setInventoryId(sequenceGeneratorService.generateId("INV"));

        inventory.setProduct(product);

        inventory.setQuantityOnHand(request.getQuantityOnHand());

        inventory.setReservedQuantity(request.getReservedQuantity());

        inventory.setAvailableQuantity(
                request.getQuantityOnHand() - request.getReservedQuantity()
        );

        inventory.setMinimumStock(request.getMinimumStock());

        inventory.setMaximumStock(request.getMaximumStock());

        inventory.setWarehouseLocation(request.getWarehouseLocation());

        inventory.setActive(request.getActive());

        inventoryRepository.save(inventory);

        return InventoryMapper.toResponse(inventory);
    }

    @Override
    public InventoryResponse updateInventory(String inventoryId, InventoryRequest request) {

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found."));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found."));

        inventory.setProduct(product);

        inventory.setQuantityOnHand(request.getQuantityOnHand());

        inventory.setReservedQuantity(request.getReservedQuantity());

        inventory.setAvailableQuantity(
                request.getQuantityOnHand() - request.getReservedQuantity()
        );

        inventory.setMinimumStock(request.getMinimumStock());

        inventory.setMaximumStock(request.getMaximumStock());

        inventory.setWarehouseLocation(request.getWarehouseLocation());

        inventory.setActive(request.getActive());

        inventoryRepository.save(inventory);

        return InventoryMapper.toResponse(inventory);
    }

    @Override
    public InventoryResponse getInventoryById(String inventoryId) {

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found."));

        return InventoryMapper.toResponse(inventory);
    }

    @Override
    public InventoryResponse getInventoryByProduct(String productId) {

        Inventory inventory = inventoryRepository.findByProduct_ProductId(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found for this product."));

        return InventoryMapper.toResponse(inventory);
    }

    @Override
    public List<InventoryResponse> getAllInventory() {

        return inventoryRepository.findAll()
                .stream()
                .filter(inventory -> !inventory.getDeleted())
                .map(InventoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryResponse activateInventory(String inventoryId) {

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found."));

        inventory.setActive(true);

        inventoryRepository.save(inventory);

        return InventoryMapper.toResponse(inventory);
    }

    @Override
    public InventoryResponse deactivateInventory(String inventoryId) {

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found."));

        inventory.setActive(false);

        inventoryRepository.save(inventory);

        return InventoryMapper.toResponse(inventory);
    }

    @Override
    public void deleteInventory(String inventoryId) {

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found."));

        inventory.setDeleted(true);
        inventory.setActive(false);

        inventoryRepository.save(inventory);
    }
}