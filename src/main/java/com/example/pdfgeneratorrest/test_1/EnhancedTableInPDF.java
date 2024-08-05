package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.awt.Color;
import java.io.IOException;

public class EnhancedTableInPDF {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float tableHeight = 200f;
            int rows = 7; // Sarlavhalar + 5 qator
            int cols = 4;
            float rowHeight = tableHeight / rows;
            float colWidth = tableWidth / cols;
            float tableBottomY = yStart - tableHeight;

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Jadvalni chizish
                contentStream.setLineWidth(1f);
                
                // Jadval chegaralari
                contentStream.setStrokingColor(Color.BLACK);
                contentStream.moveTo(margin, yStart);
                contentStream.lineTo(margin + tableWidth, yStart);
                contentStream.lineTo(margin + tableWidth, tableBottomY);
                contentStream.lineTo(margin, tableBottomY);
                contentStream.closePath();
                contentStream.stroke();

                // Yozuvlarning qatorlarini chizish
                for (int i = 1; i <= rows; i++) {
                    float y = yStart - i * rowHeight;
                    contentStream.moveTo(margin, y);
                    contentStream.lineTo(margin + tableWidth, y);
                    contentStream.stroke();
                }
                
                // Ustunlar orasidagi chiziqlarni chizish
                for (int j = 0; j <= cols; j++) {
                    float x = margin + j * colWidth;
                    contentStream.moveTo(x, yStart);
                    contentStream.lineTo(x, tableBottomY);
                    contentStream.stroke();
                }
                
                // Jadvalga matn qo'shish
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.setNonStrokingColor(Color.BLACK);
                
                // Sarlavhalar
                String[] headers = {"ID", "Name", "Quantity", "Price"};
                for (int i = 0; i < headers.length; i++) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin + i * colWidth + 5, yStart - 15);
                    contentStream.showText(headers[i]);
                    contentStream.endText();
                }
                
                // Jadval qatorlariga matn qo'shish
                String[][] data = {
                    {"1", "Apples", "30", "$20"},
                    {"2", "Oranges", "20", "$15"},
                    {"3", "Bananas", "15", "$10"},
                    {"4", "Grapes", "25", "$12"},
                    {"5", "Pears", "22", "$18"}
                };

                for (int i = 0; i < data.length; i++) {
                    for (int j = 0; j < data[i].length; j++) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(margin + j * colWidth + 5, yStart - (i + 2) * rowHeight + 5);
                        contentStream.showText(data[i][j]);
                        contentStream.endText();
                    }
                }

                // Jadval qatorlarini rang bilan ajratish
                contentStream.setNonStrokingColor(new Color(200, 200, 255).getRGB()); // Yengil ko'k rang
                for (int i = 1; i <= data.length; i++) {
                    float y = yStart - i * rowHeight;
                    contentStream.addRect(margin, y - rowHeight, tableWidth, rowHeight);
                    contentStream.fill();
                }

                // Sarlavha qatorining rangini o'zgartirish
                contentStream.setNonStrokingColor(new Color(0, 0, 128).getRGB()); // Oq ko'k rang
                contentStream.addRect(margin, yStart - rowHeight, tableWidth, rowHeight);
                contentStream.fill();
                
                // Sarlavhalar matnini oq rangda ko'rsatish
                contentStream.setNonStrokingColor(Color.WHITE);
                for (int i = 0; i < headers.length; i++) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin + i * colWidth + 5, yStart - rowHeight + 5);
                    contentStream.showText(headers[i]);
                    contentStream.endText();
                }

                document.save("enhanced_table_example.pdf");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
