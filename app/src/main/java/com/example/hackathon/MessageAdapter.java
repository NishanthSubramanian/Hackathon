package com.example.hackathon;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    MessageAdapter(Context context) {
        context = context;
    }

    private HashMap<String, Long> map;
    private Context context;
    private String uid;
    private ArrayList<Message> messages;
    public FirebaseFirestore firebaseFirestore;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public TextView time;
        public TextView name;
        private CardView cardview;
        public TextView text2;
        public TextView time2;
        public TextView name2;
        private CardView cardview2;
        public MyViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.message_text);
            time = view.findViewById(R.id.time);
            name = view.findViewById(R.id.sender);
            cardview = view.findViewById(R.id.card_view);
            text2 = view.findViewById(R.id.message_text2);
            time2 = view.findViewById(R.id.time2);
            name2 = view.findViewById(R.id.sender2);
            cardview2 = view.findViewById(R.id.card_view2);
        }


    }

//    public class MyViewHoilder2 extends RecyclerView.ViewHolder {
//        public MyViewHoilder2(View view){
//            super(view);
//        }
//
//    }


    public MessageAdapter(Context context, ArrayList<Message> messages, String uid) {
        this.context = context;
        this.messages = messages;
        this.uid = uid;
        this.firebaseFirestore = FirebaseFirestore.getInstance();
    }

//    @Override
//    public int getItemViewType(int position) {
//        if(getMessages().get(position).getUid().equals("xcsd")){
//            return 0;
//        }
//        return  1;
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.message_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Message item = messages.get(position);
        holder.text.setText(item.getText());
        holder.time.setText(item.getTimeInString());
        holder.name.setText(item.getId());
        Log.d("mes",item.toString());
        holder.cardview.setVisibility(View.VISIBLE);
        holder.cardview2.setVisibility(View.VISIBLE);
        holder.text2.setText(item.getText());
        holder.time2.setText(item.getTimeInString());
        holder.name2.setText(item.getId());

        if(getMessages().get(position).getSenderUID().equals(uid)){
            holder.cardview2.setVisibility(View.GONE);
            Log.d("HERE","asdkjfhajlsdhflk");
        }
        else{
            holder.cardview.setVisibility(View.GONE);
            Log.d("HERE2","asdkjfhajlsdhflk");
        }
    }

    @Override
    public int getItemCount() {

        return messages.size();

    }

    public void added(Message c) {
        Log.d("added @ adapter", messages.size() + "s");
        messages.add(c);
        notifyItemInserted(messages.indexOf(c));
    }

    public void remove(Message c) {

        int pos = messages.indexOf(c);
        messages.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, messages.size());
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<String> getMessageText() {
        ArrayList<String> s = new ArrayList<>();
        for (Message item : messages) {
            s.add(item.getText());
        }
        return s;
    }

    public void setMap(HashMap<String, Long> map) {
        this.map = map;
    }
}

