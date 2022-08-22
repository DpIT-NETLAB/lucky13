package com.example.lucky13.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.lucky13.models.Disease;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DiseaseRepository {

    private static final String TAG = "DISEASE-READING-OPS";

    FirebaseFirestore firebaseFirestore;
    CollectionReference diseaseCollection;
    Map<String, Object> diseaseMap;

    public void firestoreInstance() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void setDiseaseCollection() {
        firestoreInstance();
        this.diseaseCollection = firebaseFirestore.collection("Diseases");
    }

    public MutableLiveData<ArrayList<Disease>> getAllDiseases() {

        MutableLiveData<ArrayList<Disease>> diseasesList = new MutableLiveData<>();
        ArrayList<Disease> tempDiseasesList = new ArrayList<>();

        firestoreInstance();
        setDiseaseCollection();

        diseaseCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Disease disease = new Disease(
                                (String) document.get("id"),
                                (String) document.get("name"),
                                (String) document.get("medicalField"),
                                (ArrayList<String>) document.get("symptomUIDs")
                        );

                        tempDiseasesList.add(disease);
                    }

                    diseasesList.setValue(tempDiseasesList);

                }
                else {
                    Log.d(TAG, "FAILED OPERATION: " + task.getException());
                }
            }
        });
        return diseasesList;
    }

    public Map<String, Object> getDiseaseMap(String UID) {
        diseaseMap = new HashMap<>();
        setDiseaseCollection();

        diseaseCollection.document(UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Log.d(TAG, "Document snapshot data: " + document.getData());

                        diseaseMap = document.getData();
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
        return diseaseMap;
    }

}
