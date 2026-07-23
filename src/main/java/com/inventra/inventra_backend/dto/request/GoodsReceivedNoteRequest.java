package com.inventra.inventra_backend.dto.request;

import com.inventra.inventra_backend.entity.enums.GoodsReceivedNoteStatus;

import java.time.LocalDate;

public class GoodsReceivedNoteRequest {

    private String purchaseOrderId;

    private LocalDate receivedDate;

    private String receivedBy;

    private String remarks;

    private GoodsReceivedNoteStatus status;

    private Boolean active;

    public GoodsReceivedNoteRequest() {
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public GoodsReceivedNoteStatus getStatus() {
        return status;
    }

    public void setStatus(GoodsReceivedNoteStatus status) {
        this.status = status;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}