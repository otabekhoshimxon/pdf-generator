package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;

public class CustomFontExample {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            File fontFile = new File("path/to/your/font.ttf");
            PDType0Font customFont = PDType0Font.load(document, fontFile);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(customFont, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Bu shaxsiy shrift bilan yozilgan matn.");
                contentStream.endText();
            }

            document.save("pdf_with_custom_font.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
