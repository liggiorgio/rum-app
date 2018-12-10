/*
    Provides functionality to the news list
*/

package com.liggiorgio.rumapp.news;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        LinearLayout mTextView;
        NewsViewHolder(LinearLayout v) {
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
        LinearLayout v;
        if (viewType == 1)
            v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section, parent, false);
        else
            v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
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
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ((TextView) holder.mTextView.getChildAt(0)).setText(newsDataset.get(position).getTitle());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return newsDataset.size();
    }
}