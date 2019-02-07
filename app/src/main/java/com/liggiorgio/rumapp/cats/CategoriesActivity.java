package com.liggiorgio.rumapp.cats;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.liggiorgio.rumapp.DrawerActivity;
import com.liggiorgio.rumapp.R;
import com.liggiorgio.rumapp.news.*;

import java.util.ArrayList;

public class CategoriesActivity extends DrawerActivity {

    private ArrayList<CategoriesItem> catsList;
    private RecyclerView catsRecyclerView;
    private RecyclerView.LayoutManager catsLayoutManager;
    private RecyclerView.Adapter catsAdapter;
    private CategoriesViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Categories list dataset
        catsList = new ArrayList<>();

        // Create view model for news list
        model = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        model.getCats().observe(this, cats -> {
            // Update UI
            if (cats == null)
                return;
            catsList.clear();
            catsList.addAll(cats);
            catsAdapter.notifyDataSetChanged();
        });

        // Reference the RecycleView
        catsRecyclerView = findViewById(R.id.cats_list);

        // Add decorator
        int gridSpan = 2;
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(2, gridSpan);
        catsRecyclerView.addItemDecoration(dividerItemDecoration);

        // RecyclerView layout never resize
        catsRecyclerView.setHasFixedSize(true);

        // Linear layout manager for content management
        catsLayoutManager = new GridLayoutManager(this, gridSpan);
        catsRecyclerView.setLayoutManager(catsLayoutManager);

        // Adapter for items in the list
        catsAdapter = new CategoriesAdapter(catsList);
        catsRecyclerView.setAdapter(catsAdapter);

        catsRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, catsRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(CategoriesActivity.this, "Single Click on position: " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("PATH", catsList.get(position).getPath());
                intent.putExtra("CAT", catsList.get(position).getTitle());
                startActivity(intent);
                //finish();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(CategoriesActivity.this, "Long press on position: " + position, Toast.LENGTH_LONG).show();
            }
        }));
    }

    @Override
    protected int getChildLayoutResourceId() {
        return R.layout.activity_categories;
    }

    @Override
    protected int getMenuItemId() {
        return 1;
    }
}
