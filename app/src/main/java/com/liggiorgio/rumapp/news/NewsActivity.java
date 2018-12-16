/*
    Home activity displaying a scrollable list of news
*/

package com.liggiorgio.rumapp.news;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import com.liggiorgio.rumapp.DrawerActivity;
import com.liggiorgio.rumapp.R;

import java.util.ArrayList;

public class NewsActivity extends DrawerActivity {

    private RecyclerView newsRecyclerView;
    private RecyclerView.LayoutManager newsLayoutManager;
    private RecyclerView.Adapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // News list dataset
        ArrayList<Item> newsList = new ArrayList<>();

        // Create view model for news list
        NewsViewModel model = ViewModelProviders.of(this).get(NewsViewModel.class);
        model.getNews().observe(this, news -> {
            // Update UI
            newsList.clear();
            assert news != null;
            newsList.addAll(news);
            newsAdapter.notifyDataSetChanged();
        });

        // Reference the RecycleView
        newsRecyclerView = findViewById(R.id.news_list);

        // Add decorator
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(getResources().getDrawable(R.drawable.line_divider));
        newsRecyclerView.addItemDecoration(dividerItemDecoration);

        // RecyclerView layout never resize
        newsRecyclerView.setHasFixedSize(true);

        // Linear layout manager for content management
        newsLayoutManager = new LinearLayoutManager(this);
        newsRecyclerView.setLayoutManager(newsLayoutManager);

        // Adapter for items in the list
        newsAdapter = new NewsAdapter(newsList);
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

    @Override
    protected int getMenuItemId() {
        return 0;
    }

}
