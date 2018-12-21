package com.liggiorgio.rumapp.news;

public class NewsItem {

    private final String image;
    private final String ref;
    private final String title;
    private final String date;
    private final String text;

    NewsItem(String image, String ref, String title, String date, String text) {
        this.image = image;
        this.ref = ref;
        this.title = title;
        this.date = date;
        this.text = text;
    }

    public int getType() {
        return 0;
    }

    public String getImage() {
        return image;
    }

    public String getRef() {
        return ref;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }
}