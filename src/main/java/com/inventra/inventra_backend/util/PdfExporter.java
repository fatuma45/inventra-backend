package com.inventra.inventra_backend.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfExporter {

    private PdfExporter() {
    }

    public static byte[] export(
            String title,
            List<String> headers,
            List<List<String>> rows) {

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Document document = new Document(PageSize.A4);

            PdfWriter.getInstance(document, outputStream);

            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);

            Paragraph paragraph = new Paragraph(title, titleFont);

            paragraph.setSpacingAfter(20);

            paragraph.setAlignment(Element.ALIGN_CENTER);

            document.add(paragraph);

            PdfPTable table = new PdfPTable(headers.size());

            table.setWidthPercentage(100);

            // Header Row
            for (String header : headers) {

                PdfPCell cell = new PdfPCell(new Phrase(header));

                cell.setHorizontalAlignment(Element.ALIGN_CENTER);

                table.addCell(cell);
            }

            // Data Rows
            for (List<String> row : rows) {

                for (String value : row) {

                    table.addCell(value);

                }
            }

            document.add(table);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception ex) {

            throw new RuntimeException(ex);

        }
    }
}