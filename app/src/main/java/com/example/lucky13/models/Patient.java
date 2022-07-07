package com.example.lucky13.models;

import java.sql.Timestamp;
import java.util.List;

public class Patient {

    private String id;
    private String name;
    private String password;

    private Timestamp dateOfBirth;
    private String phoneNumber;

    private double height;
    private double weight;
    private boolean sex;

    private List<String> medicalRecord;

    // empty constructor
    public Patient() {
    }

    public Patient(String id, String name, String password, Timestamp dateOfBirth, String phoneNumber,
                   double height, double weight, boolean sex,
                   List<String> medicalRecord) {

        this.id = id;
        this.name = name;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.height = height;
        this.weight = weight;
        this.sex = sex;
        this.medicalRecord = medicalRecord;
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

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public List<String> getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(List<String> medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
}
