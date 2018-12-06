/*
    This activity is extended by other activities to inherit
    the toolbar and other shared settings within the app.
*/

package com.liggiorgio.rumapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public abstract class ParentActivity extends AppCompatActivity {

    // Appcompat toolbar reference
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // Get the child layout to apply
    protected abstract int getLayoutResourceId();
}
