package com.example.pdfgeneratorrest.test_1.table;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.Color;
import java.io.IOException;

public class TableWithColorExample {
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

            // Ustun rang berish
            contentStream.setNonStrokingColor(Color.LIGHT_GRAY); // Rangni tanlash
            contentStream.addRect(margin, yStart - rowHeight, tableWidth, rowHeight);
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
            contentStream.endText();

            contentStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.save("table_with_color_example.pdf");
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
