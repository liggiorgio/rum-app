package com.liggiorgio.rumapp.reader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.Toast;
import com.liggiorgio.rumapp.ParentActivity;
import com.liggiorgio.rumapp.R;

public class ReaderActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable home/up buttons
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);

        // Show toast
        Intent intent = getIntent();
        int position = intent.getIntExtra("NUMBER", -1);
        Toast.makeText(ReaderActivity.this, "Single Click on position: " + position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_reader;
    }
}
