package com.liggiorgio.rumapp.about;

import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.widget.Button;
import com.liggiorgio.rumapp.DrawerActivity;
import com.liggiorgio.rumapp.R;

public class AboutActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getChildLayoutResourceId() {
        return R.layout.activity_about;
    }

    @Override
    protected int getMenuItemId() {
        return 2;
    }
}
