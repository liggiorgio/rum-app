package com.liggiorgio.rumapp.news;

public class NewsItem {

    private final String ref;
    private final String title;
    private final String date;
    private final String categories;
    private final String text;

    NewsItem(String ref, String title, String date, String categories, String text) {
        this.ref = ref;
        this.title = title;
        this.date = date;
        this.categories = categories;
        this.text = text;
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

    public String getCategories() {
        return categories;
    }

    public String getText() {
        return text;
    }
}