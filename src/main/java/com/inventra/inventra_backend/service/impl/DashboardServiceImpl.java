package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.dto.response.report.DashboardSummaryResponse;
import com.inventra.inventra_backend.repository.InventoryRepository;
import com.inventra.inventra_backend.repository.ProductRepository;
import com.inventra.inventra_backend.repository.PurchaseOrderRepository;
import com.inventra.inventra_backend.repository.SaleRepository;
import com.inventra.inventra_backend.repository.SupplierRepository;
import com.inventra.inventra_backend.service.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final SaleRepository saleRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final InventoryRepository inventoryRepository;

    public DashboardServiceImpl(
            ProductRepository productRepository,
            SupplierRepository supplierRepository,
            SaleRepository saleRepository,
            PurchaseOrderRepository purchaseOrderRepository,
            InventoryRepository inventoryRepository) {

        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.saleRepository = saleRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public DashboardSummaryResponse getSummary() {

        DashboardSummaryResponse response = new DashboardSummaryResponse();

        response.setTotalProducts(productRepository.count());

        response.setTotalSuppliers(supplierRepository.count());

        response.setTotalSales(
                (long) saleRepository.findByDeletedFalse().size()
        );

        response.setTotalPurchaseOrders(
                (long) purchaseOrderRepository.findByDeletedFalse().size()
        );

        response.setTotalRevenue(
                saleRepository.getTotalRevenue()
        );

        response.setLowStockItems(
                inventoryRepository.countLowStockItems()
        );

        return response;
    }
}