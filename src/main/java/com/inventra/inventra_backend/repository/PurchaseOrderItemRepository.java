package com.inventra.inventra_backend.repository;

import com.inventra.inventra_backend.entity.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, String> {

    List<PurchaseOrderItem> findByDeletedFalse();

    List<PurchaseOrderItem> findByPurchaseOrder_PurchaseOrderIdAndDeletedFalse(String purchaseOrderId);

    List<PurchaseOrderItem> findByProduct_ProductIdAndDeletedFalse(String productId);
}
