package com.example.lucky13.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lucky13.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lucky13.models.Doctor;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorHolder> {

    class DoctorHolder extends RecyclerView.ViewHolder {
        private TextView mDoctorName, mDoctorMedicalField, mDoctorGender, mDoctorReview;

        DoctorHolder(View itemView) {
            super(itemView);
            mDoctorName = itemView.findViewById(R.id.doctor_name);
            mDoctorMedicalField = itemView.findViewById(R.id.doctor_medical_field);
            mDoctorGender = itemView.findViewById(R.id.doctor_gender);
            mDoctorReview = itemView.findViewById(R.id.doctor_review);
        }

        void setDetails(Doctor doctor) {
            mDoctorName.setText(doctor.getName());
            mDoctorMedicalField.setText(doctor.getMedicalField());
            mDoctorGender.setText(doctor.getGender());
            String review = Double.toString(doctor.getReview());
            mDoctorReview.setText(review);

        }

    }

    private Context context;
    private ArrayList<Doctor> doctorArrayList;

    public DoctorAdapter(Context context, ArrayList<Doctor> doctorArrayList) {
        this.context = context;
        this.doctorArrayList = doctorArrayList;
    }

    @NonNull
    @Override
    public DoctorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_card_layout, parent, false);
        return new DoctorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorHolder holder, int position) {

        Doctor doctor = doctorArrayList.get(position);

        holder.setDetails(doctor);

    }

    @Override
    public int getItemCount() {
        return doctorArrayList.size();
    }


}
