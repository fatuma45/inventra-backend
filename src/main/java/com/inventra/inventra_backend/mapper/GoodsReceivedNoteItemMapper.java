package com.inventra.inventra_backend.mapper;

import com.inventra.inventra_backend.dto.request.GoodsReceivedNoteItemRequest;
import com.inventra.inventra_backend.dto.response.GoodsReceivedNoteItemResponse;
import com.inventra.inventra_backend.entity.GoodsReceivedNoteItem;

import java.math.BigDecimal;

public class GoodsReceivedNoteItemMapper {

    private GoodsReceivedNoteItemMapper() {
    }

    public static GoodsReceivedNoteItem toEntity(GoodsReceivedNoteItemRequest request) {

        GoodsReceivedNoteItem item = new GoodsReceivedNoteItem();

        item.setQuantityReceived(request.getQuantityReceived());
        item.setUnitCost(request.getUnitCost());

        BigDecimal total = request.getUnitCost()
                .multiply(BigDecimal.valueOf(request.getQuantityReceived()));

        item.setTotalCost(total);

        item.setRemarks(request.getRemarks());

        return item;
    }

    public static GoodsReceivedNoteItemResponse toResponse(GoodsReceivedNoteItem entity) {

        GoodsReceivedNoteItemResponse response = new GoodsReceivedNoteItemResponse();

        response.setGrnItemId(entity.getGrnItemId());

        if (entity.getGoodsReceivedNote() != null) {
            response.setGrnId(entity.getGoodsReceivedNote().getGrnId());
        }

        if (entity.getProduct() != null) {
            response.setProductId(entity.getProduct().getProductId());
            response.setProductName(entity.getProduct().getProductName());
        }

        response.setQuantityReceived(entity.getQuantityReceived());
        response.setUnitCost(entity.getUnitCost());
        response.setTotalCost(entity.getTotalCost());
        response.setRemarks(entity.getRemarks());

        return response;
    }
}