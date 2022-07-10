package com.example.lucky13.dao;

import com.example.lucky13.models.Clinic;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOClinic {

    private DatabaseReference databaseReference;

    public DAOClinic() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(Clinic.class.getSimpleName());
    }

    public Task<Void> add(Clinic clinic) {

        return databaseReference.push().setValue(clinic);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap) {

        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key) {

        return databaseReference.child(key).removeValue();
    }
}
