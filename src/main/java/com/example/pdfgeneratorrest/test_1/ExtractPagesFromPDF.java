package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;

public class ExtractPagesFromPDF {
    public static void main(String[] args) {
        try (PDDocument sourceDocument = PDDocument.load(new File("input.pdf"))) {
            PDDocument targetDocument = new PDDocument();
            PDPage page = sourceDocument.getPage(0); // Birinchi sahifani tanlash
            targetDocument.addPage(page);

            targetDocument.save("extracted_page.pdf");
            targetDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
