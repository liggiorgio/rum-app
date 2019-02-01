package com.liggiorgio.rumapp.about;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.widget.Button;
import android.widget.ScrollView;
import com.liggiorgio.rumapp.DrawerActivity;
import com.liggiorgio.rumapp.R;

public class AboutActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Restore scrolling Y offset
        if (savedInstanceState != null) {
            int offset = savedInstanceState.getInt(getString(R.string.key_scroll_height));
            ScrollView scroller = findViewById(R.id.about_scroller);
            // TODO: test whether KitKat actually scrolls view
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                scroller.setScrollY(offset);
            } else {
                scroller.post(() -> scroller.setScrollY(offset));
            }
        }
    }

    // Save current scrolling Y offset
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        int offset = findViewById(R.id.about_scroller).getScrollY();
        savedInstanceState.putInt(getString(R.string.key_scroll_height), offset);

        // Call superclass
        super.onSaveInstanceState(savedInstanceState);
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
