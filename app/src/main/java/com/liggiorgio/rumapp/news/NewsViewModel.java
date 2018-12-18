package com.liggiorgio.rumapp.news;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModel extends ViewModel implements AsyncResponse {

    private ArrayList<Item> allNews;
    private MutableLiveData<List<Item>> latestNews;
    private int page = 1;
    private boolean offline = false;

    public LiveData<List<Item>> getNews() {
        if (latestNews == null) {
            allNews = new ArrayList<>();
            latestNews = new MutableLiveData<>();
            fetchNews();
        }
        return latestNews;
    }

    // Get latest news
    void fetchNews() {
        String url = "http://www.reteuniversitariamediterranea.it/page/" + page +"/?s";
        page++;
        new NewsFetchAsyncTask(this).execute(url);
    }

    // Receive an offline set of data
    void pushList(ArrayList<Item> stored) {
        offline = true;
        allNews.addAll(stored);
        latestNews.setValue(allNews);
    }

    // Add fetched news to current list, or add placeholders
    // if no internet connection is available
    @Override
    public void processFinish(ArrayList<Item> output) {
        if (output.size() > 0) {
            if (offline) {
                allNews.clear();
                offline = false;
            }
            allNews.addAll(output);
            latestNews.setValue(allNews);
        }
    }
}
