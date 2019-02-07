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
        String root = "/category";
        //cats.add(new CategoriesItem(0, "", null));
        cats.add(new CategoriesItem(R.drawable.ic_topic_science, "Scienze di base e applicate", root + "/scuole/scienze-base"));
        cats.add(new CategoriesItem(R.drawable.ic_topic_engineering, "Politecnica", root + "/scuole/politecnica"));
        cats.add(new CategoriesItem(R.drawable.ic_topic_law, "Scienze giuridiche ed economico-sociali", root + "/scuole/scienze-giuridiche"));
        cats.add(new CategoriesItem(R.drawable.ic_topic_humanities, "Scienze umane e del patrimonio culturale", root + "/scuole/scienze-umane"));
        cats.add(new CategoriesItem(R.drawable.ic_topic_medicine, "Scuola di Medicina e Chirurgia", root + "/scuole/medicina-chirurgia"));
        cats.add(new CategoriesItem(R.drawable.ic_topic_school, "Ateneo", root + "/ateneo"));
        cats.add(new CategoriesItem(R.drawable.ic_topic_erasmus, "Erasmus", root + "/erasmus"));
        cats.add(new CategoriesItem(R.drawable.ic_topic_management, "E.R.S.U.", root + "/ersu"));
        cats.add(new CategoriesItem(R.drawable.ic_topic_management, "Consiglio di Amministrazione", root + "/consiglio-amministrazione"));
        cats.add(new CategoriesItem(R.drawable.ic_topic_school, "Senato accademico", root + "/senato"));
        cats.add(new CategoriesItem(R.drawable.ic_topic_sport, "C.U.S.", root + "/cus"));
        cats.add(new CategoriesItem(R.drawable.ic_topic_exam, "Test di ammissione", root + "/test-di-ammissione"));
        allCats.setValue(cats);
    }
}
