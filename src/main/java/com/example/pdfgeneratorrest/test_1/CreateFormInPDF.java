package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import java.io.IOException;

public class CreateFormInPDF {
    public static void main(String[] args) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDAcroForm acroForm = new PDAcroForm(document);
            document.getDocumentCatalog().setAcroForm(acroForm);

            PDTextField textBox = new PDTextField(acroForm);
            textBox.setPartialName("sampleField");
            textBox.setDefaultAppearance("/Helv 12 Tf 0 g");
            acroForm.getFields().add(textBox);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Sample Text:");
                contentStream.endText();

                contentStream.addRect(100, 680, 200, 20);
                contentStream.stroke();
            }

            document.save("pdf_with_form.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
