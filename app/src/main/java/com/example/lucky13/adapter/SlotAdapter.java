package com.example.lucky13.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lucky13.R;
import com.example.lucky13.activities.common_activities.WelcomePage;
import com.example.lucky13.activities.patient_path.AfterAppointmentActivity;
import com.example.lucky13.models.Disease;
import com.example.lucky13.models.Doctor;
import com.example.lucky13.notification.FcmNotificationsSender;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.SlotHolder> {

    class SlotHolder extends RecyclerView.ViewHolder {
        private TextView mDay, mTime;

        SlotHolder(View itemView) {
            super(itemView);
            mDay = itemView.findViewById(R.id.day);
            mTime = itemView.findViewById(R.id.time);

        }

        @SuppressLint("SetTextI18n")
        @RequiresApi(api = Build.VERSION_CODES.O)
        void setDetails(LocalDate day, String time, Doctor doctor) {
            if (day != null) {
                mDay.setText(day.getDayOfMonth() + ", " + day.getMonth());
            }
            mTime.setText(time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] split = time.split(" ");
                    String[] split2 = split[0].split(":");

                    int hour = Integer.parseInt(split2[0]);
                    int minute  = Integer.parseInt(split2[1]);

                    appointments.putAll(doctor.getAppointments());

                    LocalDateTime localDateTime = LocalDateTime.of(day.getYear(), day.getMonth(), day.getDayOfMonth(), hour, minute);
                    ZoneId zoneId = ZoneId.of("Europe/Oslo");
                    long date = localDateTime.atZone(zoneId).toEpochSecond();

                    appointments.put(Long.toString(date), "00,30");

                    firebaseFirestore.collection("Doctors").document(doctor.getUID())
                            .update("appointments", appointments)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("SLOTS: ", "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("SLOTS: ", "Error writing document", e);
                                }
                            });

                    Intent intent = new Intent(context, AfterAppointmentActivity.class);
                    intent.putExtra("doctor", doctor.getToken());
                    context.startActivity(intent);
                }
            });
        }

    }

    private Context context;

    private String[] times;
    private LocalDate[] dates;

    Doctor doctor;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    HashMap<String, String> appointments = new HashMap<>();

    public SlotAdapter(Context context, String[] times, LocalDate[] dates, Doctor doctor) {
        this.context = context;
        this.times = times;
        this.dates = dates;
        this.doctor = doctor;
    }

    @NonNull
    @Override
    public SlotAdapter.SlotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.available_slot_card_layout, parent, false);
        return new SlotAdapter.SlotHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull SlotAdapter.SlotHolder holder, int position) {

        LocalDate slot = dates[position];
        String time = times[position];

        holder.setDetails(slot, time, doctor);

    }

    @Override
    public int getItemCount() {
        return times.length;
    }
}
