package com.inventra.inventra_backend.repository;

import com.inventra.inventra_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByProductName(String productName);

    Optional<Product> findBySku(String sku);

    Optional<Product> findByBarcode(String barcode);

    boolean existsByProductName(String productName);

    boolean existsBySku(String sku);

    boolean existsByBarcode(String barcode);
}
