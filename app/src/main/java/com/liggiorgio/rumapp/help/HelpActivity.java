package com.liggiorgio.rumapp.help;

import android.os.Bundle;
import com.liggiorgio.rumapp.DrawerActivity;
import com.liggiorgio.rumapp.R;

public class HelpActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int getChildLayoutResourceId() {
        return R.layout.activity_help;
    }

    @Override
    protected int getMenuItemId() {
        return 4;
    }
}
