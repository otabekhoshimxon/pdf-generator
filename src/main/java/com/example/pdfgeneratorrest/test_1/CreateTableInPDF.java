package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;

public class CreateTableInPDF {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float tableHeight = 100;
                int rows = 5;
                int cols = 3;
                float rowHeight = tableHeight / rows;
                float tableHeightRatio = tableHeight / (rows + 1);
                float colWidth = tableWidth / (float) cols;
                
                // Jadvalni chizish
                contentStream.setLineWidth(1f);
                contentStream.moveTo(margin, yStart);
                contentStream.lineTo(margin + tableWidth, yStart);
                contentStream.stroke();
                
                for (int i = 0; i <= rows; i++) {
                    float y = yStart - i * rowHeight;
                    contentStream.moveTo(margin, y);
                    contentStream.lineTo(margin + tableWidth, y);
                    contentStream.stroke();
                }
                
                for (int j = 0; j <= cols; j++) {
                    float x = margin + j * colWidth;
                    contentStream.moveTo(x, yStart);
                    contentStream.lineTo(x, yStart - tableHeight);
                    contentStream.stroke();
                }
                
                // Jadvalga matn qo'shish
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + 5, yStart - rowHeight + 5);
                contentStream.showText("Header 1");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + colWidth + 5, yStart - rowHeight + 5);
                contentStream.showText("Header 2");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + 2 * colWidth + 5, yStart - rowHeight + 5);
                contentStream.showText("Header 3");
                contentStream.endText();
                
                document.save("table_example.pdf");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
