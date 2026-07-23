package com.inventra.inventra_backend.service;

import com.inventra.inventra_backend.dto.request.SaleItemRequest;
import com.inventra.inventra_backend.dto.response.SaleItemResponse;

import java.util.List;

public interface SaleItemService {

    SaleItemResponse create(String saleId, SaleItemRequest request);

    SaleItemResponse getById(String saleItemId);

    List<SaleItemResponse> getAll();

    List<SaleItemResponse> getBySale(String saleId);

    SaleItemResponse update(
            String saleId,
            String saleItemId,
            SaleItemRequest request
    );

    void delete(String saleItemId);

}