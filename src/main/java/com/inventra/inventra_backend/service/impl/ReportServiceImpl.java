package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.response.report.DashboardChartResponse;
import com.inventra.inventra_backend.dto.response.report.DashboardSummaryResponse;
import com.inventra.inventra_backend.repository.*;
import com.inventra.inventra_backend.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final SaleRepository saleRepository;
    private final InventoryRepository inventoryRepository;
    private final StockMovementRepository stockMovementRepository;

    public ReportServiceImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            SupplierRepository supplierRepository,
            SaleRepository saleRepository,
            InventoryRepository inventoryRepository,
            StockMovementRepository stockMovementRepository) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.saleRepository = saleRepository;
        this.inventoryRepository = inventoryRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    @Override
    public DashboardSummaryResponse getDashboardSummary() {

        DashboardSummaryResponse summary = new DashboardSummaryResponse();

        summary.setTotalProducts(productRepository.count());

        summary.setTotalCategories(categoryRepository.count());

        summary.setTotalSuppliers(supplierRepository.count());

        summary.setTotalSales(saleRepository.count());

        summary.setTotalRevenue(saleRepository.getTotalRevenue());

        summary.setLowStockItems(inventoryRepository.countLowStockItems());

        summary.setOutOfStockItems(inventoryRepository.countOutOfStockItems());

        return summary;
    }

    @Override
    public List<DashboardChartResponse> getMonthlySales() {
        return null;
    }

    @Override
    public List<DashboardChartResponse> getMonthlyRevenue() {

        return saleRepository.getMonthlyRevenue()
                .stream()
                .map(row -> new DashboardChartResponse(
                        row[0].toString(),
                        (java.math.BigDecimal) row[1]
                ))
                .toList();
    }

    @Override
    public List<DashboardChartResponse> getTopSellingProducts() {
        return null;
    }

    @Override
    public List<DashboardChartResponse> getLowStockItems() {
        return null;
    }

    @Override
    public List<DashboardChartResponse> getStockMovementHistory() {
        return null;
    }
}
