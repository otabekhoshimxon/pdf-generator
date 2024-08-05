package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExportPDFAsImage {
    public static void main(String[] args) {
        try (PDDocument document = PDDocument.load(new File("input.pdf"))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); page++) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300); // 300 DPI sifatida eksport qilish
                ImageIO.write(bim, "png", new File("page_" + (page + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
