package com.inventra.inventra_backend.dto.response;

import com.inventra.inventra_backend.entity.enums.GoodsReceivedNoteStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GoodsReceivedNoteResponse {

    private String grnId;

    private String purchaseOrderId;

    private LocalDate receivedDate;

    private String receivedBy;

    private String remarks;

    private GoodsReceivedNoteStatus status;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public GoodsReceivedNoteResponse() {
    }

    public String getGrnId() {
        return grnId;
    }

    public void setGrnId(String grnId) {
        this.grnId = grnId;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
