package com.liggiorgio.rumapp.news;

public class NewsItem implements Item {

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

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int getType() {
        return 0;
    }
}