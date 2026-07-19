package com.inventra.inventra_backend.mapper;

import com.inventra.inventra_backend.dto.response.SupplierResponse;
import com.inventra.inventra_backend.entity.Supplier;

public class SupplierMapper {

    private SupplierMapper() {
    }

    public static SupplierResponse toResponse(Supplier supplier) {

        SupplierResponse response = new SupplierResponse();

        response.setSupplierId(supplier.getSupplierId());
        response.setSupplierName(supplier.getSupplierName());
        response.setContactPerson(supplier.getContactPerson());
        response.setEmail(supplier.getEmail());
        response.setPhoneNumber(supplier.getPhoneNumber());
        response.setAddress(supplier.getAddress());
        response.setTaxPin(supplier.getTaxPin());
        response.setPaymentTerms(supplier.getPaymentTerms());
        response.setWebsite(supplier.getWebsite());
        response.setNotes(supplier.getNotes());
        response.setActive(supplier.getActive());

        return response;
    }
}
