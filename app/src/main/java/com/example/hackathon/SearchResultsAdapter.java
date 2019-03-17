package com.example.hackathon;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder> {

    private ArrayList<Event> searchResults;
    private Context context;
    private User user;

    public static class SearchResultsViewHolder extends RecyclerView.ViewHolder {
        TextView eventHost;
        TextView eventTitle;
        TextView eventTime;
        CardView card;


        public SearchResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.item_event_cv);
            eventHost=itemView.findViewById(R.id.event_host);
            eventTitle=itemView.findViewById(R.id.event_title);
            eventTime=itemView.findViewById(R.id.event_time);
        }
    }

    public SearchResultsAdapter(Context context, ArrayList<Event> searchResults,User user) {
        this.searchResults = searchResults;
        this.context = context;
        this.user = user;
    }

    @NonNull
    @Override
    public SearchResultsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_event, viewGroup, false);
        SearchResultsViewHolder viewHolder = new SearchResultsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchResultsViewHolder viewHolder, final int i) {
        final Event searchResult = searchResults.get(i);

        viewHolder.eventHost.setText(searchResult.getHostName());
        viewHolder.eventTime.setText(searchResult.getStartDate());
        viewHolder.eventTitle.setText(searchResult.getTitle());

        viewHolder.card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,EventActivity.class);
                i.putExtra("event",searchResult);
                i.putExtra("informal",user);
                context.startActivity(i);
            }});
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public void added(Event searchResult) {
        searchResults.add(searchResult);
        notifyItemInserted(searchResults.indexOf(searchResult));
    }
}
