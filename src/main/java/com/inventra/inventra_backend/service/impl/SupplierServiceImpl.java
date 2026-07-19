package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.request.SupplierRequest;
import com.inventra.inventra_backend.dto.response.SupplierResponse;
import com.inventra.inventra_backend.entity.Supplier;
import com.inventra.inventra_backend.exception.DuplicateResourceException;
import com.inventra.inventra_backend.exception.ResourceNotFoundException;
import com.inventra.inventra_backend.mapper.SupplierMapper;
import com.inventra.inventra_backend.repository.SupplierRepository;
import com.inventra.inventra_backend.sequence.SequenceGeneratorService;
import com.inventra.inventra_backend.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public SupplierServiceImpl(
            SupplierRepository supplierRepository,
            SequenceGeneratorService sequenceGeneratorService) {

        this.supplierRepository = supplierRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public SupplierResponse createSupplier(SupplierRequest request) {

        if (supplierRepository.existsBySupplierName(request.getSupplierName())) {
            throw new DuplicateResourceException("Supplier name already exists.");
        }

        if (request.getEmail() != null &&
                !request.getEmail().isBlank() &&
                supplierRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException("Email already exists.");
        }

        if (request.getPhoneNumber() != null &&
                !request.getPhoneNumber().isBlank() &&
                supplierRepository.existsByPhoneNumber(request.getPhoneNumber())) {

            throw new DuplicateResourceException("Phone number already exists.");
        }

        if (request.getTaxPin() != null &&
                !request.getTaxPin().isBlank() &&
                supplierRepository.existsByTaxPin(request.getTaxPin())) {

            throw new DuplicateResourceException("Tax PIN already exists.");
        }

        Supplier supplier = new Supplier();

        supplier.setSupplierId(sequenceGeneratorService.generateId("SUP"));
        supplier.setSupplierName(request.getSupplierName());
        supplier.setContactPerson(request.getContactPerson());
        supplier.setEmail(request.getEmail());
        supplier.setPhoneNumber(request.getPhoneNumber());
        supplier.setAddress(request.getAddress());
        supplier.setTaxPin(request.getTaxPin());
        supplier.setPaymentTerms(request.getPaymentTerms());
        supplier.setWebsite(request.getWebsite());
        supplier.setNotes(request.getNotes());
        supplier.setActive(request.getActive());

        supplierRepository.save(supplier);

        return SupplierMapper.toResponse(supplier);
    }

    @Override
    public SupplierResponse updateSupplier(String supplierId, SupplierRequest request) {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier not found."));

        if (!supplier.getSupplierName().equals(request.getSupplierName())
                && supplierRepository.existsBySupplierName(request.getSupplierName())) {

            throw new DuplicateResourceException("Supplier name already exists.");
        }

        supplier.setSupplierName(request.getSupplierName());
        supplier.setContactPerson(request.getContactPerson());
        supplier.setEmail(request.getEmail());
        supplier.setPhoneNumber(request.getPhoneNumber());
        supplier.setAddress(request.getAddress());
        supplier.setTaxPin(request.getTaxPin());
        supplier.setPaymentTerms(request.getPaymentTerms());
        supplier.setWebsite(request.getWebsite());
        supplier.setNotes(request.getNotes());
        supplier.setActive(request.getActive());

        supplierRepository.save(supplier);

        return SupplierMapper.toResponse(supplier);
    }

    @Override
    public SupplierResponse getSupplierById(String supplierId) {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier not found."));

        return SupplierMapper.toResponse(supplier);
    }

    @Override
    public List<SupplierResponse> getAllSuppliers() {

        return supplierRepository.findAll()
                .stream()
                .filter(supplier -> !supplier.getDeleted())
                .map(SupplierMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SupplierResponse activateSupplier(String supplierId) {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier not found."));

        supplier.setActive(true);

        supplierRepository.save(supplier);

        return SupplierMapper.toResponse(supplier);
    }

    @Override
    public SupplierResponse deactivateSupplier(String supplierId) {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier not found."));

        supplier.setActive(false);

        supplierRepository.save(supplier);

        return SupplierMapper.toResponse(supplier);
    }

    @Override
    public void deleteSupplier(String supplierId) {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier not found."));

        supplier.setDeleted(true);
        supplier.setActive(false);

        supplierRepository.save(supplier);
    }
}