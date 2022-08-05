package com.example.lucky13.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.lucky13.R;
import com.example.lucky13.models.Question;
import com.example.lucky13.models.Response;
import com.example.lucky13.models.Symptom;
import com.example.lucky13.service.QuestionService;
import com.example.lucky13.service.SymptomService;

import org.jetbrains.annotations.Contract;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kotlin.Triple;

public class PatientQuestionActivity extends AppCompatActivity {

    SymptomService symptomService = new SymptomService();
    QuestionService questionService = new QuestionService();
    Response responseService = new ResponseService();

    Triple<String, String, String> relevantQuestions = new Triple<>("", "", "");
    ArrayList<Question> questions = new ArrayList<>();
    ArrayList<String> passedDiseaseUIDs = new ArrayList<>();

    TextView questionTextView;
    RadioButton answearRadioButton1;
    RadioButton answearRadioButton2;
    RadioButton answearRadioButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_question);

        questionTextView = (TextView) findViewById(R.id.patientQuestionTextView);
        answearRadioButton1 = (RadioButton) findViewById(R.id.patientQuestionRadioButton1);
        answearRadioButton2 = (RadioButton) findViewById(R.id.patientQuestionRadioButton2);
        answearRadioButton3 = (RadioButton) findViewById(R.id.patientQuestionRadioButton3);

        Intent incomingIntent = getIntent();
        String pageOrder = incomingIntent.getStringExtra("pageOrder");
        ArrayList<String> symptoms = incomingIntent.getStringArrayListExtra("symptoms");

        switch (pageOrder) {
            case "1":

                passedDiseaseUIDs.clear();
                relevantQuestions = getMostRelevantQuestions(symptoms);

                Question firstQuestion = questionService.getQuestion(relevantQuestions.getFirst());
                ArrayList<String> questionResponses = firstQuestion.getReponses();


                questionTextView.setText(firstQuestion.getText());
                answearRadioButton1.setText(questionResponses.get(1));
                answearRadioButton2.setText(questionResponses.get(2));
                answearRadioButton3.setText(questionResponses.get(3));


                answearRadioButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Response response = responseService
                        passedDiseaseUIDs.addAll()
                    }
                });
            case "2":

                //TODO: LOAD THE NEXT PAGE
                break;
            case "3":

                //TODO: LOAD THE NEXT PAGE
                break;
        }

        questionService.questionList.observe(this, questionsList -> {

            questions.clear();
            questions.addAll(questionsList);

        });
    }

    @NonNull
    @Contract("_ -> new")
    private Triple<String, String, String> getMostRelevantQuestions(@NonNull ArrayList<String> symptoms) {

        Map<String, Integer> questionsFrequencyMap = new HashMap<>();

        for (String symptomUID: symptoms) {

            Symptom symptom = symptomService.getSymptom(symptomUID);

            for (String relatedQuestionUID: symptom.getRelatedQuestions()) {

                if (questionsFrequencyMap.containsKey(relatedQuestionUID))
                    questionsFrequencyMap.put(relatedQuestionUID, 1);
                else
                    questionsFrequencyMap.put(relatedQuestionUID, questionsFrequencyMap.get(relatedQuestionUID) + 1);
            }
        }

        Integer maxFreq1 = -1,
                maxFreq2 = -1,
                maxFreq3 = -1;
        String relevantQuestion1 = "",
                relevantQuestion2 = "",
                relevantQuestion3 = "";

        for (Map.Entry<String, Integer> entry: questionsFrequencyMap.entrySet()) {

            if (entry.getValue() > maxFreq3) {

                if (entry.getValue() >= maxFreq2) {

                    if (entry.getValue() >= maxFreq1) {
                        relevantQuestion3 = relevantQuestion2;
                        relevantQuestion2 = relevantQuestion1;
                        relevantQuestion1 = entry.getKey();
                    } else {
                        relevantQuestion3 = relevantQuestion2;
                        relevantQuestion2 = relevantQuestion1;
                        relevantQuestion2 = entry.getKey();
                    }
                } else {
                    relevantQuestion3 = relevantQuestion2;
                    relevantQuestion3 = entry.getKey();
                }
            }
        }

        return new Triple<String, String, String> (
                relevantQuestion1,
                relevantQuestion2,
                relevantQuestion3
        );
    }
}