package com.example.pdfgeneratorrest.test_1.table;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.Color;
import java.io.IOException;

public class AdvancedTableExample {
    public static void main(String[] args) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        float margin = 50;
        float yStart = page.getMediaBox().getHeight() - margin;
        float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
        int cols = 5; // 5 ustun
        float colWidth = tableWidth / cols;
        float rowHeight = 20f;
        int rows = 7; // 7 qator

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setLineWidth(1f);

            // Jadval chegaralari
            contentStream.setStrokingColor(Color.BLACK);
            contentStream.addRect(margin, yStart - rowHeight * rows, tableWidth, rowHeight * rows);
            contentStream.stroke();

            // Ustunlarni qo'shish
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

            // Kataklarni rang berish
            contentStream.setNonStrokingColor(Color.LIGHT_GRAY);
            contentStream.addRect(margin + colWidth * 1, yStart - rowHeight * 6, colWidth * 2, rowHeight);
            contentStream.fill();

            // Birlashtirilgan katak
            contentStream.setNonStrokingColor(Color.CYAN);
            contentStream.addRect(margin + colWidth * 3, yStart - rowHeight * 5, colWidth * 2, rowHeight * 2);
            contentStream.fill();

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
            contentStream.showText("Header 5");
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(margin + colWidth * 1 + 5, yStart - rowHeight * 6 + 5);
            contentStream.showText("Merged Cell 1");
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(margin + colWidth * 3 + 5, yStart - rowHeight * 5 + 5);
            contentStream.showText("Merged Cell 2");
            contentStream.endText();

            contentStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.save("advanced_table_example.pdf");
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
