package com.inventra.inventra_backend.service;

import com.inventra.inventra_backend.dto.request.GoodsReceivedNoteRequest;
import com.inventra.inventra_backend.dto.response.GoodsReceivedNoteResponse;

import java.util.List;

public interface GoodsReceivedNoteService {

    GoodsReceivedNoteResponse create(GoodsReceivedNoteRequest request);

    GoodsReceivedNoteResponse getById(String grnId);

    List<GoodsReceivedNoteResponse> getAll();

    List<GoodsReceivedNoteResponse> getByPurchaseOrder(String purchaseOrderId);

    List<GoodsReceivedNoteResponse> getByUser(String userId);

    GoodsReceivedNoteResponse update(String grnId, GoodsReceivedNoteRequest request);

    void activate(String grnId);

    void deactivate(String grnId);

    void delete(String grnId);
}