package com.liggiorgio.rumapp.news;

public class NewsItem implements Item {
    public final String title;

    NewsItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getType() {
        return 0;
    }
}