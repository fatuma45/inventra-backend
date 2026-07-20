package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.request.PurchaseOrderItemRequest;
import com.inventra.inventra_backend.dto.response.PurchaseOrderItemResponse;
import com.inventra.inventra_backend.entity.Product;
import com.inventra.inventra_backend.entity.PurchaseOrder;
import com.inventra.inventra_backend.entity.PurchaseOrderItem;
import com.inventra.inventra_backend.exception.ResourceNotFoundException;
import com.inventra.inventra_backend.mapper.PurchaseOrderItemMapper;
import com.inventra.inventra_backend.repository.ProductRepository;
import com.inventra.inventra_backend.repository.PurchaseOrderItemRepository;
import com.inventra.inventra_backend.repository.PurchaseOrderRepository;
import com.inventra.inventra_backend.sequence.SequenceGeneratorService;
import com.inventra.inventra_backend.service.PurchaseOrderItemService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderItemServiceImpl implements PurchaseOrderItemService {

    private final PurchaseOrderItemRepository purchaseOrderItemRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public PurchaseOrderItemServiceImpl(
            PurchaseOrderItemRepository purchaseOrderItemRepository,
            PurchaseOrderRepository purchaseOrderRepository,
            ProductRepository productRepository,
            SequenceGeneratorService sequenceGeneratorService) {

        this.purchaseOrderItemRepository = purchaseOrderItemRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productRepository = productRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public PurchaseOrderItemResponse createPurchaseOrderItem(PurchaseOrderItemRequest request) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(request.getPurchaseOrderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Order not found."));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found."));

        PurchaseOrderItem item = new PurchaseOrderItem();

        item.setPurchaseOrderItemId(
                sequenceGeneratorService.generateId("POI")
        );

        item.setPurchaseOrder(purchaseOrder);

        item.setProduct(product);

        item.setQuantity(request.getQuantity());

        item.setUnitPrice(request.getUnitPrice());

        item.setTotalPrice(
                request.getUnitPrice().multiply(
                        BigDecimal.valueOf(request.getQuantity())
                )
        );

        item.setActive(request.getActive());

        purchaseOrderItemRepository.save(item);

        recalculatePurchaseOrderTotal(purchaseOrder);

        return PurchaseOrderItemMapper.toResponse(item);
    }

    @Override
    public PurchaseOrderItemResponse updatePurchaseOrderItem(
            String purchaseOrderItemId,
            PurchaseOrderItemRequest request) {

        PurchaseOrderItem item = purchaseOrderItemRepository.findById(purchaseOrderItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Order Item not found."));

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(request.getPurchaseOrderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Order not found."));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found."));

        item.setPurchaseOrder(purchaseOrder);

        item.setProduct(product);

        item.setQuantity(request.getQuantity());

        item.setUnitPrice(request.getUnitPrice());

        item.setTotalPrice(
                request.getUnitPrice().multiply(
                        BigDecimal.valueOf(request.getQuantity())
                )
        );

        item.setActive(request.getActive());

        purchaseOrderItemRepository.save(item);

        recalculatePurchaseOrderTotal(purchaseOrder);

        return PurchaseOrderItemMapper.toResponse(item);
    }

    @Override
    public PurchaseOrderItemResponse getPurchaseOrderItemById(String purchaseOrderItemId) {

        PurchaseOrderItem item = purchaseOrderItemRepository.findById(purchaseOrderItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Order Item not found."));

        return PurchaseOrderItemMapper.toResponse(item);
    }

    @Override
    public List<PurchaseOrderItemResponse> getAllPurchaseOrderItems() {

        return purchaseOrderItemRepository.findByDeletedFalse()
                .stream()
                .map(PurchaseOrderItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseOrderItemResponse> getPurchaseOrderItemsByPurchaseOrder(String purchaseOrderId) {

        return purchaseOrderItemRepository
                .findByPurchaseOrder_PurchaseOrderIdAndDeletedFalse(purchaseOrderId)
                .stream()
                .map(PurchaseOrderItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseOrderItemResponse> getPurchaseOrderItemsByProduct(String productId) {

        return purchaseOrderItemRepository
                .findByProduct_ProductIdAndDeletedFalse(productId)
                .stream()
                .map(PurchaseOrderItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseOrderItemResponse activatePurchaseOrderItem(String purchaseOrderItemId) {

        PurchaseOrderItem item = purchaseOrderItemRepository.findById(purchaseOrderItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Order Item not found."));

        item.setActive(true);

        purchaseOrderItemRepository.save(item);

        recalculatePurchaseOrderTotal(item.getPurchaseOrder());

        return PurchaseOrderItemMapper.toResponse(item);
    }

    @Override
    public PurchaseOrderItemResponse deactivatePurchaseOrderItem(String purchaseOrderItemId) {

        PurchaseOrderItem item = purchaseOrderItemRepository.findById(purchaseOrderItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Order Item not found."));

        item.setActive(false);

        purchaseOrderItemRepository.save(item);

        recalculatePurchaseOrderTotal(item.getPurchaseOrder());

        return PurchaseOrderItemMapper.toResponse(item);
    }

    @Override
    public void deletePurchaseOrderItem(String purchaseOrderItemId) {

        PurchaseOrderItem item = purchaseOrderItemRepository.findById(purchaseOrderItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Order Item not found."));

        item.setDeleted(true);

        purchaseOrderItemRepository.save(item);

        recalculatePurchaseOrderTotal(item.getPurchaseOrder());
    }

    /**
     * Recalculates the total amount of a Purchase Order
     * based on all active and non-deleted items.
     */
    private void recalculatePurchaseOrderTotal(PurchaseOrder purchaseOrder) {

        BigDecimal total = purchaseOrderItemRepository
                .findByPurchaseOrder_PurchaseOrderIdAndDeletedFalse(
                        purchaseOrder.getPurchaseOrderId())
                .stream()
                .filter(PurchaseOrderItem::getActive)
                .map(PurchaseOrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        purchaseOrder.setTotalAmount(total);

        purchaseOrderRepository.save(purchaseOrder);
    }
}
