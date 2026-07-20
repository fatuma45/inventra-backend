package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.request.StockMovementRequest;
import com.inventra.inventra_backend.dto.response.StockMovementResponse;
import com.inventra.inventra_backend.entity.Inventory;
import com.inventra.inventra_backend.entity.Product;
import com.inventra.inventra_backend.entity.StockMovement;
import com.inventra.inventra_backend.entity.User;
import com.inventra.inventra_backend.exception.BadRequestException;
import com.inventra.inventra_backend.exception.ResourceNotFoundException;
import com.inventra.inventra_backend.mapper.StockMovementMapper;
import com.inventra.inventra_backend.repository.InventoryRepository;
import com.inventra.inventra_backend.repository.ProductRepository;
import com.inventra.inventra_backend.repository.StockMovementRepository;
import com.inventra.inventra_backend.repository.UserRepository;
import com.inventra.inventra_backend.sequence.SequenceGeneratorService;
import com.inventra.inventra_backend.service.StockMovementService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockMovementServiceImpl implements StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public StockMovementServiceImpl(
            StockMovementRepository stockMovementRepository,
            InventoryRepository inventoryRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            SequenceGeneratorService sequenceGeneratorService) {

        this.stockMovementRepository = stockMovementRepository;
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public StockMovementResponse createMovement(StockMovementRequest request) {

        Inventory inventory = inventoryRepository.findById(request.getInventoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found."));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found."));

        User user = userRepository.findById(request.getPerformedBy())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        StockMovement movement = new StockMovement();

        movement.setMovementId(sequenceGeneratorService.generateId("MOV"));

        movement.setInventory(inventory);

        movement.setProduct(product);

        movement.setMovementType(request.getMovementType());

        movement.setQuantity(request.getQuantity());

        movement.setReferenceNumber(request.getReferenceNumber());

        movement.setRemarks(request.getRemarks());

        movement.setPerformedBy(user);

        movement.setMovementDate(LocalDateTime.now());

        switch (request.getMovementType()) {

            case STOCK_IN, RETURN ->

                    inventory.setQuantityOnHand(
                            inventory.getQuantityOnHand() + request.getQuantity()
                    );

            case STOCK_OUT, DAMAGE, TRANSFER -> {

                if (inventory.getAvailableQuantity() < request.getQuantity()) {
                    throw new BadRequestException("Insufficient stock available.");
                }

                inventory.setQuantityOnHand(
                        inventory.getQuantityOnHand() - request.getQuantity()
                );
            }

            case ADJUSTMENT ->

                    inventory.setQuantityOnHand(request.getQuantity());
        }

        inventory.setAvailableQuantity(
                inventory.getQuantityOnHand() - inventory.getReservedQuantity()
        );

        inventoryRepository.save(inventory);

        stockMovementRepository.save(movement);

        return StockMovementMapper.toResponse(movement);
    }

    @Override
    public List<StockMovementResponse> getAllMovements() {

        return stockMovementRepository.findAll()
                .stream()
                .map(StockMovementMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StockMovementResponse getMovementById(String movementId) {

        StockMovement movement = stockMovementRepository.findById(movementId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Stock movement not found."));

        return StockMovementMapper.toResponse(movement);
    }

    @Override
    public List<StockMovementResponse> getProductHistory(String productId) {

        return stockMovementRepository
                .findByProduct_ProductIdOrderByMovementDateDesc(productId)
                .stream()
                .map(StockMovementMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockMovementResponse> getInventoryHistory(String inventoryId) {

        return stockMovementRepository
                .findByInventory_InventoryIdOrderByMovementDateDesc(inventoryId)
                .stream()
                .map(StockMovementMapper::toResponse)
                .collect(Collectors.toList());
    }
}