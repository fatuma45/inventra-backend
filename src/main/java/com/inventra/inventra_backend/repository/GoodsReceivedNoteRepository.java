package com.inventra.inventra_backend.repository;

import com.inventra.inventra_backend.entity.GoodsReceivedNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsReceivedNoteRepository extends JpaRepository<GoodsReceivedNote, String> {

    List<GoodsReceivedNote> findByDeletedFalse();

    List<GoodsReceivedNote> findByPurchaseOrder_PurchaseOrderIdAndDeletedFalse(String purchaseOrderId);

    List<GoodsReceivedNote> findByReceivedBy_UserIdAndDeletedFalse(String userId);
}
