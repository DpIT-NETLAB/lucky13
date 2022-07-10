package com.example.lucky13.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.lucky13.R;
import com.example.lucky13.dao.DAOPatient;
import com.example.lucky13.models.Patient;
import com.example.lucky13.utils.Utils;
import com.example.lucky13.viewmodel.AuthViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.util.List;

import kotlin.Triple;

public class AddPatientFragment extends Fragment {

    private EditText heightText,
                    weightText;
    private EditText medicalRecord; //? ma gandesc la ceva gen add member pe github, adica atunci cand dai enter la un string, se separa de restul, dar cred ca e greu de implementat
    private DatePicker datePicker;
    private Switch sexButton;

    private AuthViewModel viewModel;
    private NavController navController;
    private FirebaseAuth fAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this , (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);

        viewModel.getLoggedStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLogged) {

                if (isLogged) { // in caz ca pacientul e logat, continua sa si bage datele

                    //navController.navigate(R.id.action_);
                    //TODO: dupa ce se face schema de la conceptul aplicatiei se va baga si actiunea din navgraph
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_patient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        heightText = view.findViewById(R.id.heightText);
        weightText = view.findViewById(R.id.weightText);
        sexButton = view.findViewById(R.id.sexRadioButton);
        datePicker = view.findViewById(R.id.datePicker);
        medicalRecord = view.findViewById(R.id.medicalRecordText);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = currentUser.getEmail();

        String heightString = heightText.getText().toString().trim();
        String weightString = weightText.getText().toString().trim();
        Boolean sexBoolean = sexButton.isChecked();
        String medicalRecordText = medicalRecord.getText().toString().trim();

        Integer birthDay = datePicker.getDayOfMonth();
        Integer birthMonth = datePicker.getMonth();
        Integer birthYear = datePicker.getYear();

        double height = 0,
                weight = 0;

        try {
            height = Utils.fromStringToDouble(heightString);
            weight = Utils.fromStringToDouble(weightString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Patient patient = new Patient(
                "123",
                "Ramane de vazut numele",
                "password trebuie scos",
                new Triple(birthDay, birthMonth, birthYear),
                "0785243299",
                height,
                weight,
                sexBoolean,
                null
                );

        DAOPatient daoPatient = new DAOPatient();

        daoPatient.add(patient).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(getContext(), "Patient added", Toast.LENGTH_SHORT).show();
                Log.d("ADD PATIENT", "CANNOT ADD PATIENT");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getContext(), "Patient cannot\n be added", Toast.LENGTH_SHORT).show();
                Log.d("ADD PATIENT", "CANNOT ADD PATIENT");
            }
        });
    }
}