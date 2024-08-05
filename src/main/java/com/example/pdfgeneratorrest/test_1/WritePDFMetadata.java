package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import java.io.File;
import java.io.IOException;

public class WritePDFMetadata {
    public static void main(String[] args) {
        try (PDDocument document = PDDocument.load(new File("input.pdf"))) {
            PDDocumentInformation info = document.getDocumentInformation();
            info.setTitle("Yangi Sarlavha");
            info.setAuthor("Yangi Muallif");
            info.setSubject("Yangi Mavzu");
            info.setKeywords("PDFBox, Metadata, Yangi");

            document.save("output_with_metadata.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
