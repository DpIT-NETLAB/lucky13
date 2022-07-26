package com.example.lucky13.dao;

import com.example.lucky13.models.Patient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOPatient {

    private DatabaseReference databaseReference;

    public DAOPatient() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(Patient.class.getSimpleName());
    }

    public Task<Void> add(Patient patient) {

        return databaseReference.push().setValue(patient);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap) {

        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key) {

        return databaseReference.child(key).removeValue();
    }
}
