package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class ChangeTextColor {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.setNonStrokingColor(255, 0, 0); // Qizil rang
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Bu qizil rangdagi matn.");
                contentStream.endText();
            }

            document.save("pdf_with_colored_text.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
