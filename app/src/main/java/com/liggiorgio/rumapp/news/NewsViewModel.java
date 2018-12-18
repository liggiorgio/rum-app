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

    // Get latest news
    void loadNews() {
        String url = "http://www.reteuniversitariamediterranea.it/page/" + page +"/?s";
        page++;
        new NewsFetchAsyncTask(this).execute(url);
    }

    // Add fetched news to current list, or add placeholders
    // if no internet connection is available
    @Override
    public void processFinish(ArrayList<Item> output) {
        if (output.size() > 0) {
            allNews.addAll(output);
        } else {
            for (int i=0; i<5; i++)
                allNews.add(new NewsItem(null,null,"Notizia " + (i+1+(page-2)*5),"Adesso","Testo."));
        }
        // Update list
        latestNews.setValue(allNews);
    }
}
