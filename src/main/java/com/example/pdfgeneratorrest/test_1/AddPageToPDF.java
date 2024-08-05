package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.IOException;

public class AddPageToPDF {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            PDPage firstPage = new PDPage();
            document.addPage(firstPage);
            
            PDPage secondPage = new PDPage();
            document.addPage(secondPage);

            document.save("pdf_with_multiple_pages.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
