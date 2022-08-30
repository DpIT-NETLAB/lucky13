package com.example.lucky13.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.lucky13.models.Clinic;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClinicRepository {
    private static final String TAG = "CLINIC-CRUD-OPERATIONS";

    FirebaseFirestore firebaseFirestore;
    CollectionReference clinicCollection;
    Map<String, Object> clinicMap;

    public void firestoreInstance() {

        this.firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void setClinicCollection() {

        firestoreInstance();
        this.clinicCollection = firebaseFirestore.collection("Clinics");
    }

    public MutableLiveData<ArrayList<Clinic>> getAllClinics() {

        MutableLiveData<ArrayList<Clinic>> clinicList = new MutableLiveData<>();
        ArrayList<Clinic> tempClinicList = new ArrayList<>();

        firestoreInstance();
        setClinicCollection();

        clinicCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Clinic clinic = new Clinic(
                                (String) document.get("id"),
                                (String) document.get("name"),
                                (double) document.get("averageReview"),
                                (List<String>) document.get("doctorUIDs"),
                                (GeoPoint) document.get("location")
                        );

                        tempClinicList.add(clinic);
                    }

                    clinicList.setValue(tempClinicList);
                } else {
                    Log.d(TAG, "FAILED OPERATION: " + task.getException());
                }
            }
        });

        return clinicList;
    }

    public Map<String, Object> getClinic(String UID) {

        clinicMap = new HashMap<>();
        setClinicCollection();

        clinicCollection.document(UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Log.d(TAG, "Document snapshot data: " + document.getData());

                        clinicMap = document.getData();
                    } else {
                        Log.d(TAG, "No document with given UID");
                    }
                } else {
                    Log.d(TAG, "FAILED OPERATION: " + task.getException());
                }
            }
        });

        return clinicMap;
    }

}
