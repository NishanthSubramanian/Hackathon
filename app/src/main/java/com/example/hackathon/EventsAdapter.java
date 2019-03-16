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
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {
    private Context context;
    private Integer type;
    private ArrayList<Event> events;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, tags, landmark, distance;
        public CircleImageView photo;
        public ConstraintLayout constraintLayout;
        public Button accept;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.event_title);
            photo = view.findViewById(R.id.event_host);
        }
    }


    public EventsAdapter(Context context, ArrayList<Event> events, Integer type) {
        this.context = context;
        this.events = events;
        this.type = type;
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


    }

    @Override
    public int getItemCount() {
       return events.size();
    }

   /* public void added(Services c){
        Log.d("added @ adapter", servicesArrayList.size()+"s");
        servicesArrayList.add(c);
        notifyItemInserted(servicesArrayList.indexOf(c));
    }

    public void addedFromCustomer(Labourer labourer){
        Log.d("addedFromCustomer ", labourers.size()+"s");
        labourers.add(labourer);
        notifyItemInserted(labourers.indexOf(labourer));
    }

    public void setServiceAndCustomer(Services service, Customer customer){
        this.service = service;
        this.customer = customer;
    }*/
}