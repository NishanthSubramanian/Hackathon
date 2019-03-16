package com.example.hackathon;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Event {
    private Timestamp startDate;
    private Timestamp endDate;
    private boolean isFormal;
    private String name;
    private String description;
    private ArrayList<User> Participants;

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public boolean isFormal() {
        return isFormal;
    }

    public void setFormal(boolean formal) {
        isFormal = formal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<User> getParticipants() {
        return Participants;
    }

    public void setParticipants(ArrayList<User> participants) {
        Participants = participants;
    }
}
