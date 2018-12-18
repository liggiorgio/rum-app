/*
    Provides functionality to the news list
*/

package com.liggiorgio.rumapp.news;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.liggiorgio.rumapp.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private ArrayList<Item> newsDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class NewsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ConstraintLayout mTextView;
        NewsViewHolder(ConstraintLayout v) {
            super(v);
            mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    NewsAdapter(ArrayList<Item> myDataset) {
        newsDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        ConstraintLayout v;
        if (viewType == 1)
            v = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section, parent, false);
        else
            v = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        // Section or news?
        return newsDataset.get(position).getType();
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        ((TextView) holder.mTextView.getChildAt(1)).setText(newsDataset.get(position).getTitle());
        ((TextView) holder.mTextView.getChildAt(2)).setText(newsDataset.get(position).getDate());
        ((TextView) holder.mTextView.getChildAt(3)).setText(newsDataset.get(position).getText());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return newsDataset.size();
    }
}