package com.liggiorgio.rumapp.news;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<List<Item>> news;
    public LiveData<List<Item>> getNews() {
        if (news == null) {
            news = new MutableLiveData<>();
            loadNews();
        }
        return news;
    }

    private void loadNews() {
        // Do an asynchronous operation to fetch news.
        List<Item> updateList = new ArrayList<>();
        // Header
        updateList.add(new SectionItem("In evidenza"));

        // State Name
        updateList.add(new NewsItem("Notizia in evidenza"));

        // Header
        updateList.add(new SectionItem("Ultime news"));
        // State Name
        for (int i=0; i<10; i++)
            updateList.add(new NewsItem("Notizia " + (i+1)));

        // Update list
        news.setValue(updateList);
    }

}
