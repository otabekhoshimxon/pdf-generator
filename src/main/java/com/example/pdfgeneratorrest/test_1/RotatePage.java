package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class RotatePage {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // Sahifani aylantirish
            page.setRotation(90);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Bu aylantirilgan sahifadagi matn.");
                contentStream.endText();
            }

            document.save("pdf_with_rotated_page.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
