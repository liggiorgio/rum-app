package com.liggiorgio.rumapp.cats;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.liggiorgio.rumapp.R;

import java.util.ArrayList;
import java.util.List;

public class CategoriesViewModel extends ViewModel {

    private MutableLiveData<List<CategoriesItem>> allCats;

    public MutableLiveData<List<CategoriesItem>> getCats() {
        if (allCats == null) {
            allCats = new MutableLiveData<>();
            loadCats();
        }
        return allCats;
    }

    // Application categories
    private void loadCats() {
        ArrayList<CategoriesItem> cats = new ArrayList<>();
        for (int i=0; i<5; i++) {
            cats.add(new CategoriesItem(R.drawable.ic_about_star, "Ultime notizie", null));
            cats.add(new CategoriesItem(R.drawable.ic_about_compass, "Orientamento didattico", null));
            cats.add(new CategoriesItem(R.drawable.ic_about_contact, "Segreterie", null));
        }
        allCats.setValue(cats);
    }
}
