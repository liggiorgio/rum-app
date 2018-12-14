package com.liggiorgio.rumapp.news;

public class SectionItem implements Item {
    private final String title;

    SectionItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getType() {
        return 1;
    }
}