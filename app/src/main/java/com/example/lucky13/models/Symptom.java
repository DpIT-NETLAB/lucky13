package com.example.lucky13.models;

public class Symptom {

    private String UID;
    private String name;

    private String relatedQuestion;

    public Symptom() {};

    public Symptom(String UID, String name, String relatedQuestion) {
        this.UID = UID;
        this.name = name;
        this.relatedQuestion = relatedQuestion;
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

    public String getRelatedQuestion() {
        return relatedQuestion;
    }

    public void setRelatedQuestion(String relatedQuestion) {
        this.relatedQuestion = relatedQuestion;
    }
}
