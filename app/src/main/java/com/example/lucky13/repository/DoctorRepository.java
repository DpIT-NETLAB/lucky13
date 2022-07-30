package com.example.lucky13.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lucky13.models.Doctor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class DoctorRepository {

    private static final String TAG = "DOCTOR-CRUD-OPERATIONS";

    FirebaseFirestore firebaseFirestore;
    CollectionReference doctorCollection;
    Map<String, Object> doctorMap;

    public void firestoreInstance() {

        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void setDoctorCollection() {

        firestoreInstance();
        doctorCollection = firebaseFirestore.collection("Doctors");
    }

    public DocumentReference getDocumentReference(String UID) {

        return doctorCollection.document(UID);
    }

    public ArrayList<Map<String, Object>> getAllDoctors() {

        ArrayList<Map<String, Object>> doctorsList = new ArrayList<>();

        doctorCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document: task.getResult()) {

                        doctorsList.add(document.getData());
                    }
                } else {
                    Log.d(TAG, "FAILED OPERATION: " + task.getException());
                }
            }
        });

        return doctorsList;
    }

    public Map<String, Object> getDoctor(String UID) {

        DocumentReference documentReference = getDocumentReference(UID);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Log.d(TAG, "Document snapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No document with given UID");
                    }

                    doctorMap = document.getData();
                } else {
                    Log.d(TAG, "FAILED OPERATION: " + task.getException());
                }
            }
        });

        return doctorMap;
    }

    public void addDoctor(@NonNull Doctor doctor, Map<String, Object> doctorMap) {

        setDoctorCollection();
        doctorCollection.document(doctor.getUID()).set(doctorMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Log.d(TAG, "DOCTOR ADDED TO THE FIRESTORE DATABASE");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "DOCTOR CANNOT BE ADDED TO THE FIRESTORE DATABASE");
                    }
                });
    }
}
