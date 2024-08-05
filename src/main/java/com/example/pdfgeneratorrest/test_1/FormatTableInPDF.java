package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class FormatTableInPDF {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float rowHeight = 20f;
                float tableHeight = rowHeight * 5;
                int cols = 3;
                float colWidth = tableWidth / (float) cols;
                
                // Jadvalni chizish
                contentStream.setLineWidth(1f);
                contentStream.setStrokingColor(0, 0, 0); // Qora chiziqlar
                contentStream.moveTo(margin, yStart);
                contentStream.lineTo(margin + tableWidth, yStart);
                contentStream.stroke();
                
                for (int i = 0; i <= 5; i++) {
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
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.setNonStrokingColor(0, 0, 0); // Qora matn
                String[] headers = {"Header 1", "Header 2", "Header 3"};
                int rowIndex = 0;
                for (String header : headers) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin + (rowIndex * colWidth) + 5, yStart - rowHeight + 5);
                    contentStream.showText(header);
                    contentStream.endText();
                    rowIndex++;
                }
                
                // Data qo'shish
                String[][] data = {
                    {"Row1-1", "Row1-2", "Row1-3"},
                    {"Row2-1", "Row2-2", "Row2-3"},
                    {"Row3-1", "Row3-2", "Row3-3"},
                    {"Row4-1", "Row4-2", "Row4-3"},
                    {"Row5-1", "Row5-2", "Row5-3"}
                };
                
                for (int i = 0; i < data.length; i++) {
                    for (int j = 0; j < data[i].length; j++) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(margin + j * colWidth + 5, yStart - (i + 2) * rowHeight + 5);
                        contentStream.showText(data[i][j]);
                        contentStream.endText();
                    }
                }
                
                document.save("formatted_table_example.pdf");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
