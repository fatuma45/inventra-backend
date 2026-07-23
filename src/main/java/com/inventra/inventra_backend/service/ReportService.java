package com.inventra.inventra_backend.service;

import com.inventra.inventra_backend.dto.response.report.DashboardChartResponse;
import com.inventra.inventra_backend.dto.response.report.DashboardSummaryResponse;

import java.util.List;

public interface ReportService {

    DashboardSummaryResponse getDashboardSummary();

    List<DashboardChartResponse> getMonthlySales();

    List<DashboardChartResponse> getMonthlyRevenue();

    List<DashboardChartResponse> getTopSellingProducts();

    List<DashboardChartResponse> getLowStockItems();

    List<DashboardChartResponse> getStockMovementHistory();
}