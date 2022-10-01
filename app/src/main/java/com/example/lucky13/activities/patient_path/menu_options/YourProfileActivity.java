package com.example.lucky13.activities.patient_path.menu_options;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lucky13.R;
import com.example.lucky13.activities.patient_path.side_bar.DrawerBaseActivity;
import com.example.lucky13.models.Patient;
import com.example.lucky13.service.PatientService;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class YourProfileActivity extends DrawerBaseActivity {

    TextView name,
            email,
            birthday,
            gender;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    PatientService patientService = new PatientService();
    ArrayList<Patient> allPatients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_profile);

        initWidgets();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {

            patientService.getAllPatients();
            patientService.patientList.observe(this, patients -> {

                allPatients.addAll(patients);

                displayUserData(firebaseUser);
            });
        }
    }

    private void initWidgets() {

        name = (TextView) findViewById(R.id.yourProfileFullNameText);
        email = (TextView) findViewById(R.id.yourProfileEmailText);
        birthday = (TextView) findViewById(R.id.yourProfileBirthdayText);
        gender = (TextView) findViewById(R.id.yourProfileGenderText);
    }

    @Nullable
    private Patient getPatientByUID(String UID) {

        for (Patient patient: allPatients) {

            if (patient.getUID().equals(UID))
                return patient;
        }

        return null;
    }

    private void displayUserData(@NonNull FirebaseUser firebaseUser) {

        String UID = firebaseUser.getUid();
        Patient patient = getPatientByUID(UID);

        assert patient != null;
        String fullName = patient.getFirstName() + " " + patient.getLastName();
        String mail = patient.getEmail();
        String birthdayF = patient.getDateOfBirth().getFirst().toString() + "/" +
                            patient.getDateOfBirth().getSecond().toString() + "/" +
                            patient.getDateOfBirth().getThird().toString();
        String sex = patient.getGender().equals("male")
                        ? "Male"
                        : "Female";

        name.setText(fullName);
        email.setText(mail);
        birthday.setText(birthdayF);
        gender.setText(sex);
    }
}