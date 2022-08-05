package com.example.lucky13.activities.patient_path;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.lucky13.R;
import com.example.lucky13.activities.common_activities.WelcomePage;
import com.example.lucky13.models.Question;
import com.example.lucky13.models.Response;
import com.example.lucky13.models.Symptom;
import com.example.lucky13.service.QuestionService;
import com.example.lucky13.service.ResponseService;
import com.example.lucky13.service.SymptomService;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kotlin.Triple;

public class PatientQuestionActivity extends AppCompatActivity {

    SymptomService symptomService = new SymptomService();
    QuestionService questionService = new QuestionService();
    ResponseService responseService = new ResponseService();

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

        relevantQuestions = getMostRelevantQuestions(symptoms);

        System.out.println(relevantQuestions.getFirst() + "   *************************************************************************************************8");

        switch (pageOrder) {
            case "1":

                passedDiseaseUIDs.clear();

                Question firstQuestion = questionService.getQuestion(relevantQuestions.getFirst());
                ArrayList<String> questionResponses = firstQuestion.getResponses();


                questionTextView.setText(firstQuestion.getText());
                answearRadioButton1.setText(questionResponses.get(1));
                answearRadioButton2.setText(questionResponses.get(2));
                answearRadioButton3.setText(questionResponses.get(3));


                answearRadioButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Response response = responseService.getResponse(questionResponses.get(1));
                        passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                        Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                        intent.putExtra("pageOrder", "2");
                        intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                        startActivity(intent);
                    }
                });
                answearRadioButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Response response = responseService.getResponse(questionResponses.get(2));
                        passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                        Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                        intent.putExtra("pageOrder", "2");
                        intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                        startActivity(intent);
                    }
                });
                answearRadioButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Response response = responseService.getResponse(questionResponses.get(3));
                        passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                        Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                        intent.putExtra("pageOrder", "2");
                        intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                        startActivity(intent);
                    }
                });

            case "2":

                Question secondQuestion = questionService.getQuestion(relevantQuestions.getSecond());
                ArrayList<String> question_Responses = secondQuestion.getResponses();

                questionTextView.setText(secondQuestion.getText());
                answearRadioButton1.setText(question_Responses.get(1));
                answearRadioButton2.setText(question_Responses.get(2));
                answearRadioButton3.setText(question_Responses.get(3));

                answearRadioButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Response response = responseService.getResponse(question_Responses.get(2));
                        passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                        Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                        intent.putExtra("pageOrder", "3");
                        intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                        startActivity(intent);
                    }
                });
                answearRadioButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Response response = responseService.getResponse(question_Responses.get(3));
                        passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                        Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                        intent.putExtra("pageOrder", "3");
                        intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                        startActivity(intent);
                    }
                });
                answearRadioButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Response response = responseService.getResponse(question_Responses.get(3));
                        passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                        Intent intent = new Intent(PatientQuestionActivity.this, PatientQuestionActivity.class);
                        intent.putExtra("pageOrder", "3");
                        intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                        startActivity(intent);
                    }
                });

            case "3":

                Question thirdQuestion = questionService.getQuestion(relevantQuestions.getThird());
                ArrayList<String> thirdQuestionResponses = thirdQuestion.getResponses();

                questionTextView.setText(thirdQuestion.getText());
                answearRadioButton1.setText(thirdQuestionResponses.get(1));
                answearRadioButton2.setText(thirdQuestionResponses.get(2));
                answearRadioButton3.setText(thirdQuestionResponses.get(3));

                answearRadioButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Response response = responseService.getResponse(thirdQuestionResponses.get(1));
                        passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                        Intent intent = new Intent(PatientQuestionActivity.this, WelcomePage.class);
                        intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                        startActivity(intent);
                    }
                });
                answearRadioButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Response response = responseService.getResponse(thirdQuestionResponses.get(2));
                        passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                        Intent intent = new Intent(PatientQuestionActivity.this, WelcomePage.class);
                        intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                        startActivity(intent);
                    }
                });
                answearRadioButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Response response = responseService.getResponse(thirdQuestionResponses.get(3));
                        passedDiseaseUIDs.addAll(response.getDiseaseUIDs());

                        Intent intent = new Intent(PatientQuestionActivity.this, WelcomePage.class);
                        intent.putStringArrayListExtra("passedDiseaseUIDs", passedDiseaseUIDs);

                        startActivity(intent);
                    }
                });
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