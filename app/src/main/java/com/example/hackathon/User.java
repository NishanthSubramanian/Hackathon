package com.example.hackathon;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String name, year, image, id;
    private Long phone;
    private ArrayList<String> saved = new ArrayList<>();


    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", year='" + year + '\'' +
                ", image='" + image + '\'' +
                ", id='" + id + '\'' +
                ", phone=" + phone +
                ", saved=" + saved +
                '}';
    }

    public ArrayList<String> getSaved() {
        return saved;
    }

    public void setSaved(ArrayList<String> saved) {
        this.saved = saved;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
}
