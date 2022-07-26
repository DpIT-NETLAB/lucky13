package com.example.lucky13.dao;

import com.example.lucky13.models.Disease;
import com.example.lucky13.models.Doctor;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAODisease {

    private DatabaseReference databaseReference;

    public DAODisease() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(Disease.class.getSimpleName());
    }

    public Task<Void> add(Disease disease) {

        return databaseReference.push().setValue(disease);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap) {

        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key) {

        return databaseReference.child(key).removeValue();
    }
}
