package com.example.lucky13.models;

import java.util.ArrayList;

import kotlin.Triple;

public class Patient {

    private String UID;
    private String firstName,
                    lastName;

    private Triple dateOfBirth;

    private String email;

    private double height;
    private double weight;
    private String gender;

    private ArrayList<String> medicalRecord;

    // empty constructor
    public Patient() {
    }

    public Patient(String UID, String firstName, String lastName, Triple dateOfBirth, String email,
                   double height, double weight, String gender,
                   ArrayList<String> medicalRecord) {

        this.UID = UID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.medicalRecord = medicalRecord;
    }

    public String getUID() {
        return UID;
    }
    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Triple getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Triple dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<String> getMedicalRecord() {
        return medicalRecord;
    }
    public void setMedicalRecord(ArrayList<String> medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
}
