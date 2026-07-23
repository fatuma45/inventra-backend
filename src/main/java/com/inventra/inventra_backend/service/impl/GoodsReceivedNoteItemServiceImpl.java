package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.request.GoodsReceivedNoteItemRequest;
import com.inventra.inventra_backend.dto.response.GoodsReceivedNoteItemResponse;
import com.inventra.inventra_backend.entity.*;
import com.inventra.inventra_backend.entity.enums.MovementType;
import com.inventra.inventra_backend.mapper.GoodsReceivedNoteItemMapper;
import com.inventra.inventra_backend.repository.*;
import com.inventra.inventra_backend.service.GoodsReceivedNoteItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GoodsReceivedNoteItemServiceImpl implements GoodsReceivedNoteItemService {

    private final GoodsReceivedNoteItemRepository repository;
    private final GoodsReceivedNoteRepository grnRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final StockMovementRepository stockMovementRepository;

    public GoodsReceivedNoteItemServiceImpl(
            GoodsReceivedNoteItemRepository repository,
            GoodsReceivedNoteRepository grnRepository,
            ProductRepository productRepository,
            InventoryRepository inventoryRepository,
            StockMovementRepository stockMovementRepository) {

        this.repository = repository;
        this.grnRepository = grnRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    @Override
    public GoodsReceivedNoteItemResponse create(GoodsReceivedNoteItemRequest request) {

        GoodsReceivedNote grn = grnRepository.findById(request.getGrnId())
                .orElseThrow(() -> new EntityNotFoundException("Goods Received Note not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Inventory inventory = inventoryRepository.findByProduct_ProductId(product.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Inventory record not found"));

        GoodsReceivedNoteItem item = GoodsReceivedNoteItemMapper.toEntity(request);

        item.setGrnItemId(String.format("GRNI%03d", repository.count() + 1));
        item.setGoodsReceivedNote(grn);
        item.setProduct(product);

        repository.save(item);

        int newQuantityOnHand = inventory.getQuantityOnHand() + request.getQuantityReceived();
        inventory.setQuantityOnHand(newQuantityOnHand);

        int available = newQuantityOnHand - inventory.getReservedQuantity();
        inventory.setAvailableQuantity(available);

        inventoryRepository.save(inventory);

        StockMovement movement = new StockMovement();

        movement.setMovementId(String.format("STM%03d", stockMovementRepository.count() + 1));
        movement.setInventory(inventory);
        movement.setProduct(product);
        movement.setMovementType(MovementType.STOCK_IN);
        movement.setQuantity(request.getQuantityReceived());
        movement.setReferenceNumber(grn.getGrnId());
        movement.setRemarks("Goods received into inventory");
        movement.setPerformedBy(grn.getReceivedBy());

        stockMovementRepository.save(movement);

        return GoodsReceivedNoteItemMapper.toResponse(item);
    }

    @Override
    public GoodsReceivedNoteItemResponse getById(String grnItemId) {

        GoodsReceivedNoteItem item = repository.findById(grnItemId)
                .orElseThrow(() -> new EntityNotFoundException("GRN Item not found"));

        return GoodsReceivedNoteItemMapper.toResponse(item);
    }

    @Override
    public List<GoodsReceivedNoteItemResponse> getAll() {

        return repository.findByDeletedFalse()
                .stream()
                .map(GoodsReceivedNoteItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<GoodsReceivedNoteItemResponse> getByGrn(String grnId) {

        return repository.findByGoodsReceivedNote_GrnIdAndDeletedFalse(grnId)
                .stream()
                .map(GoodsReceivedNoteItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<GoodsReceivedNoteItemResponse> getByProduct(String productId) {

        return repository.findByProduct_ProductIdAndDeletedFalse(productId)
                .stream()
                .map(GoodsReceivedNoteItemMapper::toResponse)
                .collect(Collectors.toList());
    }
    @Override
    public GoodsReceivedNoteItemResponse update(String grnItemId, GoodsReceivedNoteItemRequest request) {

        GoodsReceivedNoteItem item = repository.findById(grnItemId)
                .orElseThrow(() -> new EntityNotFoundException("GRN Item not found"));

        GoodsReceivedNote grn = grnRepository.findById(request.getGrnId())
                .orElseThrow(() -> new EntityNotFoundException("GRN not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        item.setGoodsReceivedNote(grn);
        item.setProduct(product);
        item.setQuantityReceived(request.getQuantityReceived());
        item.setUnitCost(request.getUnitCost());

        item.setTotalCost(
                request.getUnitCost().multiply(
                        java.math.BigDecimal.valueOf(request.getQuantityReceived())
                )
        );

        item.setRemarks(request.getRemarks());

        repository.save(item);

        return GoodsReceivedNoteItemMapper.toResponse(item);
    }

    @Override
    public void delete(String grnItemId) {

        GoodsReceivedNoteItem item = repository.findById(grnItemId)
                .orElseThrow(() -> new EntityNotFoundException("GRN Item not found"));

        item.setDeleted(true);

        repository.save(item);
    }

}
