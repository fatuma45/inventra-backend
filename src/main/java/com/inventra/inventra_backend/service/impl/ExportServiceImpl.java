package com.inventra.inventra_backend.service.impl;

import com.inventra.inventra_backend.entity.Product;
import com.inventra.inventra_backend.util.ExcelExporter;
import com.inventra.inventra_backend.util.PdfExporter;
import com.inventra.inventra_backend.repository.InventoryRepository;
import com.inventra.inventra_backend.repository.ProductRepository;
import com.inventra.inventra_backend.repository.SaleRepository;
import com.inventra.inventra_backend.service.ExportService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final SaleRepository saleRepository;

    public ExportServiceImpl(
            ProductRepository productRepository,
            InventoryRepository inventoryRepository,
            SaleRepository saleRepository) {

        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public ByteArrayResource exportProductsToExcel() {

        List<String> headers = Arrays.asList(
                "Product ID",
                "Product Name",
                "SKU",
                "Selling Price"
        );

        List<List<String>> rows = new ArrayList<>();

        for (Product product : productRepository.findAll()) {

            rows.add(Arrays.asList(
                    product.getProductId(),
                    product.getProductName(),
                    product.getSku(),
                    product.getSellingPrice().toString()
            ));
        }

        byte[] data = ExcelExporter.export(
                "Products",
                headers,
                rows
        );

        return new ByteArrayResource(data);
    }

    @Override
    public ByteArrayResource exportProductsToPdf() {

        List<String> headers = Arrays.asList(
                "Product ID",
                "Product Name",
                "SKU",
                "Selling Price"
        );

        List<List<String>> rows = new ArrayList<>();

        for (Product product : productRepository.findAll()) {

            rows.add(Arrays.asList(
                    product.getProductId(),
                    product.getProductName(),
                    product.getSku(),
                    product.getSellingPrice().toString()
            ));
        }

        byte[] data = PdfExporter.export(
                "Products Report",
                headers,
                rows
        );

        return new ByteArrayResource(data);
    }

    @Override
    public ByteArrayResource exportInventoryToExcel() {
        throw new UnsupportedOperationException("Coming next");
    }

    @Override
    public ByteArrayResource exportInventoryToPdf() {
        throw new UnsupportedOperationException("Coming next");
    }

    @Override
    public ByteArrayResource exportSalesToExcel() {
        throw new UnsupportedOperationException("Coming next");
    }

    @Override
    public ByteArrayResource exportSalesToPdf() {
        throw new UnsupportedOperationException("Coming next");
    }
}
