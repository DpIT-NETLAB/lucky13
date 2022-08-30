package com.example.lucky13.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.lucky13.models.Doctor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DoctorRepository {

    private static final String TAG = "DOCTOR-CRUD-OPERATIONS";

    FirebaseFirestore firebaseFirestore;
    CollectionReference doctorCollection;
    Map<String, Object> doctorMap;

    public void firestoreInstance() {

        this.firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void setDoctorCollection() {

        firestoreInstance();
        this.doctorCollection = firebaseFirestore.collection("Doctors");
    }

    public MutableLiveData<ArrayList<Doctor>> getAllDoctors() {

        MutableLiveData<ArrayList<Doctor>> doctorList = new MutableLiveData<>();
        ArrayList<Doctor> tempDoctorList = new ArrayList<>();

        firestoreInstance();
        setDoctorCollection();

        doctorCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Doctor doctor = new Doctor(
                                (String) document.get("id"),
                                (String) document.get("name"),
                                (String) document.get("email"),
                                (String) document.get("passcode"),
                                (String) document.get("clinicId"),
                                (String) document.get("medicalField"),
                                new ArrayList<String>(),
                                (String) document.get("phone"),
                                (double) document.get("review"),
                                (String) document.get("gender"),
                                (HashMap<String, String>) document.get("appointments"),
                                (HashMap<String, String>) document.get("workSchedule")
                        );

                        tempDoctorList.add(doctor);
                    }

                    doctorList.setValue(tempDoctorList);
                } else {
                    Log.d(TAG, "FAILED OPERATION: " + task.getException());
                }
            }
        });

        return doctorList;
    }

    public Map<String, Object> getDoctor(String UID) {

        doctorMap = new HashMap<>();
        setDoctorCollection();

        doctorCollection.document(UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Log.d(TAG, "Document snapshot data: " + document.getData());

                        doctorMap = document.getData();
                    } else {
                        Log.d(TAG, "No document with given UID");
                    }
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
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d(TAG, "DOCTOR CANNOT BE ADDED TO THE FIRESTORE DATABASE");
                    }
                });
    }
}
