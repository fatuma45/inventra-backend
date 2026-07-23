package com.inventra.inventra_backend.repository;

import com.inventra.inventra_backend.entity.GoodsReceivedNoteItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsReceivedNoteItemRepository extends JpaRepository<GoodsReceivedNoteItem, String> {

    List<GoodsReceivedNoteItem> findByDeletedFalse();

    List<GoodsReceivedNoteItem> findByGoodsReceivedNote_GrnIdAndDeletedFalse(String grnId);

    List<GoodsReceivedNoteItem> findByProduct_ProductIdAndDeletedFalse(String productId);
}
