package com.example.pdfgeneratorrest;

import com.example.pdfgeneratorrest.dtos.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.web.client.RestClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {


            String url = "https://tour-back.uz/api/tours/individual-tours?page=0&size=50";

        ResponseData body = RestClient.create().get().uri(url).retrieve().body(ResponseData.class);
        if (body != null) {
            TourInfoResponse data = body.getData();
            writeToPDF(data);
        } }

    private static void writeToPDF(TourInfoResponse tours) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = null;

        try {
            contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.setLeading(14.5f);

            float margin = 25;
            float yStart = 725;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;
            float rowHeight = 20;
            float cellMargin = 5;

            // Table header
            String[] headers = {"ID", "Name", "Starting Price", "Country", "Created At", "Updated At", "Itineraries", "Descriptions", "Prices"};
            float[] colWidths = {50, 150, 100, 100, 100, 100, 100, 100, 100};

            drawTableHeaders(contentStream, headers, colWidths, margin, yPosition, tableWidth, rowHeight, cellMargin);
            yPosition -= rowHeight;

            contentStream.setFont(PDType1Font.HELVETICA, 10);

            for (Content content : tours.getContent()) {
                if (yPosition < margin) {
                    contentStream.endText();
                    contentStream.close();

                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.setFont(PDType1Font.HELVETICA, 10);
                    contentStream.setLeading(14.5f);
                    yPosition = yStart;

                    drawTableHeaders(contentStream, headers, colWidths, margin, yPosition, tableWidth, rowHeight, cellMargin);
                    yPosition -= rowHeight;
                }

                List<String[]> rows = wrapRowText(content, colWidths, cellMargin);
                for (String[] row : rows) {
                    yPosition -= rowHeight;
                    drawTableRow(contentStream, row, colWidths, margin, yPosition, rowHeight, cellMargin);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (contentStream != null) {
                    contentStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            document.save("TourInfo.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<String[]> wrapRowText(Content content, float[] colWidths, float cellMargin) throws IOException {
        List<String[]> wrappedRows = new ArrayList<>();
        List<String> idWrapped = wrapText(String.valueOf(content.getId()), colWidths[0] - cellMargin * 2);
        List<String> nameWrapped = wrapText(content.getName().getEn() != null ? content.getName().getEn() : "", colWidths[1] - cellMargin * 2);
        List<String> startingPriceWrapped = wrapText(String.valueOf(content.getStartingPrice()), colWidths[2] - cellMargin * 2);
        List<String> countryWrapped = wrapText(content.getCountry().getName().getEn() != null ? content.getCountry().getName().getEn() : "", colWidths[3] - cellMargin * 2);
        List<String> createdAtWrapped = wrapText(content.getCreatedAt() != null ? content.getCreatedAt() : "", colWidths[4] - cellMargin * 2);
        List<String> updatedAtWrapped = wrapText(content.getUpdatedAt() != null ? content.getUpdatedAt() : "", colWidths[5] - cellMargin * 2);

        // New columns
        List<String> itinerariesWrapped = wrapText(getItineraries(content.getTourItenarary()), colWidths[6] - cellMargin * 2);
        List<String> descriptionsWrapped = wrapText(getDescriptions(content.getDescription()), colWidths[7] - cellMargin * 2);
        List<String> pricesWrapped = wrapText(getPrices(content.getTourPrices()), colWidths[8] - cellMargin * 2);

        int maxLines = Math.max(Math.max(Math.max(idWrapped.size(), nameWrapped.size()),
                        Math.max(startingPriceWrapped.size(), countryWrapped.size())),
                Math.max(Math.max(createdAtWrapped.size(), updatedAtWrapped.size()),
                        Math.max(itinerariesWrapped.size(), Math.max(descriptionsWrapped.size(), pricesWrapped.size()))));

        for (int i = 0; i < maxLines; i++) {
            String[] row = new String[9];
            row[0] = i < idWrapped.size() ? idWrapped.get(i) : "";
            row[1] = i < nameWrapped.size() ? nameWrapped.get(i) : "";
            row[2] = i < startingPriceWrapped.size() ? startingPriceWrapped.get(i) : "";
            row[3] = i < countryWrapped.size() ? countryWrapped.get(i) : "";
            row[4] = i < createdAtWrapped.size() ? createdAtWrapped.get(i) : "";
            row[5] = i < updatedAtWrapped.size() ? updatedAtWrapped.get(i) : "";
            row[6] = i < itinerariesWrapped.size() ? itinerariesWrapped.get(i) : "";
            row[7] = i < descriptionsWrapped.size() ? descriptionsWrapped.get(i) : "";
            row[8] = i < pricesWrapped.size() ? pricesWrapped.get(i) : "";
            wrappedRows.add(row);
        }

        return wrappedRows;
    }

    private static List<String> wrapText(String text, float width) throws IOException {
        List<String> lines = new ArrayList<>();
        String[] paragraphs = text.split("\n");

        for (String paragraph : paragraphs) {
            String[] words = paragraph.split(" ");
            StringBuilder line = new StringBuilder();
            float spaceWidth = PDType1Font.HELVETICA.getStringWidth(" ") / 1000 * 10;

            for (String word : words) {
                float wordWidth = PDType1Font.HELVETICA.getStringWidth(word) / 1000 * 10;
                if (!line.isEmpty()) {
                    wordWidth += spaceWidth;
                }
                if (line.isEmpty() || line.length() + wordWidth < width) {
                    if (!line.isEmpty()) {
                        line.append(" ");
                    }
                    line.append(word);
                } else {
                    lines.add(line.toString());
                    line = new StringBuilder(word);
                }
            }
            if (line.length() > 0) {
                lines.add(line.toString());
            }
        }

        return lines;
    }

    private static void drawTableHeaders(PDPageContentStream contentStream, String[] headers, float[] colWidths, float margin, float y, float tableWidth, float rowHeight, float cellMargin) throws IOException {
        float nextX = margin;
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);

        for (int i = 0; i < headers.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(nextX + cellMargin, y - rowHeight / 2 - 3);
            contentStream.showText(headers[i]);
            contentStream.endText();
            nextX += colWidths[i];
        }

        drawTableGrid(contentStream, headers.length, colWidths, margin, y, tableWidth, rowHeight);
    }

    private static void drawTableRow(PDPageContentStream contentStream, String[] cells, float[] colWidths, float margin, float y, float rowHeight, float cellMargin) throws IOException {
        float nextX = margin;
        contentStream.setFont(PDType1Font.HELVETICA, 10);

        for (int i = 0; i < cells.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(nextX + cellMargin, y - rowHeight / 2 - 3);
            contentStream.showText(cells[i]);
            contentStream.endText();
            nextX += colWidths[i];
        }

        drawTableGrid(contentStream, cells.length, colWidths, margin, y, nextX - margin, rowHeight);
    }

    private static void drawTableGrid(PDPageContentStream contentStream, int numCols, float[] colWidths, float margin, float y, float tableWidth, float rowHeight) throws IOException {
        float nextX = margin;
        contentStream.setStrokingColor(122, 11, 32);
        contentStream.setLineWidth(0.5f);

        contentStream.moveTo(nextX, y);
        contentStream.lineTo(nextX + tableWidth, y);
        contentStream.stroke();

        contentStream.moveTo(nextX, y - rowHeight);
        contentStream.lineTo(nextX + tableWidth, y - rowHeight);
        contentStream.stroke();

        for (int i = 0; i <= numCols; i++) {
            contentStream.moveTo(nextX, y);
            contentStream.lineTo(nextX, y - rowHeight);
            contentStream.stroke();
            if (i < numCols) {
                nextX += colWidths[i];
            }
        }
    }

    private static String getItineraries(List<TourItenarary> itineraries) {
        StringBuilder sb = new StringBuilder();
        for (TourItenarary it : itineraries) {
            sb.append("Title: ").append(it.getTitle().getEn()).append("\n");
            sb.append("Description: ").append(getDescriptions(it.getDescription())).append("\n");
        }
        return sb.toString();
    }

    private static String getDescriptions(List<Description> descriptions) {
        StringBuilder sb = new StringBuilder();
        for (Description desc : descriptions) {
            sb.append("En: ").append(desc.getEn()).append("\n");
            sb.append("Ru: ").append(desc.getRu()).append("\n");
        }
        return sb.toString();
    }

    private static String getPrices(List<TourPrice> prices) {
        StringBuilder sb = new StringBuilder();
        for (TourPrice price : prices) {
            sb.append("Price: ").append(price.getPrice()).append("\n");
            sb.append("Up to Persons: ").append(price.getUpToPersons()).append("\n");
            sb.append("Description: ").append(getDescriptions(price.getDescription() != null ? List.of(price.getDescription()) : new ArrayList<>())).append("\n");
        }
        return sb.toString();
    }
}
