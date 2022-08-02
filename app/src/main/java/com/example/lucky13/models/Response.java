package com.example.lucky13.models;

import java.util.ArrayList;

public class Response {

    private String id;
    private String text;
    private ArrayList<String> diseaseUIDs;


    public Response(String id, String text, ArrayList<String> diseaseUIDs) {
        this.id = id;
        this.text = text;
        this.diseaseUIDs = diseaseUIDs;
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

    public ArrayList<String> getDiseaseUIDs() {
        return diseaseUIDs;
    }

    public void setDiseaseUIDs(ArrayList<String> diseaseUIDs) {
        this.diseaseUIDs = diseaseUIDs;
    }
}
