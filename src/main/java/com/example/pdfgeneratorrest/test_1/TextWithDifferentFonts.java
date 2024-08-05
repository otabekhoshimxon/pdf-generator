package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class TextWithDifferentFonts {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Bu Helvetica Bold shriftidagi matn.");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_ITALIC, 12);
                contentStream.newLineAtOffset(100, 650);
                contentStream.showText("Bu Times Italic shriftidagi matn.");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 10);
                contentStream.newLineAtOffset(100, 600);
                contentStream.showText("Bu Courier shriftidagi matn.");
                contentStream.endText();
            }

            document.save("pdf_with_different_fonts.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
