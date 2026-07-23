package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.dto.response.report.DashboardChartResponse;
import com.inventra.inventra_backend.dto.response.report.DashboardSummaryResponse;
import com.inventra.inventra_backend.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/dashboard")
    public DashboardSummaryResponse dashboard() {
        return reportService.getDashboardSummary();
    }

    @GetMapping("/monthly-revenue")
    public List<DashboardChartResponse> monthlyRevenue() {
        return reportService.getMonthlyRevenue();
    }

    @GetMapping("/monthly-sales")
    public List<DashboardChartResponse> monthlySales() {
        return reportService.getMonthlySales();
    }

    @GetMapping("/top-products")
    public List<DashboardChartResponse> topProducts() {
        return reportService.getTopSellingProducts();
    }

    @GetMapping("/low-stock")
    public List<DashboardChartResponse> lowStock() {
        return reportService.getLowStockItems();
    }

    @GetMapping("/stock-history")
    public List<DashboardChartResponse> stockHistory() {
        return reportService.getStockMovementHistory();
    }
}