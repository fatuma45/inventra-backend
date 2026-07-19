package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.dto.request.SupplierRequest;
import com.inventra.inventra_backend.dto.response.SupplierResponse;
import com.inventra.inventra_backend.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierResponse createSupplier(@Valid @RequestBody SupplierRequest request) {
        return supplierService.createSupplier(request);
    }

    @GetMapping
    public List<SupplierResponse> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{supplierId}")
    public SupplierResponse getSupplierById(@PathVariable String supplierId) {
        return supplierService.getSupplierById(supplierId);
    }

    @PutMapping("/{supplierId}")
    public SupplierResponse updateSupplier(
            @PathVariable String supplierId,
            @Valid @RequestBody SupplierRequest request) {

        return supplierService.updateSupplier(supplierId, request);
    }

    @PatchMapping("/{supplierId}/activate")
    public SupplierResponse activateSupplier(@PathVariable String supplierId) {
        return supplierService.activateSupplier(supplierId);
    }

    @PatchMapping("/{supplierId}/deactivate")
    public SupplierResponse deactivateSupplier(@PathVariable String supplierId) {
        return supplierService.deactivateSupplier(supplierId);
    }

    @DeleteMapping("/{supplierId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSupplier(@PathVariable String supplierId) {
        supplierService.deleteSupplier(supplierId);
    }
}
