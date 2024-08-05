package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

import java.io.File;
import java.io.IOException;

public class ProtectPDF {
    public static void main(String[] args) {
        try (PDDocument document = PDDocument.load(new File("pdf_with_form.pdf"))) {
            AccessPermission accessPermission = new AccessPermission();
            accessPermission.setCanModify(false); // O'zgarishlarni cheklash

            StandardProtectionPolicy policy = new StandardProtectionPolicy("ownerpassword", "userpassword", accessPermission);
            policy.setEncryptionKeyLength(128);
            policy.setPermissions(accessPermission);

            document.protect(policy);
            document.save("protected_pdf.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
