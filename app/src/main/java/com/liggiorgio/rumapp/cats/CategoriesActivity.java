package com.liggiorgio.rumapp.cats;

import android.os.Bundle;
import com.liggiorgio.rumapp.DrawerActivity;
import com.liggiorgio.rumapp.R;

public class CategoriesActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int getChildLayoutResourceId() {
        return R.layout.activity_categories;
    }

    @Override
    protected int getMenuItemId() {
        return 1;
    }
}
