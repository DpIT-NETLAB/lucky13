package com.example.lucky13.models;

import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class Clinic {

    private String UID;
    private String name;

    private double averageReview;
    private List<String> doctorUIDs;
    private GeoPoint location;

    public Clinic() {}

    public Clinic(String UID, String name, double averageReview, List<String> doctorUIDs, GeoPoint location) {
        this.UID = UID;
        this.name = name;
        this.averageReview = averageReview;
        this.doctorUIDs = doctorUIDs;
        this.location = location;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
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

    public List<String> getDoctorUIDs() {
        return doctorUIDs;
    }

    public void setDoctorUIDs(List<String> doctorUIDs) {
        this.doctorUIDs = doctorUIDs;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}
