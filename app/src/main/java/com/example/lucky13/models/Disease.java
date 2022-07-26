package com.example.lucky13.models;

import java.util.List;

public class Disease {
    private String id;
    private String name;
    private String medicalField;

    private List<String> symptoms;

    public Disease() {}

    public Disease(String id, String name, String medicalField, List<String> symptoms) {
        this.id = id;
        this.name = name;
        this.medicalField = medicalField;
        this.symptoms = symptoms;
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

    public String getMedicalField() {
        return medicalField;
    }

    public void setMedicalField(String medicalField) {
        this.medicalField = medicalField;
    }

    public List<String> getSymptoms() { return symptoms; }

    public void setSymptoms() { this.symptoms = symptoms; }

}
