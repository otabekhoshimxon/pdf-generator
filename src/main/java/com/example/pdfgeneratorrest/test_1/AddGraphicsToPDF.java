package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;

public class AddGraphicsToPDF {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Chiziq chizish
                contentStream.setLineWidth(1);
                contentStream.moveTo(100, 700);
                contentStream.lineTo(200, 700);
                contentStream.stroke();

                // To'rtburchak chizish
                contentStream.addRect(100, 600, 100, 50);
                contentStream.stroke();

                // Doira chizish
                contentStream.setNonStrokingColor(255, 0, 0);
                contentStream.addRect(150, 500, 50,50);
                contentStream.fill();

                // Matn yozish
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 450);
                contentStream.showText("Bu grafik elementlari bilan PDF.");
                contentStream.endText();
            }

            document.save("pdf_with_graphics.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
