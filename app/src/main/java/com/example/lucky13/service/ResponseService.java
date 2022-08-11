package com.example.lucky13.service;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.lucky13.models.Response;
import com.example.lucky13.repository.QuestionRepository;
import com.example.lucky13.repository.ResponseRepository;
import com.example.lucky13.utils.converters.QuestionConverter;
import com.example.lucky13.utils.converters.ResponseConverter;

import java.util.ArrayList;

public class ResponseService {

    private final ResponseRepository repository = new ResponseRepository();
    private final ResponseConverter converter = new ResponseConverter();

    public LiveData<ArrayList<Response>> responseList;

    @NonNull
    public void getAllResponses() {

        responseList = repository.getAllResponses();
    }

    public Response getResponse(String UID) {

        return converter.convertFormMapToEntity(repository.getResponseMap(UID));
    }

}
