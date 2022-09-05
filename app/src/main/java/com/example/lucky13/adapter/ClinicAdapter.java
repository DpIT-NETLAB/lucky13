package com.example.lucky13.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lucky13.R;
import com.example.lucky13.activities.patient_path.FindDoctorsNearby;
import com.example.lucky13.models.Clinic;
import com.example.lucky13.models.Disease;

import java.util.ArrayList;

public class ClinicAdapter extends RecyclerView.Adapter<ClinicAdapter.ClinicHolder> {

    class ClinicHolder extends RecyclerView.ViewHolder {
        private TextView mClinicName, mClinicReview;

        ClinicHolder(View itemView) {
            super(itemView);
            mClinicName = itemView.findViewById(R.id.doctor_name);
            mClinicReview = itemView.findViewById(R.id.doctor_review);
        }

        void setDetails(Clinic clinic) {
            mClinicName.setText(clinic.getName());
            String review = Double.toString(clinic.getAverageReview());
            mClinicReview.setText(review);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("CLinic card", "CLICKED!!!!!");
                    Intent intent = new Intent(context, FindDoctorsNearby.class);
                    intent.putExtra("clinic", clinic.getUID());
                    intent.putExtra("number", "2");
                    context.startActivity(intent);
                }
            });
        }

    }

    private Context context;
    private ArrayList<Clinic> clinicArrayList;

    public ClinicAdapter(Context context, ArrayList<Clinic> clinicArrayList) {
        this.context = context;
        this.clinicArrayList = clinicArrayList;
    }

    @NonNull
    @Override
    public ClinicAdapter.ClinicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_card_layout, parent, false);
        return new ClinicAdapter.ClinicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClinicAdapter.ClinicHolder holder, int position) {

        Clinic clinic = clinicArrayList.get(position);

        holder.setDetails(clinic);

    }

    @Override
    public int getItemCount() {
        return clinicArrayList.size();
    }


}

