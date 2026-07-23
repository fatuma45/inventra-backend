
package com.inventra.inventra_backend.mapper;

import com.inventra.inventra_backend.dto.request.SaleRequest;
import com.inventra.inventra_backend.dto.response.SaleResponse;
import com.inventra.inventra_backend.entity.Sale;

public class SaleMapper {

    private SaleMapper() {
    }

    public static Sale toEntity(SaleRequest request) {

        Sale sale = new Sale();

        sale.setCustomerName(request.getCustomerName());
        sale.setCustomerPhone(request.getCustomerPhone());
        sale.setSaleDate(request.getSaleDate());
        sale.setPaymentMethod(request.getPaymentMethod());
        sale.setPaymentStatus(request.getPaymentStatus());
        sale.setRemarks(request.getRemarks());

        return sale;
    }

    public static SaleResponse toResponse(Sale sale) {

        SaleResponse response = new SaleResponse();

        response.setSaleId(sale.getSaleId());
        response.setCustomerName(sale.getCustomerName());
        response.setCustomerPhone(sale.getCustomerPhone());
        response.setSaleDate(sale.getSaleDate());
        response.setTotalAmount(sale.getTotalAmount());
        response.setPaymentMethod(sale.getPaymentMethod());
        response.setPaymentStatus(sale.getPaymentStatus());
        response.setRemarks(sale.getRemarks());

        if (sale.getSoldBy() != null) {
            response.setSoldBy(sale.getSoldBy().getUserId());
        }

        return response;
    }

}