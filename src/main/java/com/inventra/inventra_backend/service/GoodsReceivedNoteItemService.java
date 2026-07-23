package com.inventra.inventra_backend.service;

import com.inventra.inventra_backend.dto.request.GoodsReceivedNoteItemRequest;
import com.inventra.inventra_backend.dto.response.GoodsReceivedNoteItemResponse;

import java.util.List;

public interface GoodsReceivedNoteItemService {

    GoodsReceivedNoteItemResponse create(GoodsReceivedNoteItemRequest request);

    GoodsReceivedNoteItemResponse getById(String grnItemId);

    List<GoodsReceivedNoteItemResponse> getAll();

    List<GoodsReceivedNoteItemResponse> getByGrn(String grnId);

    List<GoodsReceivedNoteItemResponse> getByProduct(String productId);

    GoodsReceivedNoteItemResponse update(String grnItemId, GoodsReceivedNoteItemRequest request);

    void delete(String grnItemId);
}
