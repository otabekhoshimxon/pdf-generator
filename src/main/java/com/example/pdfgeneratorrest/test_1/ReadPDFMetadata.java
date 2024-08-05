package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import java.io.File;
import java.io.IOException;

public class ReadPDFMetadata {
    public static void main(String[] args) {
        try (PDDocument document = PDDocument.load(new File("input.pdf"))) {
            PDDocumentInformation info = document.getDocumentInformation();
            System.out.println("Title: " + info.getTitle());
            System.out.println("Author: " + info.getAuthor());
            System.out.println("Subject: " + info.getSubject());
            System.out.println("Keywords: " + info.getKeywords());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
