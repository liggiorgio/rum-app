/*
    Provides functionality to the news list
*/

package com.liggiorgio.rumapp.news;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.liggiorgio.rumapp.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
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

    // Provide a suitable constructor (depends on the kind of dataset)
    NewsAdapter(ArrayList<NewsItem> myDataset) {
        newsDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a news view
        ConstraintLayout v;
        v = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        //holder.mImg.setImageDrawable();
        holder.mTitle.setText(newsDataset.get(position).getTitle());
        holder.mDate.setText(newsDataset.get(position).getDate());
        holder.mCats.setText(newsDataset.get(position).getCategories());
        holder.mText.setText(newsDataset.get(position).getText());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return newsDataset.size();
    }
}