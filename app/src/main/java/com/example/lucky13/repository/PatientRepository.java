package com.example.lucky13.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.lucky13.models.Patient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kotlin.Triple;

public class PatientRepository {

    private static final String TAG = "PATIENT-CRUD-OPERATIONS";

    FirebaseFirestore firebaseFirestore;
    CollectionReference patientCollection;
    Map<String, Object> patientMap;

    public void firestoreInstance() {

       this.firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void setPatientCollection() {

        firestoreInstance();
        this.patientCollection = firebaseFirestore.collection("Patients");
    }

    public MutableLiveData<ArrayList<Patient>> getAllPatients() {

        MutableLiveData<ArrayList<Patient>> patientList = new MutableLiveData<>();
        ArrayList<Patient> tempPatientList = new ArrayList<>();

        firestoreInstance();
        setPatientCollection();

        patientCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Patient patient = new Patient(
                                (String) document.get("id"),
                                (String) document.get("firstName"),
                                (String) document.get("lastName"),
                                new Triple(0, 0, 0),
                                (String) document.get("email"),
                                (double) document.get("height"),
                                (double) document.get("weight"),
                                (String) document.get("BMI"),
                                (String) document.get("gender"),
                                new ArrayList<>()
                        );

                        tempPatientList.add(patient);
                    }

                    patientList.setValue(tempPatientList);
                } else {
                    Log.d(TAG, "FAILED OPERATION: " + task.getException());
                }
            }
        });

        return patientList;
    }


    public Map<String, Object> getPatient(String UID) {

        patientMap = new HashMap<>();
        setPatientCollection();

        patientCollection.document(UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Log.d(TAG, "Document snapshot data: " + document.getData());

                        patientMap = document.getData();
                    } else {
                        Log.d(TAG, "No document with given UID");
                    }
                } else {
                    Log.d(TAG, "FAILED OPERATION: " + task.getException());
                }
            }
        });

        return patientMap;
    }

    public void addPatient(@NonNull Patient patient, Map<String, Object> patientMap) {

        setPatientCollection();

        patientCollection.document(patient.getUID()).set(patientMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Log.d(TAG, "PATIENT ADDED TO THE FIRESTORE DATABASE");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d(TAG, "PATIENT CANNOT BE ADDED TO THE FIRESTORE DATABASE");
                    }
                });
    }
}
