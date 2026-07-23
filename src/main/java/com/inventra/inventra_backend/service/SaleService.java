package com.inventra.inventra_backend.service;

import com.inventra.inventra_backend.dto.request.SaleRequest;
import com.inventra.inventra_backend.dto.response.SaleResponse;

import java.util.List;

public interface SaleService {

    SaleResponse create(SaleRequest request);

    SaleResponse getById(String saleId);

    List<SaleResponse> getAll();

    SaleResponse update(String saleId, SaleRequest request);

    void delete(String saleId);

}
