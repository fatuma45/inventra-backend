package com.inventra.inventra_backend.service;

import com.inventra.inventra_backend.dto.request.SupplierRequest;
import com.inventra.inventra_backend.dto.response.SupplierResponse;

import java.util.List;

public interface SupplierService {

    SupplierResponse createSupplier(SupplierRequest request);

    SupplierResponse updateSupplier(String supplierId, SupplierRequest request);

    SupplierResponse getSupplierById(String supplierId);

    List<SupplierResponse> getAllSuppliers();

    SupplierResponse activateSupplier(String supplierId);

    SupplierResponse deactivateSupplier(String supplierId);

    void deleteSupplier(String supplierId);
}