package com.liggiorgio.rumapp.news;

public interface Item {

    // 0 = news, 1 = section header
    int getType();

    // Header reference
    String getImage();

    // Article reference
    String getRef();

    // Title
    String getTitle();

    // Publish date
    String getDate();

    // Content
    String getText();
}
