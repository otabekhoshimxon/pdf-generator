package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class CenterTextInPDF {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            String text = "Bu markazdagi matn.";
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(text) / 1000 * 12;
                float titleHeight = PDType1Font.HELVETICA_BOLD.getFontDescriptor().getCapHeight() / 1000 * 12;
                float pageWidth = page.getMediaBox().getWidth();
                float pageHeight = page.getMediaBox().getHeight();

                contentStream.beginText();
                contentStream.newLineAtOffset((pageWidth - titleWidth) / 2, (pageHeight - titleHeight) / 2);
                contentStream.showText(text);
                contentStream.endText();
            }

            document.save("pdf_with_centered_text.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
