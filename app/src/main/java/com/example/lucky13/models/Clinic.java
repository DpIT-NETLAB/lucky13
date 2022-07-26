package com.example.lucky13.models;

import java.util.List;

public class Clinic {

    private String id;
    private String name;

    private double averageReview;
    private List<String> doctorIds;
    private String location;

    public Clinic() {}

    public Clinic(String id, String name, double averageReview, List<String> doctorIds, String location) {
        this.id = id;
        this.name = name;
        this.averageReview = averageReview;
        this.doctorIds = doctorIds;
        this.location = location;
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

    public double getAverageReview() {
        return averageReview;
    }

    public void setAverageReview(double averageReview) {
        this.averageReview = averageReview;
    }

    public List<String> getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(List<String> doctorIds) {
        this.doctorIds = doctorIds;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
