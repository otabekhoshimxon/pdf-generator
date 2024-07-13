package com.example.pdfgeneratorrest.dtos;

import java.util.ArrayList;

@lombok.Data
public class TourInfoResponse{
    private ArrayList<Content> content;
    private Pageable pageable;
    private int totalPages;
    private int totalElements;
    private boolean last;
    private int numberOfElements;
    private int size;
    private int number;
    private Sort sort;
    private boolean first;
    private boolean empty;
}
