package com.liggiorgio.rumapp.cats;

public class CategoriesItem {

    private final int icon;
    private final String title;
    private final String path;

    public CategoriesItem(int icon, String title, String path) {
        this.icon = icon;
        this.title = title;
        this.path = path;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }
}
