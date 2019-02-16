package com.liggiorgio.rumapp.reader;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.preference.PreferenceManager;

public class ReaderViewModel extends ViewModel implements AsyncResponse {

    private MutableLiveData<ArticleItem> article;
    private MutableLiveData<Drawable> drawable;
    private String articleUrl;
    private boolean loadImages;

    public LiveData<ArticleItem> getArticle() {
        if (article == null) {
            article = new MutableLiveData<>();
            fetchArticle(articleUrl);
        }
        return article;
    }

    public LiveData<Drawable> getDrawable() {
        if (drawable == null) {
            drawable = new MutableLiveData<>();
        }
        return drawable;
    }

    // Set the url to GET
    void setUrl(String url) {
        this.articleUrl = url;
    }

    // Get article content
    private void fetchArticle(String url) {
        new ArticleFetchAsyncTask(this).execute(url);
    }

    // Add fetched news to current page
    @Override
    public void processFinish(ArticleItem output) {
        if (output != null) {
            article.setValue(output);
            if (loadImages) {
                new ImageLoadAsyncTask(this).execute(output.getImage());
            }
        }
    }

    public void setDrawable(Drawable drawable) {
        if (drawable != null)
            this.drawable.setValue(drawable);
    }

    public void setImagePref(boolean loadImages) {
        this.loadImages = loadImages;
    }
}
