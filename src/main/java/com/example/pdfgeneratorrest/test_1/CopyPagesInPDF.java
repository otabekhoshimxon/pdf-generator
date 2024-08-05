package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;

public class CopyPagesInPDF {
    public static void main(String[] args) {
        try (PDDocument sourceDocument = PDDocument.load(new File("input.pdf"))) {
            PDDocument targetDocument = new PDDocument();

            for (int i = 0; i < sourceDocument.getNumberOfPages(); i++) {
                PDPage page = sourceDocument.getPage(i);
                targetDocument.addPage(page);
            }

            targetDocument.save("copied_pages.pdf");
            targetDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
