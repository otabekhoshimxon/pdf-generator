package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;

public class CombineElementsInPDF {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Matn yozish
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Bu grafik, rasm va matnli PDF.");
                contentStream.endText();

                // Rasm qo'shish
                PDImageXObject pdImage = PDImageXObject.createFromFile("path/to/image.jpg", document);
                contentStream.drawImage(pdImage, 100, 500, pdImage.getWidth() / 2, pdImage.getHeight() / 2);

                // Grafik elementlar chizish
                contentStream.setLineWidth(2);
                contentStream.moveTo(100, 400);
                contentStream.lineTo(200, 400);
                contentStream.stroke();
                
                contentStream.addRect(100, 300, 100, 50);
                contentStream.stroke();

                contentStream.setNonStrokingColor(0, 255, 0);
                contentStream.addRect(150, 200, 50,50);
                contentStream.fill();
            }

            document.save("pdf_with_combined_elements.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
