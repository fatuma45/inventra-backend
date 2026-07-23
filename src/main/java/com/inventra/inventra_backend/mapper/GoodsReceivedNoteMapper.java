package com.inventra.inventra_backend.mapper;

import com.inventra.inventra_backend.dto.request.GoodsReceivedNoteRequest;
import com.inventra.inventra_backend.dto.response.GoodsReceivedNoteResponse;
import com.inventra.inventra_backend.entity.GoodsReceivedNote;

public class GoodsReceivedNoteMapper {

    private GoodsReceivedNoteMapper() {
    }

    public static GoodsReceivedNote toEntity(GoodsReceivedNoteRequest request) {

        GoodsReceivedNote grn = new GoodsReceivedNote();

        grn.setReceivedDate(request.getReceivedDate());
        grn.setRemarks(request.getRemarks());
        grn.setStatus(request.getStatus());
        grn.setActive(request.getActive());

        return grn;
    }

    public static GoodsReceivedNoteResponse toResponse(GoodsReceivedNote entity) {

        GoodsReceivedNoteResponse response = new GoodsReceivedNoteResponse();

        response.setGrnId(entity.getGrnId());

        if (entity.getPurchaseOrder() != null) {
            response.setPurchaseOrderId(entity.getPurchaseOrder().getPurchaseOrderId());
        }

        if (entity.getReceivedBy() != null) {
            response.setReceivedBy(entity.getReceivedBy().getUserId());
        }

        response.setReceivedDate(entity.getReceivedDate());
        response.setRemarks(entity.getRemarks());
        response.setStatus(entity.getStatus());
        response.setActive(entity.getActive());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());

        return response;
    }
}
