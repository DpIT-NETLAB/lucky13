package com.example.lucky13.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.lucky13.models.Response;
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

public class ResponseRepository {

    private static final String TAG = "Response-READING-OPS";

    FirebaseFirestore firebaseFirestore;
    CollectionReference responseCollection;
    Map<String, Object> responseMap;

    public void firestoreInstance() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void setResponseCollection() {
        firestoreInstance();
        this.responseCollection = firebaseFirestore.collection("Responses");
    }

    public MutableLiveData<ArrayList<Response>> getAllResponses() {

        MutableLiveData<ArrayList<Response>> responseList = new MutableLiveData<>();
        ArrayList<Response> tempResponseList = new ArrayList<>();

        firestoreInstance();
        setResponseCollection();

        responseCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Response response = new Response(
                                (String) document.get("id"),
                                (String) document.get("text"),
                                (ArrayList<String>) document.get("diseaseUIDs")
                        );

                        tempResponseList.add(response);
                    }

                    responseList.setValue(tempResponseList);
                } else {
                    Log.d(TAG, "FAILED OPERATION: " + task.getException());
                }
            }
        });

        return responseList;
    }

    public Map<String, Object> getResponseMap(String UID) {
        responseMap = new HashMap<>();
        setResponseCollection();

        responseCollection.document(UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Log.d(TAG, "Document snapshot data: " + document.getData());

                        responseMap = document.getData();
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
        return responseMap;
    }
}
