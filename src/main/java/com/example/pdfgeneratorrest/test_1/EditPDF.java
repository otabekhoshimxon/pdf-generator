package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class EditPDF {
    public static void main(String[] args) {
        try (PDDocument document = PDDocument.load(new File("sample.pdf"))) {
            PDPage page = document.getPage(0);
            
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(100, 650);
                contentStream.showText("Bu yangi qo'shilgan matn.");
                contentStream.endText();
            }
            
            document.save("sample_edited.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
