package com.example.lucky13.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.lucky13.models.Question;
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

public class QuestionRepository {

    private static final String TAG = "Question-READING-OPS";

    FirebaseFirestore firebaseFirestore;
    CollectionReference questionCollection;
    Map<String, Object> questionMap;

    public void firestoreInstance() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void setQuestionCollection() {
        firestoreInstance();
        this.questionCollection = firebaseFirestore.collection("Questions");
    }

    public MutableLiveData<ArrayList<Question>> getAllQuestions() {

        MutableLiveData<ArrayList<Question>> questionList = new MutableLiveData<>();
        ArrayList<Question> tempQuestionList = new ArrayList<>();

        firestoreInstance();
        setQuestionCollection();

        questionCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Question question = new Question(
                                (String) document.get("id"),
                                (String) document.get("text"),
                                (ArrayList<String>) document.get("responseUIDs")
                        );

                        tempQuestionList.add(question);
                    }

                    questionList.setValue(tempQuestionList);
                } else {
                    Log.d(TAG, "FAILED OPERATION: " + task.getException());
                }
            }
        });

        return questionList;
    }

    public Map<String, Object> getQuestionMap(String UID) {
        questionMap = new HashMap<>();
        setQuestionCollection();

        questionCollection.document(UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Log.d(TAG, "Document snapshot data: " + document.getData());

                        questionMap = document.getData();
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
        return questionMap;
    }
}
