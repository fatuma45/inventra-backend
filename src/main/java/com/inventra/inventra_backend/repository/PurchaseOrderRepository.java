package com.inventra.inventra_backend.repository;

import com.inventra.inventra_backend.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, String> {

    List<PurchaseOrder> findByDeletedFalse();

    List<PurchaseOrder> findBySupplier_SupplierId(String supplierId);
}
