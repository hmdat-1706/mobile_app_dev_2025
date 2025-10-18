package com.example.minhdat_23520249_lab2_5;

public class Dish {
    private String name;
    private Thumbnail thumbnail;
    private boolean hasPromotion;

    public Dish(String name, Thumbnail thumbnail, boolean hasPromotion) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.hasPromotion = hasPromotion;
    }

    public String getName() {
        return name;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public boolean hasPromotion() {
        return hasPromotion;
    }
}
