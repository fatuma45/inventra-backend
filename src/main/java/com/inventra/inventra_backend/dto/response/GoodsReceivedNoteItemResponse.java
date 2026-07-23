package com.inventra.inventra_backend.dto.response;

import java.math.BigDecimal;

public class GoodsReceivedNoteItemResponse {

    private String grnItemId;
    private String grnId;
    private String productId;
    private String productName;
    private Integer quantityReceived;
    private BigDecimal unitCost;
    private BigDecimal totalCost;
    private String remarks;

    public GoodsReceivedNoteItemResponse() {
    }

    public String getGrnItemId() {
        return grnItemId;
    }

    public void setGrnItemId(String grnItemId) {
        this.grnItemId = grnItemId;
    }

    public String getGrnId() {
        return grnId;
    }

    public void setGrnId(String grnId) {
        this.grnId = grnId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantityReceived() {
        return quantityReceived;
    }

    public void setQuantityReceived(Integer quantityReceived) {
        this.quantityReceived = quantityReceived;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
