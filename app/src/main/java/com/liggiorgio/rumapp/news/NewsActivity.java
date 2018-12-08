/*
    Home activity displaying a scrollable list of news
*/

package com.liggiorgio.rumapp.news;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import com.liggiorgio.rumapp.DrawerActivity;
import com.liggiorgio.rumapp.R;

public class NewsActivity extends DrawerActivity {

    // TODO: create ViewModel to fetch remote news
    private String[] newsSet = createDataset();
    private RecyclerView newsRecyclerView;
    private RecyclerView.LayoutManager newsLayoutManager;
    private RecyclerView.Adapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Highlight the item from the navigation drawer
        ((NavigationView) findViewById(R.id.navigation_view)).getMenu().getItem(0).setChecked(true);

        // Reference the RecycleView
        newsRecyclerView = findViewById(R.id.news_list);

        // RecyclerView layout never resize
        newsRecyclerView.setHasFixedSize(true);

        // Linear layout manager for content management
        newsLayoutManager = new LinearLayoutManager(this);
        newsRecyclerView.setLayoutManager(newsLayoutManager);

        // Adapter for items in the list
        newsAdapter = new NewsAdapter(newsSet);
        newsRecyclerView.setAdapter(newsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.news_menu, menu);
        return true;
    }

    @Override
    protected int getChildLayoutResourceId() {
        return R.layout.activity_news;
    }

    // Placeholder function
    private String[] createDataset() {
        return new String[] {
                "Titolo", "Titolo", "Titolo",
                "Titolo", "Titolo", "Titolo",
                "Titolo", "Titolo", "Titolo",
                "Titolo", "Titolo", "Titolo",
                "Titolo", "Titolo", "Titolo",
        };
    }
}
