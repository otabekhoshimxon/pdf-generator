package com.example.pdfgeneratorrest.dtos;

@lombok.Data
public class Pageable{
    private int pageNumber;
    private int pageSize;
    private Sort sort;
    private int offset;
    private boolean paged;
    private boolean unpaged;
}
