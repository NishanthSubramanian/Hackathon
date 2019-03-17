package com.example.hackathon;

import android.content.Context;
import android.text.format.DateUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Message {
    private String text;
    private Timestamp time;
    private String timeInString;
    private String id, senderUID;



    public void compute()
    {
        String s = id.substring(id.indexOf('+')+1,id.length());
        setSenderUID(s);
        this.timeInString = "05:30";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(Long.parseLong(getTime().toString())));
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

    public String getTimeInString() {
        return timeInString;
    }

    public void setTimeInString(String timeInString) {
        this.timeInString = timeInString;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderUID() {
        return senderUID;
    }

    public void setSenderUID(String senderUID) {
        this.senderUID = senderUID;
    }
}
