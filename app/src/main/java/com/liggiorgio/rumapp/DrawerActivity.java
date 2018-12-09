/*
    This class extends the ParentActivity, adding a navigation drawer along
    with the shared toolbar to an activity. The extending class' layout is
    inflated right after loading this class layout.
*/

package com.liggiorgio.rumapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import com.liggiorgio.rumapp.about.AboutActivity;
import com.liggiorgio.rumapp.cats.CategoriesActivity;
import com.liggiorgio.rumapp.conts.ContactsActivity;
import com.liggiorgio.rumapp.help.HelpActivity;
import com.liggiorgio.rumapp.info.InfoActivity;
import com.liggiorgio.rumapp.news.NewsActivity;
import com.liggiorgio.rumapp.places.PlacesActivity;

public abstract class DrawerActivity extends ParentActivity {

    private int childLayoutId;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Highlight the corresponding item in the navigation drawer
        ((NavigationView) findViewById(R.id.navigation_view)).getMenu().getItem(getMenuItemId()).setChecked(true);

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
                        switch (menuItem.getItemId()) {
                            case R.id.action_home: {
                                // Go to home activity
                                Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
                                startActivity(intent);
                                finish();
                            } break;
                            case R.id.action_cats: {
                                // Go to categories activity
                                Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
                                startActivity(intent);
                                finish();
                            } break;
                            case R.id.action_about: {
                                // Go to about activity
                                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                                startActivity(intent);
                                finish();
                            } break;
                            case R.id.action_contacts: {
                                // Go to contacts activity
                                Intent intent = new Intent(getApplicationContext(), ContactsActivity.class);
                                startActivity(intent);
                                finish();
                            } break;
                            case R.id.action_places: {
                                // Go to places activity
                                Intent intent = new Intent(getApplicationContext(), PlacesActivity.class);
                                startActivity(intent);
                                finish();
                            } break;
                            case R.id.action_help: {
                                // Go to FAQ activity
                                Intent intent = new Intent(getApplicationContext(), HelpActivity.class);
                                startActivity(intent);
                                finish();
                            } break;
                            case R.id.action_info: {
                                // Go to info activity
                                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                                startActivity(intent);
                                finish();
                            } break;
                            default: break;
                        }

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

    // Get id to highlight correct value in nav drawer
    protected abstract int getMenuItemId();

}
