/*
    Home activity displaying a list of latest news
*/

package com.liggiorgio.rumapp;

import android.os.Bundle;

public class MainActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }
}
