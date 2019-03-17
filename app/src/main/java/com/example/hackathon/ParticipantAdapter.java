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

class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.MyViewHolder> {
    private Context context;
    private Integer type;
    private ArrayList<User> users;
    private ArrayList<String> eventId;
/*
    private HashMap<String, User> users;
*/
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private User user;

   /* public Boolean isUserPresent(String s){
        return users.containsKey(s);
    }*/

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,description, title, time;
        public CircleImageView photo;
        public ConstraintLayout constraintLayout;


        public MyViewHolder(View view) {
            super(view);
            photo = view.findViewById(R.id.participant_civ);
            name = view.findViewById(R.id.participant_name);

        }
    }


    public ParticipantAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
/*
        this.user = user;
*/
        this.eventId = new ArrayList<>();
/*
        this.type = type;
*/
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_participant, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ParticipantAdapter.MyViewHolder holder, final int position) {
        final User user = users.get(position);
        Log.d("user",user.toString());

        if(!user.getImage().equals("null")) {
            Glide.with(context).load(user.getImage()).into(holder.photo);
        }

        holder.name.setText(user.getName());

       /* holder.title.setText(event.getTitle());
        if(!event.getHostImage().equals("null")) {
            Glide.with(context).load(event.getHostImage()).into(holder.photo);
        }
        holder.time.setText(event.getStartDate());
        holder.name.setText(event.getHostName());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EventActivity.class);
                intent.putExtra("event",event);
                context.startActivity(intent);

            }
        });*/



    }

    @Override
    public int getItemCount() {
        return users.size();
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

    public void added(User e){
        Log.d("added @ adapter", users.size()+"s");
        users.add(e);
       // eventId.add(e.getEventId());
        notifyItemInserted(users.indexOf(e));
    }

   /* public void removed(String s){
        int pos = eventId.indexOf(s);
        if(pos != -1){
            events.remove(pos);
            eventId.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos, events.size());
        }

    }*/

   /* public ArrayList<String> getItems(){
        return this.eventId;
    }*/

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
