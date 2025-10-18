package com.example.minhdat_23520249_lab2_5;

public enum Thumbnail {
    Thumbnail1("Thumbnail 1", R.drawable.thumb1),
    Thumbnail2("Thumbnail 2", R.drawable.thumb2),
    Thumbnail3("Thumbnail 3", R.drawable.thumb3),
    Thumbnail4("Thumbnail 4", R.drawable.thumb4);

    private final String name;
    private final int img;

    Thumbnail(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }
}
