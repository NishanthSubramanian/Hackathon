package com.example.hackathon;

import android.content.Context;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Message {
    private String text;
    private Long time;
    private String timeInString;
    private String id, senderUID;


    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", time=" + time +
                ", timeInString='" + timeInString + '\'' +
                ", id='" + id + '\'' +
                ", senderUID='" + senderUID + '\'' +
                '}';
    }

    public void compute()
    {   Log.d("csds",id+"");
        String s = id.substring(id.indexOf('+')+1,id.length());
        setSenderUID(s);
        String t = id.substring(0,id.indexOf('+'));
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.valueOf(t));
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
        Log.d("ded",date+"!");
        this.timeInString = date;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(Long.parseLong(getTime().toString())));
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
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
