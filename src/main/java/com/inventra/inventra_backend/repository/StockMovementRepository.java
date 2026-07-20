package com.inventra.inventra_backend.repository;

import com.inventra.inventra_backend.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, String> {

    List<StockMovement> findByProduct_ProductIdOrderByMovementDateDesc(String productId);

    List<StockMovement> findByInventory_InventoryIdOrderByMovementDateDesc(String inventoryId);
}