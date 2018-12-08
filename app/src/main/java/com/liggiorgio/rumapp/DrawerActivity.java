/*
    This class extends the ParentActivity, adding a navigation drawer along
    with the shared toolbar to an activity. The extending class' layout is
    inflated right after loading this class layout.
*/

package com.liggiorgio.rumapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.LinearLayout;

public abstract class DrawerActivity extends ParentActivity {

    private int childLayoutId;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate child layout in parent
        LinearLayout contentLayout = findViewById(R.id.content_layout);
        getLayoutInflater().inflate(childLayoutId, contentLayout);

        // Enable home button
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // Handle navigation click events
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // Set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // Close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // TODO: Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResourceId() {
        childLayoutId = this.getChildLayoutResourceId();
        return R.layout.activity_drawer;
    }

    // Get the layout of extending class, that will be inflated
    protected abstract int getChildLayoutResourceId();

}
