package com.inventra.inventra_backend.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ExcelExporter {

    private ExcelExporter() {
    }

    public static byte[] export(
            String sheetName,
            List<String> headers,
            List<List<String>> rows) {

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet(sheetName);

            CellStyle headerStyle = workbook.createCellStyle();

            Font font = workbook.createFont();
            font.setBold(true);

            headerStyle.setFont(font);

            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < headers.size(); i++) {

                Cell cell = headerRow.createCell(i);

                cell.setCellValue(headers.get(i));

                cell.setCellStyle(headerStyle);
            }

            int rowIndex = 1;

            for (List<String> rowData : rows) {

                Row row = sheet.createRow(rowIndex++);

                for (int i = 0; i < rowData.size(); i++) {

                    row.createCell(i)
                            .setCellValue(rowData.get(i));
                }
            }

            for (int i = 0; i < headers.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            workbook.write(outputStream);

            return outputStream.toByteArray();

        } catch (Exception ex) {

            throw new RuntimeException(ex);

        }
    }
}