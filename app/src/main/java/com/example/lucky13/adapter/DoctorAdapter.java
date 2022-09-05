package com.example.lucky13.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lucky13.R;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lucky13.activities.patient_path.DoctorPresentationActivity;
import com.example.lucky13.models.Clinic;
import com.example.lucky13.models.Doctor;
import com.example.lucky13.service.ClinicService;

import java.util.ArrayList;


public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorHolder> {
    private static final String TAG = "DOCTORS LIST";

    ClinicService clinicService = new ClinicService();

    class DoctorHolder extends RecyclerView.ViewHolder {
        private TextView mDoctorName, mDoctorMedicalField, mDoctorGender, mDoctorReview;

        DoctorHolder(View itemView, Context context) {
            super(itemView);
            mDoctorName = itemView.findViewById(R.id.doctor_name);
            mDoctorMedicalField = itemView.findViewById(R.id.doctor_medical_field);
            mDoctorGender = itemView.findViewById(R.id.doctor_gender);
            mDoctorReview = itemView.findViewById(R.id.doctor_review);

//            mDoctorName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                }
//            });

        }

        void setDetails(Doctor doctor, Location location) {
            mDoctorName.setText(doctor.getName());
            mDoctorMedicalField.setText(doctor.getMedicalField());
            mDoctorGender.setText(doctor.getGender());
            String review = Double.toString(doctor.getReview());
            mDoctorReview.setText(review);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "CLICKED!!!!!");
                    Intent intent = new Intent(context, DoctorPresentationActivity.class);
                    intent.putExtra("id", doctor.getUID());
                    intent.putExtra("name", doctor.getName());
                    intent.putExtra("field", doctor.getMedicalField());
                    intent.putExtra("location", location);
                    Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + location.getLatitude());
                    clinicService.getAllClinics();
                    clinicService.clinicList.observe((LifecycleOwner) context, clinicList -> {
                        for (Clinic clinic : clinicList) {
                            if (clinic.getDoctorUIDs().contains(doctor.getUID())) {
                                intent.putExtra("clinicLocation", location);
                                Log.d(TAG, "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% " + location.getLatitude());
                                context.startActivity(intent);
                            }
                        }
                    });
                }
            });

        }

    }

    Context context;
    private ArrayList<Doctor> doctorArrayList;
    Location location;

    public DoctorAdapter(Context context, ArrayList<Doctor> doctorArrayList, Location location) {
        this.context = context;
        this.doctorArrayList = doctorArrayList;
        this.location = location;
    }

    @NonNull
    @Override
    public DoctorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_card_layout, parent, false);
        DoctorHolder doctorHolder =  new DoctorHolder(view, context);
        return doctorHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorHolder holder, int position) {

        Doctor doctor = doctorArrayList.get(position);

        holder.setDetails(doctor, location);


    }

    @Override
    public int getItemCount() {
        return doctorArrayList.size();
    }


}