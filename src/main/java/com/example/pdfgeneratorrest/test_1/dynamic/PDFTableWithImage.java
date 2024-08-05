package com.example.pdfgeneratorrest.test_1.dynamic;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.InputStream;

public class PDFTableWithImage {
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

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            // Jadval chegaralari
            contentStream.setLineWidth(1f);
            contentStream.setStrokingColor(Color.BLACK);

            // Yumuq burchaklar
            float borderRadius = 10f; // Burchak radiusi
            drawRoundedRectangle(contentStream, margin, yStart - rowHeight * 5, tableWidth, rowHeight * 5, borderRadius);

            // Rasmni joylashtirish
            try (InputStream imageStream = PDFTableWithImage.class.getResourceAsStream("sample.jpg")) {
                if (imageStream != null) {
                    PDImageXObject image = PDImageXObject.createFromFile("sample.jpg", document);
                    contentStream.drawImage(image, margin + colWidth * 2, yStart - rowHeight * 2 - 50, 50, 50);
                }
            }

            // Jadvalni to'ldirish
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.setNonStrokingColor(Color.BLACK);
            // Sarlavhalar va ma'lumotlar uchun kod

            contentStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.save("table_with_image.pdf");
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void drawRoundedRectangle(PDPageContentStream contentStream, float x, float y, float width, float height, float radius) throws IOException {
        Path2D path = new Path2D.Float();
        path.append(new RoundRectangle2D.Float(x, y, width, height, radius, radius), false);
        contentStream.addRect(x, y, width, height); // Normal to'rtburchak
        contentStream.clip(); // Burchakni yumshatish
        contentStream.stroke();
    }
}
