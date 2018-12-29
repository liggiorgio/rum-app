package com.liggiorgio.rumapp.reader;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class ReaderViewModel extends ViewModel implements AsyncResponse {

    private MutableLiveData<ArticleItem> article;
    private String articleUrl;

    public LiveData<ArticleItem> getArticle() {
        if (article == null) {
            article = new MutableLiveData<>();
            fetchArticle(articleUrl);
        }
        return article;
    }

    // Set the url to GET
    void setUrl(String url) {
        this.articleUrl = url;
    }

    // Get article content
    private void fetchArticle(String url) {
        new ArticleFetchAsyncTask(this).execute(url);
    }

    // Add fetched news to current list, or add placeholders
    // if no internet connection is available
    @Override
    public void processFinish(ArticleItem output) {
        if (output != null) {
            article.setValue(output);
        }
    }
}
