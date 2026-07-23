package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.request.SaleItemRequest;
import com.inventra.inventra_backend.dto.response.SaleItemResponse;
import com.inventra.inventra_backend.entity.*;
import com.inventra.inventra_backend.entity.enums.MovementType;
import com.inventra.inventra_backend.mapper.SaleItemMapper;
import com.inventra.inventra_backend.repository.*;
import com.inventra.inventra_backend.service.SaleItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SaleItemServiceImpl implements SaleItemService {

    private final SaleItemRepository saleItemRepository;
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final StockMovementRepository stockMovementRepository;
    private final UserRepository userRepository;

    public SaleItemServiceImpl(
            SaleItemRepository saleItemRepository,
            SaleRepository saleRepository,
            ProductRepository productRepository,
            InventoryRepository inventoryRepository,
            StockMovementRepository stockMovementRepository,
            UserRepository userRepository) {

        this.saleItemRepository = saleItemRepository;
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.stockMovementRepository = stockMovementRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SaleItemResponse create(String saleId, SaleItemRequest request) {

        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Sale not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Product not found"));

        Inventory inventory = inventoryRepository
                .findByProduct_ProductId(product.getProductId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Inventory not found"));

        if (inventory.getAvailableQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock available.");
        }

        SaleItem saleItem = new SaleItem();

        saleItem.setSaleItemId(
                String.format("SI%03d",
                        saleItemRepository.count() + 1)
        );

        saleItem.setSale(sale);

        saleItem.setProduct(product);

        saleItem.setQuantity(request.getQuantity());

        saleItem.setUnitPrice(product.getSellingPrice());

        saleItem.setTotalPrice(
                product.getSellingPrice()
                        .multiply(BigDecimal.valueOf(request.getQuantity()))
        );

        saleItem.setRemarks(request.getRemarks());

        // Deduct inventory
        inventory.setQuantityOnHand(
                inventory.getQuantityOnHand() - request.getQuantity()
        );

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() - request.getQuantity()
        );

        inventoryRepository.save(inventory);

        // Create stock movement
        StockMovement movement = new StockMovement();

        movement.setMovementId(
                String.format("SM%03d",
                        stockMovementRepository.count() + 1)
        );

        movement.setInventory(inventory);
        movement.setProduct(product);
        movement.setMovementType(MovementType.STOCK_OUT);
        movement.setQuantity(request.getQuantity());

        movement.setReferenceNumber(sale.getSaleId());

        movement.setRemarks("Sale Transaction");

        movement.setPerformedBy(sale.getSoldBy());

        movement.setMovementDate(LocalDateTime.now());

        stockMovementRepository.save(movement);

        saleItemRepository.save(saleItem);

        // Recalculate sale total
        BigDecimal total = saleItemRepository
                .findBySale_SaleIdAndDeletedFalse(saleId)
                .stream()
                .map(SaleItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        sale.setTotalAmount(total);

        saleRepository.save(sale);

        return SaleItemMapper.toResponse(saleItem);
    }

    @Override
    public SaleItemResponse getById(String saleItemId) {

        SaleItem saleItem = saleItemRepository.findById(saleItemId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Sale Item not found"));

        return SaleItemMapper.toResponse(saleItem);
    }

    @Override
    public List<SaleItemResponse> getAll() {

        return saleItemRepository.findByDeletedFalse()
                .stream()
                .map(SaleItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaleItemResponse> getBySale(String saleId) {

        return saleItemRepository
                .findBySale_SaleIdAndDeletedFalse(saleId)
                .stream()
                .map(SaleItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SaleItemResponse update(
            String saleId,
            String saleItemId,
            SaleItemRequest request) {

        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Sale not found"));

        SaleItem saleItem = saleItemRepository.findById(saleItemId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Sale Item not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Product not found"));

        Inventory inventory = inventoryRepository
                .findByProduct_ProductId(product.getProductId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Inventory not found"));

        // Return previous quantity to inventory
        inventory.setQuantityOnHand(
                inventory.getQuantityOnHand() + saleItem.getQuantity());

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() + saleItem.getQuantity());

        // Check enough stock
        if (inventory.getAvailableQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock available.");
        }

        // Deduct new quantity
        inventory.setQuantityOnHand(
                inventory.getQuantityOnHand() - request.getQuantity());

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() - request.getQuantity());

        inventoryRepository.save(inventory);

        saleItem.setProduct(product);
        saleItem.setQuantity(request.getQuantity());
        saleItem.setUnitPrice(product.getSellingPrice());

        saleItem.setTotalPrice(
                product.getSellingPrice()
                        .multiply(BigDecimal.valueOf(request.getQuantity()))
        );

        saleItem.setRemarks(request.getRemarks());

        saleItemRepository.save(saleItem);

        BigDecimal total = saleItemRepository
                .findBySale_SaleIdAndDeletedFalse(saleId)
                .stream()
                .map(SaleItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        sale.setTotalAmount(total);

        saleRepository.save(sale);

        return SaleItemMapper.toResponse(saleItem);
    }

    @Override
    public void delete(String saleItemId) {

        SaleItem saleItem = saleItemRepository.findById(saleItemId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Sale Item not found"));

        saleItem.setDeleted(true);

        saleItemRepository.save(saleItem);
    }
}
