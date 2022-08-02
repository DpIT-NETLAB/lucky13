package com.example.lucky13.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lucky13.R;
import com.example.lucky13.models.Question;
import com.example.lucky13.service.QuestionService;

import java.util.ArrayList;

public class PatientQuestionActivity extends AppCompatActivity {

    QuestionService questionService = new QuestionService();
    ArrayList<Question> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_question);

        questionService.questionList.observe(this, questionsList -> {
            questions.addAll(questionsList);

            //TODO: sa rezolvam treaba cu calcularea celor mai relevante questions
            // SI DUPA SA MAI SI TESTAM PE CE INTENT SUNTEM CA SA STIM CE INTREBARE SA AFISAM
            getMostRelevantQuestions();
        });
    }

    public void getMostRelevantQuestions() {

    }
}