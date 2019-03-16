package com.example.hackathon;



import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import java.util.ArrayList;

public class FormalCategoriesAdapter extends RecyclerView.Adapter<FormalCategoriesAdapter.FormalCategoriesViewHolder> {

    private ArrayList<String> categories;
    private Context context;

    public static class FormalCategoriesViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        CardView card;


        public FormalCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.formalCategory_tv);
            card = itemView.findViewById(R.id.formalCategory_cv);
        }
    }

    public FormalCategoriesAdapter(Context context, ArrayList<String> categories) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public FormalCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.formal_categories_layout, viewGroup, false);
        FormalCategoriesViewHolder viewHolder = new FormalCategoriesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FormalCategoriesViewHolder viewHolder, final int i) {
        final String category = categories.get(i);

        viewHolder.categoryName.setText(category);

        viewHolder.card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }});
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void added(String category) {
        categories.add(category);
        notifyItemInserted(categories.indexOf(category));
    }
}
