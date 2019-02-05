package com.liggiorgio.rumapp.cats;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.liggiorgio.rumapp.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    private final ArrayList<CategoriesItem> catsDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class CategoriesViewHolder extends RecyclerView.ViewHolder {
        ImageView mImg;
        TextView mTitle;
        public CategoriesViewHolder(ConstraintLayout v) {
            super(v);
            mImg = (ImageView) v.getChildAt(0);
            mTitle = (TextView) v.getChildAt(1);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    CategoriesAdapter(ArrayList<CategoriesItem> myDataset) {
        catsDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a categories view
        ConstraintLayout v;
        v = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false);
        return new CategoriesAdapter.CategoriesViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        holder.mImg.setImageResource(catsDataset.get(position).getIcon());
        holder.mTitle.setText(catsDataset.get(position).getTitle());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return catsDataset.size();
    }
}
