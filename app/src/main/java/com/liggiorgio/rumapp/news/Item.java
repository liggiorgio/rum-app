package com.liggiorgio.rumapp.news;

public interface Item {

    // 0 = news, 1 = section header
    int getType();

    // Title
    String getTitle();

    // Publish date
    String getDate();

    // Content
    String getText();
}
