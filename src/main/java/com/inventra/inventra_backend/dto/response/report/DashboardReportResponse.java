package com.inventra.inventra_backend.dto.response.report;

import java.math.BigDecimal;

public class DashboardReportResponse {

    private Long totalProducts;
    private Long totalCategories;
    private Long totalSuppliers;
    private Long totalCustomers;
    private Long totalSales;
    private BigDecimal totalRevenue;
    private Long lowStockItems;
    private Long outOfStockItems;

    public DashboardReportResponse() {
    }

    public Long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public Long getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(Long totalCategories) {
        this.totalCategories = totalCategories;
    }

    public Long getTotalSuppliers() {
        return totalSuppliers;
    }

    public void setTotalSuppliers(Long totalSuppliers) {
        this.totalSuppliers = totalSuppliers;
    }

    public Long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(Long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public Long getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Long totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Long getLowStockItems() {
        return lowStockItems;
    }

    public void setLowStockItems(Long lowStockItems) {
        this.lowStockItems = lowStockItems;
    }

    public Long getOutOfStockItems() {
        return outOfStockItems;
    }

    public void setOutOfStockItems(Long outOfStockItems) {
        this.outOfStockItems = outOfStockItems;
    }
}