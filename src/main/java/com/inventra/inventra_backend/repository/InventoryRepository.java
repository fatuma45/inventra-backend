package com.inventra.inventra_backend.repository;

import com.inventra.inventra_backend.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {

    Optional<Inventory> findByProduct_ProductId(String productId);

    boolean existsByProduct_ProductId(String productId);

    @Query("""
            SELECT COUNT(i)
            FROM Inventory i
            WHERE i.quantityOnHand <= i.minimumStock
            AND i.deleted = false
            """)
    Long countLowStockItems();

    @Query("""
            SELECT COUNT(i)
            FROM Inventory i
            WHERE i.quantityOnHand = 0
            AND i.deleted = false
            """)
    Long countOutOfStockItems();

}