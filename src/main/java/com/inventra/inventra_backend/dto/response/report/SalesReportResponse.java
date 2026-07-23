package com.inventra.inventra_backend.dto.response.report;

import java.math.BigDecimal;

public class SalesReportResponse {

    private String saleId;
    private String customerName;
    private BigDecimal totalAmount;

    public SalesReportResponse() {
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
