package com.example.lucky13.models;

import java.util.List;

public class Doctor {

    private String id;
    private String name;
    private String password;

    private String email;
    private String clinicId;
    private String medicalField;
    private List<String> language;

    private String phone;
    private double review;
    private boolean sex;

    public Doctor() {}

    public Doctor(String id, String name, String password, String email, String clinicId,
                  String medicalField, List<String> language, String phone,
                  double review, boolean sex) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.clinicId = clinicId;
        this.medicalField = medicalField;
        this.language = language;
        this.phone = phone;
        this.review = review;
        this.sex = sex;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<String> getLanguage() {
        return language;
    }

    public void setLanguage(List<String> language) {
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

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
