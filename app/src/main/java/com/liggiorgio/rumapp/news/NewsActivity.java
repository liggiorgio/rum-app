/*
    Home activity displaying a scrollable list of news
*/

package com.liggiorgio.rumapp.news;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import com.liggiorgio.rumapp.DrawerActivity;
import com.liggiorgio.rumapp.R;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class NewsActivity extends DrawerActivity {

    private ArrayList<Item> newsList;
    private RecyclerView newsRecyclerView;
    private RecyclerView.LayoutManager newsLayoutManager;
    private RecyclerView.Adapter newsAdapter;
    private NewsViewModel model;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // News list dataset
        newsList = new ArrayList<>();

        // Create view model for news list
        model = ViewModelProviders.of(this).get(NewsViewModel.class);
        model.getNews().observe(this, news -> {
            // Update UI
            newsList.clear();
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

        // Enable infinite scrolling
        scrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) newsLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Load more news while scrolling
                model.fetchNews();
            }
        };

        newsRecyclerView.addOnScrollListener(scrollListener);

        // Load previously fetched news
        model.pushList(loadList());

    }

    // Load previously stored news, if any
    private ArrayList<Item> loadList() {
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        HashSet<String> myHash = (HashSet<String>) prefs.getStringSet(getResources().getString(R.string.key_news_list), new HashSet<>());
        ArrayList<String> mySet = new ArrayList<>(myHash);
        Collections.sort(mySet);
        ArrayList<Item> news = new ArrayList<>();
        String[] temp;
        for (String s : mySet) {
            temp = s.split("§");
            news.add(new NewsItem(temp[1],temp[2],temp[3],temp[4],temp[5]));
        }
        return news;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.news_menu, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        HashSet<String> mySet = new HashSet<>();
        int c = 0;
        for (Item i : newsList) {
            mySet.add(StringUtils.leftPad(String.valueOf(c++), 4, '0') + "§" + i.getImage() + "§" + i.getRef() + "§" + i.getTitle() + "§" + i.getDate() + "§" + i.getText());
        }
        editor.putStringSet(getResources().getString(R.string.key_news_list), mySet);
        editor.apply();
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
