package com.inventra.inventra_backend.repository;

import com.inventra.inventra_backend.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {

    Optional<Supplier> findBySupplierName(String supplierName);

    boolean existsBySupplierName(String supplierName);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByTaxPin(String taxPin);
}