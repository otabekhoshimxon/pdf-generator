package com.example.pdfgeneratorrest.dtos;

import java.util.ArrayList;

@lombok.Data
public class Country{
    private int id;
    private Name name;
    private String imageUrl;
    private String code;
    private ArrayList<Object> region;
    private boolean deleted;
}
