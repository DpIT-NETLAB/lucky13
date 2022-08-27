package com.example.lucky13.models;

import java.util.List;

public class Disease {
    private String UID;
    private String name;
    private String medicalField;

    private List<String> symptomUIDs;

    public Disease() {}

    public Disease(String UID, String name, String medicalField, List<String> symptomUIDs) {
        this.UID = UID;
        this.name = name;
        this.medicalField = medicalField;
        this.symptomUIDs = symptomUIDs;
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

    public String getMedicalField() {
        return medicalField;
    }

    public void setMedicalField(String medicalField) {
        this.medicalField = medicalField;
    }

    public List<String> getSymptomUIDs() { return symptomUIDs; }

    public void setSymptomUIDs(List<String> symptomUIDs) { this.symptomUIDs = symptomUIDs; }

}