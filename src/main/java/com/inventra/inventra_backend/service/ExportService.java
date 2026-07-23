package com.inventra.inventra_backend.service;

import org.springframework.core.io.ByteArrayResource;

public interface ExportService {

    ByteArrayResource exportProductsToExcel();

    ByteArrayResource exportProductsToPdf();

    ByteArrayResource exportInventoryToExcel();

    ByteArrayResource exportInventoryToPdf();

    ByteArrayResource exportSalesToExcel();

    ByteArrayResource exportSalesToPdf();

}
