/*
    Home activity displaying a scrollable list of news
*/

package com.liggiorgio.rumapp.news;

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

        // Create dataset
        ArrayList<Item> newsList = new ArrayList<>();

        // Fake fill dataset
        datasetFill(newsList);

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

    private void datasetFill(ArrayList<Item> newsList) {
        // Header
        newsList.add(new SectionItem("In evidenza"));

        // State Name
        newsList.add(new NewsItem("Notizia in evidenza"));

        // Header
        newsList.add(new SectionItem("Ultime news"));
        // State Name
        for (int i=0; i<10; i++)
            newsList.add(new NewsItem("Notizia " + (i+1)));
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
