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

    public LiveData<List<Item>> getNews() {
        if (latestNews == null) {
            allNews = new ArrayList<>();
            latestNews = new MutableLiveData<>();
            loadNews();
        }
        return latestNews;
    }

    void loadNews() {
        // Get news
        String url = "http://www.reteuniversitariamediterranea.it/page/" + page +"/?s";
        page++;
        new HttpAsyncTask(this).execute(url);
    }

    @Override
    public void processFinish(ArrayList<String> output) {
        //allNews.clear();
        for (String aResult : output)
            allNews.add(new NewsItem(aResult));

        // Update list
        latestNews.setValue(allNews);
    }
}
