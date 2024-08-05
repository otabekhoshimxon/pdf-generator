package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import java.io.IOException;

public class MergePDFs {
    public static void main(String[] args) {
        try {
        PDFMergerUtility merger = new PDFMergerUtility();
        merger.addSource("file1.pdf");
        merger.addSource("file2.pdf");
        merger.setDestinationFileName("merged.pdf");


            merger.mergeDocuments(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
