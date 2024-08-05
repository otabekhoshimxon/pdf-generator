package com.example.pdfgeneratorrest.test_1.mock;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaginatedTableExample {
    private static final float MARGIN = 50;
    private static final float ROW_HEIGHT = 20f;
    private static final int MAX_ROWS_PER_PAGE = 25; // Har bir sahifada maksimal qatorlar soni

    public static void main(String[] args) {
        List<String[]> data = generateMockData(); // Mock ma'lumotlar
        try (PDDocument document = new PDDocument()) {
            int totalRows = data.size() + 1; // Ma'lumotlar qatori + sarlavha
            int pages = (int) Math.ceil((double) totalRows / MAX_ROWS_PER_PAGE);

            for (int pageNum = 0; pageNum < pages; pageNum++) {
                PDPage page = new PDPage();
                document.addPage(page);

                float yStart = page.getMediaBox().getHeight() - MARGIN;
                float tableWidth = page.getMediaBox().getWidth() - 2 * MARGIN;
                int cols = 4; // Ustunlar soni
                float colWidth = tableWidth / cols;

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.setLineWidth(1f);

                    // Jadval chegaralari
                    contentStream.setStrokingColor(Color.BLACK);
                    contentStream.addRect(MARGIN, yStart - ROW_HEIGHT * MAX_ROWS_PER_PAGE, tableWidth, ROW_HEIGHT * MAX_ROWS_PER_PAGE);
                    contentStream.stroke();

                    // Qator va ustun chiziqlari
                    for (int i = 1; i < MAX_ROWS_PER_PAGE; i++) {
                        float y = yStart - i * ROW_HEIGHT;
                        contentStream.moveTo(MARGIN, y);
                        contentStream.lineTo(MARGIN + tableWidth, y);
                        contentStream.stroke();
                    }
                    for (int i = 1; i < cols; i++) {
                        float x = MARGIN + i * colWidth;
                        contentStream.moveTo(x, yStart);
                        contentStream.lineTo(x, yStart - ROW_HEIGHT * MAX_ROWS_PER_PAGE);
                        contentStream.stroke();
                    }

                    // Jadval matni sarlavhalari
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(MARGIN + 2, yStart - 15);
                    contentStream.showText("Name");
                    contentStream.newLineAtOffset(colWidth, 0);
                    contentStream.showText("Email");
                    contentStream.newLineAtOffset(colWidth, 0);
                    contentStream.showText("Position");
                    contentStream.newLineAtOffset(colWidth, 0);
                    contentStream.showText("Join Date");
                    contentStream.endText();

                    // Jadval matni (soxta ma'lumotlar)
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    int startRow = pageNum * MAX_ROWS_PER_PAGE;
                    int endRow = Math.min(startRow + MAX_ROWS_PER_PAGE, data.size());
                    for (int i = startRow; i < endRow; i++) {
                        String[] row = data.get(i);
                        float y = yStart - (i - startRow + 1) * ROW_HEIGHT;

                        // Har bir katakka matnni to'g'ri joylashtirish
                        contentStream.beginText();
                        contentStream.newLineAtOffset(MARGIN + 2, y + 5);
                        contentStream.showText(row[0]); // Name
                        contentStream.newLineAtOffset(colWidth, 0);
                        contentStream.showText(row[1]); // Email
                        contentStream.newLineAtOffset(colWidth, 0);
                        contentStream.showText(row[2]); // Position
                        contentStream.newLineAtOffset(colWidth, 0);
                        contentStream.showText(row[3]); // Join Date
                        contentStream.endText();
                    }
                }
            }
            document.save("paginated_table_example.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> generateMockData() {
        List<String[]> data = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            data.add(new String[]{
                    "Name " + i,
                    "email" + i + "@example.com",
                    "Position " + i,
                    "2024-08-" + (i % 30 + 1)
            });
        }
        return data;
    }
}
