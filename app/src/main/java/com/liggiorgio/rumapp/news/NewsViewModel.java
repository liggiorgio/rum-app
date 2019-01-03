package com.liggiorgio.rumapp.news;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModel extends ViewModel implements AsyncResponse {

    private ArrayList<NewsItem> allNews;
    private MutableLiveData<List<NewsItem>> latestNews;
    private int page = 1;
    private boolean firstLoad = true;

    public LiveData<List<NewsItem>> getNews() {
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
    void pushList(ArrayList<NewsItem> stored) {
        //firstLoad = false;
        allNews.addAll(stored);
        latestNews.setValue(allNews);
    }

    // Add fetched news to current list
    @Override
    public void processFinish(ArrayList<NewsItem> output) {
        if (output.size() > 0) {
            if (firstLoad) {
                firstLoad = false;
                allNews.clear();
            }
            allNews.addAll(output);
            latestNews.setValue(allNews);
        }
    }
}
