package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class CreatePDF {
    public static void main(String[] args) {
        // Bo'sh hujjat yaratish
        PDDocument document = new PDDocument();
        
        // Sahifa qo'shish
        PDPage page = new PDPage();
        document.addPage(page);
        
        // Matn yozish uchun ContentStream yaratish
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Salom, bu PDFBox yordamida yaratilgan matn.");
            contentStream.endText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Hujjatni saqlash
        try {
            document.save("sample.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Hujjatni yopish
        try {
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
