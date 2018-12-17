package com.liggiorgio.rumapp.news;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModel extends ViewModel implements AsyncResponse {

    private ArrayList<Item> allNews;
    private MutableLiveData<List<Item>> allNewsLive;

    public LiveData<List<Item>> getNews() {
        if (allNewsLive == null) {
            allNews = new ArrayList<>();
            allNewsLive = new MutableLiveData<>();
            loadNews();
        }
        return allNewsLive;
    }

    private void loadNews() {
        // Do an asynchronous operation to fetch all news.
        List<Item> updateList = new ArrayList<>();
        String[] titles;

        // Get allNewsLive
        String url = "http://www.reteuniversitariamediterranea.it/page/1/?s";
        new HttpAsyncTask(this).execute(url);

        /*/ Header
        updateList.add(new SectionItem("In evidenza"));

        // State Name
        updateList.add(new NewsItem("Notizia in evidenza"));

        // Header
        updateList.add(new SectionItem("Ultime allNewsLive"));
        // State Name
        for (int i=0; i<10; i++)
            updateList.add(new NewsItem("Notizia " + (i+1)));

        // Update list
        allNewsLive.setValue(updateList);*/
    }

    @Override
    public void processFinish(ArrayList<String> output) {
        for (String aResult : output)
            allNews.add(new NewsItem(aResult));

        // Update list
        allNewsLive.setValue(allNews);
    }
}
