package com.example.pdfgeneratorrest.test_1.table;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.Color;
import java.io.IOException;

public class TableWithMergedCellsExample {
    public static void main(String[] args) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        float margin = 50;
        float yStart = page.getMediaBox().getHeight() - margin;
        float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
        int cols = 4;
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

            // Birlashtirilgan katak
            float mergeWidth = colWidth * 2; // 2 ustunlik birlashma
            float mergeHeight = rowHeight;
            contentStream.setNonStrokingColor(Color.LIGHT_GRAY); // Rang
            contentStream.addRect(margin + colWidth, yStart - mergeHeight, mergeWidth, mergeHeight);
            contentStream.fill();

            // Jadval matni
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin + colWidth + 2, yStart - 15);
            contentStream.showText("Merged Cell");
            contentStream.endText();

            contentStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.save("table_with_merged_cells_example.pdf");
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
