package com.example.hackathon;

import android.content.Context;
import android.text.format.DateUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Message {
    private String text;
    private Timestamp time;
    private String uid;
    private Context context;
    public void compute()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(Long.parseLong(getTime().toString())));
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
