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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {
    private Context context;
    private Integer type;
    private ArrayList<Event> events;
    private ArrayList<String> eventId;
    private HashMap<String, User> users;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private User user;

    public Boolean isUserPresent(String s){
        return users.containsKey(s);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,description, title, time;
        public CircleImageView photo;
        public ConstraintLayout constraintLayout;
        public Button accept;
        public CircleImageView isFavourite;

        public MyViewHolder(View view) {
            super(view);
            photo = view.findViewById(R.id.event_civ);
            name = view.findViewById(R.id.event_host);
            title = view.findViewById(R.id.event_title);
            time = view.findViewById(R.id.event_time);
            isFavourite = view.findViewById(R.id.event_fav_civ);
        }
    }


    public EventsAdapter(Context context, ArrayList<Event> events, User user, int type) {
        this.context = context;
        this.events = events;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.user = user;
        this.eventId = new ArrayList<>();
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
        Event event = events.get(position);
        Log.d("event",event.toString());
        holder.title.setText(event.getTitle());
        if(!event.getHostImage().equals("null")) {
            Glide.with(context).load(event.getHostImage()).into(holder.photo);
        }
        holder.time.setText(event.getStartDate());
        holder.name.setText(event.getHostName());

        if(type == 0){
            if(user.getSaved().contains(event.getEventId())){
                holder.isFavourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_black_24dp));
            }else{
                holder.isFavourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_border_black_24dp));
            }

            holder.isFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!user.getSaved().contains(event.getEventId())){
                        firebaseFirestore.collection("informal").document(firebaseAuth.getUid())
                                .update("saved", FieldValue.arrayUnion(event.getEventId()))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        user.getSaved().add(event.getEventId());
                                        holder.isFavourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_black_24dp));

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("failure@avedadd",e.toString());
                                    }
                                });
                    }else{
                        firebaseFirestore.collection("informal").document(firebaseAuth.getUid())
                                .update("saved", FieldValue.arrayRemove(event.getEventId()))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        user.getSaved().remove(event.getEventId());
                                        holder.isFavourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_border_black_24dp));

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("failure@avedadd",e.toString());
                                    }
                                });
                    }


                }
            });

        }else if (type == 1){

        }else if (type == 2){
            holder.isFavourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_black_24dp));
            holder.isFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseFirestore.collection("informal").document(firebaseAuth.getUid())
                            .update("saved", FieldValue.arrayRemove(event.getEventId()))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    user.getSaved().remove(event.getEventId());
                                    holder.isFavourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_border_black_24dp));
                                    removed(event.getEventId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("failure@avedadd",e.toString());
                                }
                            });
                }
            });
        }else{

        }

    }

    @Override
    public int getItemCount() {
       return events.size();
    }

    public void notifySaved(String s){
        int pos = eventId.indexOf(s);
        if(pos != -1){
            notifyItemChanged(pos);
        }

    }

    public void setUser(User user){
        this.user = user;
    }

    public void added(Event e){
        Log.d("added @ adapter", events.size()+"s");
        events.add(e);
        eventId.add(e.getEventId());
        notifyItemInserted(events.indexOf(e));
    }

    public void removed(String s){
        int pos = eventId.indexOf(s);
        if(pos != -1){
            events.remove(pos);
            eventId.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos, events.size());
        }

    }

    public ArrayList<String> getItems(){
        return this.eventId;
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