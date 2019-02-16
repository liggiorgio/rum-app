package com.liggiorgio.rumapp.news;

public class NewsItem {

    private final int icon;
    private final String ref;
    private final String title;
    private final String date;
    private final String categories;
    private final String text;

    NewsItem(int icon, String ref, String title, String date, String categories, String text) {
        this.icon = icon;
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

    public int getIcon() {
        return icon;
    }
}