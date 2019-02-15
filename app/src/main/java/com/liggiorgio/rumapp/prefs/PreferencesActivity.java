package com.liggiorgio.rumapp.prefs;

import android.os.Bundle;
import com.liggiorgio.rumapp.DrawerActivity;
import com.liggiorgio.rumapp.R;

public class PreferencesActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int getChildLayoutResourceId() {
        return R.layout.activity_prefs;
    }

    @Override
    protected int getMenuItemId() {
        return 5;
    }
}
