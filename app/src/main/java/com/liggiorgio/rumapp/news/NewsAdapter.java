/*
    Provides functionality to the news list
*/

package com.liggiorgio.rumapp.news;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.liggiorgio.rumapp.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private ArrayList<NewsItem> newsDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView mImg;
        TextView mTitle, mDate, mCats, mText;
        NewsViewHolder(ConstraintLayout v) {
            super(v);
            mImg = (ImageView) v.getChildAt(0);
            mTitle = (TextView) v.getChildAt(1);
            mDate = (TextView) v.getChildAt(2);
            mCats = (TextView) v.getChildAt(3);
            mText = (TextView) v.getChildAt(4);
        }
    }

    // Loading progressbar view holder (loaded at the end of list)
    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.loadMore);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    NewsAdapter(ArrayList<NewsItem> myDataset) {
        newsDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a news view
        ConstraintLayout view;
        if (viewType == VIEW_TYPE_ITEM) {
            view = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
            return new NewsViewHolder(view);
        } else {
            view = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressbar, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Populate with news or add progress bar
        if (holder instanceof NewsViewHolder) {
            // It's a news item
            ((NewsViewHolder) holder).mImg.setImageResource(newsDataset.get(position).getIcon());
            ((NewsViewHolder) holder).mTitle.setText(newsDataset.get(position).getTitle());
            ((NewsViewHolder) holder).mDate.setText(newsDataset.get(position).getDate());
            ((NewsViewHolder) holder).mCats.setText(newsDataset.get(position).getCategories());
            ((NewsViewHolder) holder).mText.setText(newsDataset.get(position).getText());
        }
    }

    // Get item type, to show a news container or the loading icon
    @Override
    public int getItemViewType(int position) {
        return newsDataset.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return newsDataset == null ? 0 : newsDataset.size();
    }
}