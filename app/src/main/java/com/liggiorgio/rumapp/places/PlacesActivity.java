package com.liggiorgio.rumapp.places;

import android.os.Bundle;
import com.liggiorgio.rumapp.DrawerActivity;
import com.liggiorgio.rumapp.R;

public class PlacesActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int getChildLayoutResourceId() {
        return R.layout.activity_places;
    }

    @Override
    protected int getMenuItemId() {
        return 4;
    }
}
