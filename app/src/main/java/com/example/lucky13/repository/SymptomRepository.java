package com.example.lucky13.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lucky13.models.Symptom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymptomRepository {

    private static final String TAG = "SYMPTOM-READING-OPS";

    FirebaseFirestore firebaseFirestore;
    CollectionReference symptomCollection;
    Map<String, Object> symptomMap;

    public void firestoreInstance() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void setSymptomCollection() {
        firestoreInstance();
        this.symptomCollection = firebaseFirestore.collection("Symptoms");
    }

    public MutableLiveData<ArrayList<Symptom>> getAllSymptoms() {

        MutableLiveData<ArrayList<Symptom>> symptomList = new MutableLiveData<>();
        ArrayList<Symptom> tempSymptomList = new ArrayList<>();

        firestoreInstance();
        setSymptomCollection();

        symptomCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Symptom symptom = new Symptom(
                                (String) document.get("id"),
                                (String) document.get("name"),
                                (ArrayList<String>) document.get("relatedQuestion")
                        );

                        tempSymptomList.add(symptom);
                    }

                    symptomList.setValue(tempSymptomList);
                } else {
                    Log.d(TAG, "FAILED OPERATION: " + task.getException());
                }
            }
        });

        return symptomList;
    }

    public Map<String, Object> getSymptomMap(String UID) {
        symptomMap = new HashMap<>();
        setSymptomCollection();

        symptomCollection.document(UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Log.d(TAG, "Document snapshot data: " + document.getData());

                        symptomMap = document.getData();
                    }
                    else {
                        Log.d(TAG, "No document with given UID");
                    }
                }
                else {
                    Log.d(TAG, "FAILED OPERATION: " + task.getException());
                }
            }
        });
        return symptomMap;
    }

}
