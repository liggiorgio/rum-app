package com.liggiorgio.rumapp.reader;

public class ArticleItem {

    private final String title;
    private final String image;
    private final String author;
    private final String categories;
    private final String text;

    public ArticleItem(String title, String image, String author, String categories, String text) {
        this.title = title;
        this.image = image;
        this.author = author;
        this.categories = categories;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategories() {
        return categories;
    }

    public String getText() {
        return text;
    }
}
