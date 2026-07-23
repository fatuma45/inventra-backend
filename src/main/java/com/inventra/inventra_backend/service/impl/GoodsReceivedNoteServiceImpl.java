package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.request.GoodsReceivedNoteRequest;
import com.inventra.inventra_backend.dto.response.GoodsReceivedNoteResponse;
import com.inventra.inventra_backend.entity.GoodsReceivedNote;
import com.inventra.inventra_backend.entity.PurchaseOrder;
import com.inventra.inventra_backend.entity.User;
import com.inventra.inventra_backend.mapper.GoodsReceivedNoteMapper;
import com.inventra.inventra_backend.repository.GoodsReceivedNoteRepository;
import com.inventra.inventra_backend.repository.PurchaseOrderRepository;
import com.inventra.inventra_backend.repository.UserRepository;
import com.inventra.inventra_backend.service.GoodsReceivedNoteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsReceivedNoteServiceImpl implements GoodsReceivedNoteService {

    private final GoodsReceivedNoteRepository repository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final UserRepository userRepository;

    public GoodsReceivedNoteServiceImpl(
            GoodsReceivedNoteRepository repository,
            PurchaseOrderRepository purchaseOrderRepository,
            UserRepository userRepository
    ) {
        this.repository = repository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public GoodsReceivedNoteResponse create(GoodsReceivedNoteRequest request) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(request.getPurchaseOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Purchase Order not found"));

        User user = userRepository.findById(request.getReceivedBy())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        GoodsReceivedNote grn = GoodsReceivedNoteMapper.toEntity(request);

        grn.setGrnId(String.format("GRN%03d", repository.count() + 1));
        grn.setPurchaseOrder(purchaseOrder);
        grn.setReceivedBy(user);

        return GoodsReceivedNoteMapper.toResponse(repository.save(grn));
    }

    @Override
    public GoodsReceivedNoteResponse getById(String grnId) {

        GoodsReceivedNote grn = repository.findById(grnId)
                .orElseThrow(() -> new EntityNotFoundException("GRN not found"));

        return GoodsReceivedNoteMapper.toResponse(grn);
    }

    @Override
    public List<GoodsReceivedNoteResponse> getAll() {

        return repository.findByDeletedFalse()
                .stream()
                .map(GoodsReceivedNoteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<GoodsReceivedNoteResponse> getByPurchaseOrder(String purchaseOrderId) {

        return repository.findByPurchaseOrder_PurchaseOrderIdAndDeletedFalse(purchaseOrderId)
                .stream()
                .map(GoodsReceivedNoteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<GoodsReceivedNoteResponse> getByUser(String userId) {

        return repository.findByReceivedBy_UserIdAndDeletedFalse(userId)
                .stream()
                .map(GoodsReceivedNoteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public GoodsReceivedNoteResponse update(String grnId, GoodsReceivedNoteRequest request) {

        GoodsReceivedNote grn = repository.findById(grnId)
                .orElseThrow(() -> new EntityNotFoundException("GRN not found"));

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(request.getPurchaseOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Purchase Order not found"));

        User user = userRepository.findById(request.getReceivedBy())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        grn.setPurchaseOrder(purchaseOrder);
        grn.setReceivedBy(user);
        grn.setReceivedDate(request.getReceivedDate());
        grn.setRemarks(request.getRemarks());
        grn.setStatus(request.getStatus());

        if (request.getActive() != null) {
            grn.setActive(request.getActive());
        }

        return GoodsReceivedNoteMapper.toResponse(repository.save(grn));
    }

    @Override
    public void activate(String grnId) {

        GoodsReceivedNote grn = repository.findById(grnId)
                .orElseThrow(() -> new EntityNotFoundException("GRN not found"));

        grn.setActive(true);

        repository.save(grn);
    }

    @Override
    public void deactivate(String grnId) {

        GoodsReceivedNote grn = repository.findById(grnId)
                .orElseThrow(() -> new EntityNotFoundException("GRN not found"));

        grn.setActive(false);

        repository.save(grn);
    }

    @Override
    public void delete(String grnId) {

        GoodsReceivedNote grn = repository.findById(grnId)
                .orElseThrow(() -> new EntityNotFoundException("GRN not found"));

        grn.setDeleted(true);

        repository.save(grn);
    }
}