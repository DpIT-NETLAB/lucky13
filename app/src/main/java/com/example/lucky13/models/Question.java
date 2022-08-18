package com.example.lucky13.models;

import java.util.ArrayList;

public class Question implements java.io.Serializable {

    private String id;
    private String text;
    private ArrayList<String> reponses;


    public Question() {}

    public Question(String id, String text, ArrayList<String> reponses) {
        this.id = id;
        this.text = text;
        this.reponses = reponses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getResponses() {
        return reponses;
    }

    public void setReponses(ArrayList<String> responses) { this.reponses = responses; }
}
