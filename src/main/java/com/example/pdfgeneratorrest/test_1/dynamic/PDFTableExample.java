package com.example.pdfgeneratorrest.test_1.dynamic;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.Color;
import java.io.IOException;

public class PDFTableExample {
    public static void main(String[] args) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            int cols = 4;
            float colWidth = tableWidth / cols;

            // Ma'lumotlar
            String[] headers = {"ID", "Name", "Quantity", "Price"};
            String[][] data = {
                    {"1", "Apples", "30", "$20"},
                    {"2", "Oranges", "20", "$15"},
                    {"3", "Bananas", "15", "$10"},
                    {"4", "Grapes", "25", "$12"},
                    {"5", "Pears", "22", "$18"}
            };

            int rows = data.length + 1; // Sarlavhalar + ma'lumotlar
            float rowHeight = 20f;
            float tableHeight = rowHeight * rows;
            float tableBottomY = yStart - tableHeight;

            // Jadval chegaralari
            contentStream.setLineWidth(1f);
            contentStream.setStrokingColor(Color.BLACK);
            contentStream.moveTo(margin, yStart);
            contentStream.lineTo(margin + tableWidth, yStart);
            contentStream.lineTo(margin + tableWidth, tableBottomY);
            contentStream.lineTo(margin, tableBottomY);
            contentStream.closePath();
            contentStream.stroke();

            // Qator va ustun chiziqlarini chizish
            for (int i = 0; i <= rows; i++) {
                float y = yStart - i * rowHeight;
                contentStream.moveTo(margin, y);
                contentStream.lineTo(margin + tableWidth, y);
                contentStream.stroke();
            }
            for (int j = 0; j <= cols; j++) {
                float x = margin + j * colWidth;
                contentStream.moveTo(x, yStart);
                contentStream.lineTo(x, tableBottomY);
                contentStream.stroke();
            }

            // Jadval matnini qo'shish
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.setNonStrokingColor(Color.BLACK);

            // Sarlavhalar
            for (int i = 0; i < headers.length; i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + i * colWidth + 5, yStart - 15);
                contentStream.showText(headers[i]);
                contentStream.endText();
            }

            // Ma'lumotlar
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin + j * colWidth + 5, yStart - (i + 2) * rowHeight + 5);
                    contentStream.showText(data[i][j]);
                    contentStream.endText();
                }
            }

            // Qatorlarning rangini o'zgartirish (alternativ qatorlar)
            contentStream.setNonStrokingColor(new Color(240, 240, 240)); // Yengil kulrang
            for (int i = 1; i < data.length; i += 2) {
                float y = yStart - (i + 1) * rowHeight;
                contentStream.addRect(margin, y, tableWidth, rowHeight);
                contentStream.fill();
            }

            // Sarlavha rangini o'zgartirish
            contentStream.setNonStrokingColor(new Color(0, 0, 128)); // Oq ko'k rang
            contentStream.addRect(margin, yStart - rowHeight, tableWidth, rowHeight);
            contentStream.fill();

            // Sarlavhalarni oq rangda ko'rsatish
            contentStream.setNonStrokingColor(Color.WHITE);
            for (int i = 0; i < headers.length; i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + i * colWidth + 5, yStart - rowHeight + 5);
                contentStream.showText(headers[i]);
                contentStream.endText();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // PDF hujjatini yopish
            try {
                document.save("dynamic_table_example.pdf");
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
