package com.liggiorgio.rumapp.news;

public abstract class SectionItem implements Item {
    private final String title;

    SectionItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String getDate() {
        return null;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public int getType() {
        return 1;
    }
}
