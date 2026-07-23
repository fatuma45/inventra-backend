package com.inventra.inventra_backend.repository;

import com.inventra.inventra_backend.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleItemRepository extends JpaRepository<SaleItem, String> {

    List<SaleItem> findByDeletedFalse();

    List<SaleItem> findBySale_SaleIdAndDeletedFalse(String saleId);

}
