package com.example.hackathon;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Event implements Serializable {
    private String startDate;
    private Timestamp endDate;
    private String eventId;
    private boolean isFormal;
    private String title;
    private String description;
    private ArrayList<String> participantsId;
    private ArrayList<User> participants;
    private String location;
    private String category;

    public Event() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getParticipantsId() {
        return participantsId;
    }

    public void setParticipantsId(ArrayList<String> participantsId) {
        this.participantsId = participantsId;
    }

    public ArrayList<User> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<User> participants) {
        this.participants = participants;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
