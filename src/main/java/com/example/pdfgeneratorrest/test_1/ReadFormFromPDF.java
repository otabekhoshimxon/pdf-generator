package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReadFormFromPDF {
    public static void main(String[] args) {
        try (PDDocument document = PDDocument.load(new File("pdf_with_form.pdf"))) {
            PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
            if (acroForm != null) {
                List<PDField> fields = acroForm.getFields();
                for (PDField field : fields) {
                    System.out.println("Field name: " + field.getPartialName());
                    System.out.println("Field value: " + field.getValueAsString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
