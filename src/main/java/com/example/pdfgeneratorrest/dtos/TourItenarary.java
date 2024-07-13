package com.example.pdfgeneratorrest.dtos;

import java.util.ArrayList;

@lombok.Data
public class TourItenarary{
    private int id;
    private String imageUrl;
    private ArrayList<Description> description;
    private ArrayList<Object> descriptions;
    private Title title;
}
