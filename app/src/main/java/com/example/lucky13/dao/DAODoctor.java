package com.example.lucky13.dao;

import com.example.lucky13.models.Doctor;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAODoctor {

    private DatabaseReference databaseReference;

    public DAODoctor() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(Doctor.class.getSimpleName());
    }

    public Task<Void> add(Doctor doctor) {

        return databaseReference.push().setValue(doctor);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap) {

        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key) {

        return databaseReference.child(key).removeValue();
    }
}
