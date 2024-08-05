package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;

public class DrawShapesInPDF {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setLineWidth(2);
                
                // Chiziq chizish
                contentStream.moveTo(100, 700);
                contentStream.lineTo(200, 700);
                contentStream.stroke();
                
                // To'rtburchak chizish
                contentStream.addRect(100, 600, 100, 50);
                contentStream.stroke();

                // Aylana chizish
                contentStream.addRect(100, 500, 50, 50);
                contentStream.stroke();
            }

            document.save("pdf_with_shapes.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
