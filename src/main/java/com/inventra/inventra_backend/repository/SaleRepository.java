package com.inventra.inventra_backend.repository;

import com.inventra.inventra_backend.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, String> {

    List<Sale> findByDeletedFalse();

    @Query("""
            SELECT COALESCE(SUM(s.totalAmount), 0)
            FROM Sale s
            WHERE s.deleted = false
            """)
    BigDecimal getTotalRevenue();

    @Query("""
SELECT
FUNCTION('DATE_FORMAT', s.saleDate, '%Y-%m'),
SUM(s.totalAmount)
FROM Sale s
WHERE s.deleted = false
GROUP BY FUNCTION('DATE_FORMAT', s.saleDate, '%Y-%m')
ORDER BY FUNCTION('DATE_FORMAT', s.saleDate, '%Y-%m')
""")
    List<Object[]> getMonthlyRevenue();
}