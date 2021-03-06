/*
    This class extends the ParentActivity, adding a navigation drawer along
    with the shared toolbar to an activity. The extending class' layout is
    inflated right after loading this class layout.
*/

package com.liggiorgio.rumapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import com.liggiorgio.rumapp.about.AboutActivity;
import com.liggiorgio.rumapp.cats.CategoriesActivity;
import com.liggiorgio.rumapp.help.HelpActivity;
import com.liggiorgio.rumapp.prefs.PreferencesActivity;
import com.liggiorgio.rumapp.news.NewsActivity;
import com.liggiorgio.rumapp.places.PlacesActivity;

public abstract class DrawerActivity extends ParentActivity {

    private int childLayoutId;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate child layout in parent
        LinearLayout contentLayout = findViewById(R.id.content_layout);
        getLayoutInflater().inflate(childLayoutId, contentLayout);

        // Enable home/up buttons
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);

        if (getMenuItemId() == 0)
            actionbar.setHomeAsUpIndicator(R.drawable.ic_action_menu);

        // Handle navigation click events
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    // Set item as selected to persist highlight
                    menuItem.setChecked(true);
                    // Close drawer when item is tapped
                    drawerLayout.closeDrawers();

                    // TODO: Add code here to update the UI based on the item selected
                    // For example, swap UI fragments here
                    switch (menuItem.getItemId()) {
                        case R.id.action_news: {
                            // Go to home activity
                            Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } break;
                        case R.id.action_categories: {
                            // Go to categories activity
                            Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
                            startActivity(intent);
                            //finish();
                        } break;
                        case R.id.action_about: {
                            // Go to about activity
                            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                            startActivity(intent);
                            //finish();
                        } break;
                        case R.id.action_info: {
                            // Go to info activity
                            Intent intent = new Intent(getApplicationContext(), PreferencesActivity.class);
                            startActivity(intent);
                            //finish();
                        } break;
                        default: break;
                    }
                    return true;
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Highlight the corresponding item in the navigation drawer
        ((NavigationView) findViewById(R.id.navigation_view)).getMenu().getItem(getMenuItemId()).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }

        // Otherwise defer to system default behavior.
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (getMenuItemId() == 0) {
                    drawerLayout.openDrawer(GravityCompat.START);
                    return true;
                }
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }
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

    // Get id to highlight correct value in nav drawer
    protected abstract int getMenuItemId();

}
