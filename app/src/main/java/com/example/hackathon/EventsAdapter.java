package com.example.hackathon;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {
    private Context context;
    private Integer type;
    private ArrayList<Event> events;
    private HashMap<String, User> users;

    public Boolean isUserPresent(String s){
        return users.containsKey(s);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,description, title, time;
        public CircleImageView photo;
        public ConstraintLayout constraintLayout;
        public Button accept;
        public ImageView isFavourite;

        public MyViewHolder(View view) {
            super(view);
            photo = view.findViewById(R.id.event_civ);
            name = view.findViewById(R.id.event_host);
            title = view.findViewById(R.id.event_title);
            time = view.findViewById(R.id.event_time);
            isFavourite = view.findViewById(R.id.event_fav);
        }
    }


    public EventsAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_event, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final EventsAdapter.MyViewHolder holder, final int position) {
        Event event = events.get(position);

        holder.title.setText(event.getTitle());
        Glide.with(context).load(event.getHostImage()).into(holder.photo);
        holder.time.setText(event.getStartDate());
        holder.name.setText(event.getHostName());

    }

    @Override
    public int getItemCount() {
       return events.size();
    }

    public void added(Event e){
        Log.d("added @ adapter", events.size()+"s");
        events.add(e);
        notifyItemInserted(events.indexOf(e));
    }

    /*public void addedFromCustomer(Labourer labourer){
        Log.d("addedFromCustomer ", labourers.size()+"s");
        labourers.add(labourer);
        notifyItemInserted(labourers.indexOf(labourer));
    }

    public void setServiceAndCustomer(Services service, Customer customer){
        this.service = service;
        this.customer = customer;
    }*/
}