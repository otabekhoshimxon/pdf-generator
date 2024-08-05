package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.IOException;

public class AdvancedTableInPDF {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float tableHeight = 200f;
            int rows = 6;
            int cols = 4;
            float rowHeight = tableHeight / rows;
            float colWidth = tableWidth / cols;
            float tableBottomY = yStart - tableHeight;

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Jadval chizish
                contentStream.setLineWidth(1f);
                contentStream.setStrokingColor(0, 0, 0); // Qora chiziqlar
                
                // Yozuvlarning qatorlarini chizish
                for (int i = 0; i <= rows; i++) {
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
                
                // Matn qo'shish
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.setNonStrokingColor(0, 0, 0); // Qora matn
                
                // Jadval sarlavhalarini qo'shish
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

                document.save("advanced_table_example.pdf");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
