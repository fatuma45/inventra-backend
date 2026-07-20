package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.request.PurchaseOrderRequest;
import com.inventra.inventra_backend.dto.response.PurchaseOrderResponse;
import com.inventra.inventra_backend.entity.PurchaseOrder;
import com.inventra.inventra_backend.entity.Supplier;
import com.inventra.inventra_backend.entity.User;
import com.inventra.inventra_backend.exception.ResourceNotFoundException;
import com.inventra.inventra_backend.mapper.PurchaseOrderMapper;
import com.inventra.inventra_backend.repository.PurchaseOrderRepository;
import com.inventra.inventra_backend.repository.SupplierRepository;
import com.inventra.inventra_backend.repository.UserRepository;
import com.inventra.inventra_backend.sequence.SequenceGeneratorService;
import com.inventra.inventra_backend.service.PurchaseOrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public PurchaseOrderServiceImpl(
            PurchaseOrderRepository purchaseOrderRepository,
            SupplierRepository supplierRepository,
            UserRepository userRepository,
            SequenceGeneratorService sequenceGeneratorService) {

        this.purchaseOrderRepository = purchaseOrderRepository;
        this.supplierRepository = supplierRepository;
        this.userRepository = userRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public PurchaseOrderResponse createPurchaseOrder(PurchaseOrderRequest request) {

        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier not found."));

        User user = userRepository.findById(request.getCreatedBy())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseOrder.setPurchaseOrderId(
                sequenceGeneratorService.generateId("PO")
        );

        purchaseOrder.setSupplier(supplier);

        purchaseOrder.setOrderDate(request.getOrderDate());

        purchaseOrder.setExpectedDeliveryDate(
                request.getExpectedDeliveryDate()
        );

        purchaseOrder.setStatus(request.getStatus());

        purchaseOrder.setNotes(request.getNotes());

        purchaseOrder.setCreatedBy(user);

        purchaseOrder.setActive(request.getActive());

        purchaseOrderRepository.save(purchaseOrder);

        return PurchaseOrderMapper.toResponse(purchaseOrder);
    }

    @Override
    public PurchaseOrderResponse updatePurchaseOrder(
            String purchaseOrderId,
            PurchaseOrderRequest request) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Order not found."));

        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier not found."));

        User user = userRepository.findById(request.getCreatedBy())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        purchaseOrder.setSupplier(supplier);

        purchaseOrder.setOrderDate(request.getOrderDate());

        purchaseOrder.setExpectedDeliveryDate(
                request.getExpectedDeliveryDate()
        );

        purchaseOrder.setStatus(request.getStatus());

        purchaseOrder.setNotes(request.getNotes());

        purchaseOrder.setCreatedBy(user);

        purchaseOrder.setActive(request.getActive());

        purchaseOrderRepository.save(purchaseOrder);

        return PurchaseOrderMapper.toResponse(purchaseOrder);
    }

    @Override
    public PurchaseOrderResponse getPurchaseOrderById(String purchaseOrderId) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Order not found."));

        return PurchaseOrderMapper.toResponse(purchaseOrder);
    }

    @Override
    public List<PurchaseOrderResponse> getAllPurchaseOrders() {

        return purchaseOrderRepository.findByDeletedFalse()
                .stream()
                .map(PurchaseOrderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseOrderResponse> getPurchaseOrdersBySupplier(String supplierId) {

        return purchaseOrderRepository.findBySupplier_SupplierId(supplierId)
                .stream()
                .map(PurchaseOrderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseOrderResponse activatePurchaseOrder(String purchaseOrderId) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Order not found."));

        purchaseOrder.setActive(true);

        purchaseOrderRepository.save(purchaseOrder);

        return PurchaseOrderMapper.toResponse(purchaseOrder);
    }

    @Override
    public PurchaseOrderResponse deactivatePurchaseOrder(String purchaseOrderId) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Order not found."));

        purchaseOrder.setActive(false);

        purchaseOrderRepository.save(purchaseOrder);

        return PurchaseOrderMapper.toResponse(purchaseOrder);
    }

    @Override
    public void deletePurchaseOrder(String purchaseOrderId) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase Order not found."));

        purchaseOrder.setDeleted(true);

        purchaseOrderRepository.save(purchaseOrder);
    }
}
