/*
    Home activity displaying a scrollable list of news
*/

package com.liggiorgio.rumapp.news;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import com.liggiorgio.rumapp.DrawerActivity;
import com.liggiorgio.rumapp.R;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;

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

        // Show progress bar at first start
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        // News list dataset
        newsList = new ArrayList<>();

        // Create view model for news list
        model = ViewModelProviders.of(this).get(NewsViewModel.class);
        model.getNews().observe(this, news -> {
            // Update UI
            if (newsList.size() > 0)
                findViewById(R.id.progressBar).setVisibility(View.GONE); // Hide progress bar
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

        // Set smooth scrolling for view
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getApplicationContext()) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        // Enable title bar click to scroll view to top
        findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Scroll to top
                smoothScroller.setTargetPosition(0);
                newsLayoutManager.startSmoothScroll(smoothScroller);
            }
        });

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
        LinkedHashSet<String> mySet = new LinkedHashSet<>();
        int c = 0;
        for (Item i : newsList) {
            mySet.add(StringUtils.leftPad(String.valueOf(c++), 4, '0') + "§" + i.getImage() + "§" + i.getRef() + "§" + i.getTitle() + "§" + i.getDate() + "§" + i.getText());
            // Limit capacity to prevent app crashes
            if (c == 128)
                break;
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
