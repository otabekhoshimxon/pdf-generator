package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class AddTableToPDF {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float yPosition = yStart;

                String[][] content = {
                    {"ID", "Ism", "Yosh"},
                    {"1", "Ali", "25"},
                    {"2", "Vali", "30"},
                    {"3", "Hasan", "35"}
                };

                for (String[] row : content) {
                    float nextY = yPosition - 15;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, nextY);
                    for (String cell : row) {
                        contentStream.showText(cell + " ");
                    }
                    contentStream.endText();
                    yPosition = nextY;
                }
            }

            document.save("pdf_with_table.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
