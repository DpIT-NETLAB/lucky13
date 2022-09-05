package com.example.lucky13.models;

import java.util.ArrayList;

public class Symptom implements java.io.Serializable {

    private String id;
    private String name;

    private ArrayList<String> relatedQuestions;

    public Symptom() {};

    public Symptom(String id, String name, ArrayList<String> relatedQuestion) {
        this.id = id;
        this.name = name;
        this.relatedQuestions = relatedQuestion;
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

    public ArrayList<String> getRelatedQuestions() {
        return relatedQuestions;
    }

    public void setRelatedQuestions(ArrayList<String> relatedQuestions) {
        this.relatedQuestions = relatedQuestions;
    }
}