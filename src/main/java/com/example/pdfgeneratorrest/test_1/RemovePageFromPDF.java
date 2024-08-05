package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;
import java.io.IOException;

public class RemovePageFromPDF {
    public static void main(String[] args) {
        try (PDDocument document = PDDocument.load(new File("pdf_with_multiple_pages.pdf"))) {
            document.removePage(1); // 2-sahifani o'chirish
            document.save("pdf_with_page_removed.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
