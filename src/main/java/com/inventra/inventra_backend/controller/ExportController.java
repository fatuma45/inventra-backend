package com.inventra.inventra_backend.controller;

import com.inventra.inventra_backend.service.ExportService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/export")
@CrossOrigin(origins = "*")
public class ExportController {

    private final ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping("/products/excel")
    public ResponseEntity<ByteArrayResource> exportProductsExcel() {

        ByteArrayResource file = exportService.exportProductsToExcel();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Products.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.contentLength())
                .body(file);
    }

    @GetMapping("/products/pdf")
    public ResponseEntity<ByteArrayResource> exportProductsPdf() {

        ByteArrayResource file = exportService.exportProductsToPdf();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Products.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(file.contentLength())
                .body(file);
    }
}
