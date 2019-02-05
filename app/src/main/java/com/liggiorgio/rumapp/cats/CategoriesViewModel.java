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
        //cats.add(new CategoriesItem(0, "", null));
        cats.add(new CategoriesItem(R.drawable.ic_topic_science, "Scienze di base e applicate", null));
        cats.add(new CategoriesItem(R.drawable.ic_topic_engineering, "Politecnica", null));
        cats.add(new CategoriesItem(R.drawable.ic_topic_law, "Scienze giuridiche ed economico-sociali", null));
        cats.add(new CategoriesItem(R.drawable.ic_topic_humanities, "Scienze umane e del patrimonio culturale", null));
        cats.add(new CategoriesItem(R.drawable.ic_topic_medicine, "Scuola di Medicina e Chirurgia", null));
        cats.add(new CategoriesItem(R.drawable.ic_topic_school, "Ateneo", null));
        cats.add(new CategoriesItem(R.drawable.ic_topic_erasmus, "Erasmus", null));
        cats.add(new CategoriesItem(R.drawable.ic_topic_management, "E.R.S.U.", null));
        cats.add(new CategoriesItem(R.drawable.ic_topic_management, "Consiglio di Amministrazione", null));
        cats.add(new CategoriesItem(R.drawable.ic_topic_school, "Senato accademico", null));
        cats.add(new CategoriesItem(R.drawable.ic_topic_sport, "C.U.S.", null));
        cats.add(new CategoriesItem(R.drawable.ic_topic_exam, "Test di ammissione", null));
        allCats.setValue(cats);
    }
}
