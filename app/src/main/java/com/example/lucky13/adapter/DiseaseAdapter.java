package com.example.lucky13.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lucky13.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lucky13.models.Disease;

import java.util.ArrayList;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.DiseaseHolder> {

    class DiseaseHolder extends RecyclerView.ViewHolder {
        private TextView mDiseaseName, mDiseaseMedicalField;

        DiseaseHolder(View itemView) {
            super(itemView);
            mDiseaseName = itemView.findViewById(R.id.disease_name);
            mDiseaseMedicalField = itemView.findViewById(R.id.disease_description);
        }

        void setDetails(Disease disease) {
            mDiseaseName.setText(disease.getName());
            mDiseaseMedicalField.setText(disease.getMedicalField());
        }

    }

    private Context context;
    private ArrayList<Disease> diseaseArrayList;

    public DiseaseAdapter(Context context, ArrayList<Disease> diseaseArrayList) {
        this.context = context;
        this.diseaseArrayList = diseaseArrayList;
    }

    @NonNull
    @Override
    public DiseaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.disease_card_layout, parent, false);
        return new DiseaseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseHolder holder, int position) {

        Disease disease = diseaseArrayList.get(position);

        holder.setDetails(disease);
    }

    @Override
    public int getItemCount() {
        return diseaseArrayList.size();
    }


}
