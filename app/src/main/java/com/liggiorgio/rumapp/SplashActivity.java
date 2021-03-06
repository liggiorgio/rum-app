/*
    This activity shows a splash screen to the user as long as the Android system
    is setting up the app, then moves to the main activity without wasting time.
*/

package com.liggiorgio.rumapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.liggiorgio.rumapp.news.NewsActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Start the main activity
        Intent intent = new Intent(this, NewsActivity.class);
        startActivity(intent);
        finish();
    }
}
