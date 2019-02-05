package com.liggiorgio.rumapp.cats;

public class CategoriesItem {

    private final int icon;
    private final String title;
    private final String ref;

    public CategoriesItem(int icon, String title, String ref) {
        this.icon = icon;
        this.title = title;
        this.ref = ref;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getRef() {
        return ref;
    }
}
