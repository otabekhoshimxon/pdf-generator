package com.example.pdfgeneratorrest.test_1;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HighlightTextInPDF extends PDFTextStripper {
    private final PDDocument document;

    public HighlightTextInPDF(PDDocument document) throws IOException {
        this.document = document;
        this.setSortByPosition(true);
    }

    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
        for (TextPosition text : textPositions) {
            if (string.contains("qidirilayotgan matn")) {
                try (PDPageContentStream contentStream = new PDPageContentStream(document, document.getPage(getCurrentPageNo() - 1), PDPageContentStream.AppendMode.APPEND, true, true)) {
                    contentStream.setNonStrokingColor(255, 255, 0); // Sariq rang
                    contentStream.addRect(text.getXDirAdj(), text.getYDirAdj(), text.getWidthDirAdj(), text.getHeightDir());
                    contentStream.fill();
                }
            }
        }
        super.writeString(string, textPositions);
    }

    public static void main(String[] args) {
        try (PDDocument document = PDDocument.load(new File("input.pdf"))) {
            HighlightTextInPDF highlighter = new HighlightTextInPDF(document);
            highlighter.getText(document);
            document.save("highlighted_text.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
