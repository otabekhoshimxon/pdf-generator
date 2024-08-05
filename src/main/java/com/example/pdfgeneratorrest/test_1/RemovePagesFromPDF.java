package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class RemovePagesFromPDF {
    public static void main(String[] args) {
        try (PDDocument document = PDDocument.load(new File("input.pdf"))) {
            document.removePage(0); // Birinchi sahifani o'chirish
            document.save("output.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
