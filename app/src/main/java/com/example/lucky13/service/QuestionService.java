package com.example.lucky13.service;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.lucky13.models.Question;
import com.example.lucky13.repository.QuestionRepository;
import com.example.lucky13.utils.converters.QuestionConverter;

import java.util.ArrayList;

public class QuestionService {

    private final QuestionRepository repository = new QuestionRepository();
    private final QuestionConverter converter = new QuestionConverter();

    public LiveData<ArrayList<Question>> questionList;

    @NonNull
    public void getAllQuestions() {

        questionList = repository.getAllQuestions();
    }

    public Question getQuestion(String UID) {

        return converter.convertFromMapToEntity(repository.getQuestionMap(UID));
    }

}
