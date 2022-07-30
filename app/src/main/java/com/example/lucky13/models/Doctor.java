package com.example.lucky13.models;

import java.util.ArrayList;

public class Doctor {

    private String UID;
    private String name;
    private String email;

    private String passcode;
    private String clinicId;
    private String medicalField;
    private ArrayList<String> language;

    private String phone;
    private double review;
    private String gender;

    public Doctor() {}

    public Doctor(String UID, String name, String email, String passcode,String clinicId,
                  String medicalField, ArrayList<String> language, String phone,
                  double review, String gender) {
        this.UID = UID;
        this.name = name;
        this.email = email;
        this.passcode = passcode;
        this.clinicId = clinicId;
        this.medicalField = medicalField;
        this.language = language;
        this.phone = phone;
        this.review = review;
        this.gender = gender;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public String getMedicalField() {
        return medicalField;
    }

    public void setMedicalField(String medicalField) {
        this.medicalField = medicalField;
    }

    public ArrayList<String> getLanguage() {
        return language;
    }

    public void setLanguage(ArrayList<String> language) {
        this.language = language;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getReview() {
        return review;
    }

    public void setReview(double review) {
        this.review = review;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}