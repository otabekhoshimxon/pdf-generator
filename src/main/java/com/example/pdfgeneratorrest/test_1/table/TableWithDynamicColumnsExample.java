package com.example.pdfgeneratorrest.test_1.table;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.Color;
import java.io.IOException;

public class TableWithDynamicColumnsExample {
    public static void main(String[] args) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        float margin = 50;
        float yStart = page.getMediaBox().getHeight() - margin;
        float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
        int cols = 5; // Qo'shilgan ustun
        float colWidth = tableWidth / cols;
        float rowHeight = 20f;
        int rows = 5;

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setLineWidth(1f);
            contentStream.setStrokingColor(Color.BLACK);

            // Jadval chegaralari
            contentStream.addRect(margin, yStart - rowHeight * rows, tableWidth, rowHeight * rows);
            contentStream.stroke();

            // Qator va ustun chiziqlari
            for (int i = 1; i < rows; i++) {
                float y = yStart - i * rowHeight;
                contentStream.moveTo(margin, y);
                contentStream.lineTo(margin + tableWidth, y);
                contentStream.stroke();
            }
            for (int i = 1; i < cols; i++) {
                float x = margin + i * colWidth;
                contentStream.moveTo(x, yStart);
                contentStream.lineTo(x, yStart - rowHeight * rows);
                contentStream.stroke();
            }

            // Jadval matni
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin + 2, yStart - 15);
            contentStream.showText("Header 1");
            contentStream.newLineAtOffset(colWidth, 0);
            contentStream.showText("Header 2");
            contentStream.newLineAtOffset(colWidth, 0);
            contentStream.showText("Header 3");
            contentStream.newLineAtOffset(colWidth, 0);
            contentStream.showText("Header 4");
            contentStream.newLineAtOffset(colWidth, 0);
            contentStream.showText("Header 5"); // Qo'shilgan ustun
            contentStream.endText();

            contentStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.save("table_with_dynamic_columns_example.pdf");
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
