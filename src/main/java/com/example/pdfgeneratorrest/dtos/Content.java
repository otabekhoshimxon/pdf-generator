package com.example.pdfgeneratorrest.dtos;


import java.util.ArrayList;
@lombok.Data
public class Content{
    private int id;
    private Duration duration;
    private ArrayList<TourItenarary> tourItenarary;
    private ArrayList<String> images;
    private String videoUrl;
    private int startingPrice;
    private Name name;
    private ArrayList<Description> description;
    private ArrayList<TourPrice> tourPrices;
    private PriceNote priceNote;
    private Country country;
    private boolean deleted;
    private String createdAt;
    private String updatedAt;
}

