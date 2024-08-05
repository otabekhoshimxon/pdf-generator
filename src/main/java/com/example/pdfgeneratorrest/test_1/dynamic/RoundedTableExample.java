package com.example.pdfgeneratorrest.test_1.dynamic;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.Color;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;

public class RoundedTableExample {
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
        float borderRadius = 10f; // Burchak radiusi
        
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setLineWidth(1f);
            contentStream.setStrokingColor(Color.BLACK);

            // Jadval chegaralari
            drawRoundedRectangle(contentStream, margin, yStart - rowHeight * rows, tableWidth, rowHeight * rows, borderRadius);

            // Qator va ustun chiziqlari
            for (int i = 1; i < rows; i++) {
                float y = yStart - i * rowHeight;
                contentStream.moveTo(margin + borderRadius, y);
                contentStream.lineTo(margin + tableWidth - borderRadius, y);
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
            contentStream.endText();

            for (int row = 0; row < rows - 1; row++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + 2, yStart - (row + 2) * rowHeight + 5);
                contentStream.showText("Data " + (row + 1) + "-1");
                contentStream.newLineAtOffset(colWidth, 0);
                contentStream.showText("Data " + (row + 1) + "-2");
                contentStream.newLineAtOffset(colWidth, 0);
                contentStream.showText("Data " + (row + 1) + "-3");
                contentStream.newLineAtOffset(colWidth, 0);
                contentStream.showText("Data " + (row + 1) + "-4");
                contentStream.endText();
            }

            contentStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.save("rounded_table_example.pdf");
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void drawRoundedRectangle(PDPageContentStream contentStream, float x, float y, float width, float height, float radius) throws IOException {
        GeneralPath path = new GeneralPath();
        path.append(new RoundRectangle2D.Float(x, y, width, height, radius, radius), false);
        contentStream.clip();
        contentStream.stroke();
    }
}
