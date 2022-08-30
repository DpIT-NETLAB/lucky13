package com.example.lucky13.activities.patient_path;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.lucky13.R;
import com.example.lucky13.directionhelpers.FetchURL;
import com.example.lucky13.directionhelpers.TaskLoadedCallback;
import com.example.lucky13.models.Doctor;
import com.example.lucky13.service.DoctorService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DoctorPresentationActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    private static final String TAG = "DOCTOR MAPS: ";

    TextView mDoctorName, mDoctorField, mDoctorDescription;

    AppCompatButton mBookAppointmentButton, mGetDirectionsButton;

    private GoogleMap mGoogleMap;
    private MarkerOptions loc1, loc2;
    private Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_presentation);

        mDoctorName = findViewById(R.id.doctorPresentationName);
        mDoctorField = findViewById(R.id.doctorPresentationField);
        mDoctorDescription = findViewById(R.id.doctorPresentationDescription);
        mGetDirectionsButton = findViewById(R.id.showDirectionsButton);
        mBookAppointmentButton = findViewById(R.id.makeAppointmentButton);

        Intent incomingIntent = getIntent();
        String id = incomingIntent.getStringExtra("id");
        String name = incomingIntent.getStringExtra("name");
        String field = incomingIntent.getStringExtra("field");
        Location yourLocation = incomingIntent.getParcelableExtra("location");
        Location clinicLocation = incomingIntent.getParcelableExtra("clinicLocation");

        SetText(name, field);

        mGetDirectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchURL(DoctorPresentationActivity.this).execute(getUrl(loc1.getPosition(), loc2.getPosition(), "driving"), "driving");
            }
        });
        loc1 = new MarkerOptions().position(new LatLng(yourLocation.getLatitude(), yourLocation.getLongitude())).title("Position 1");
        loc2 = new MarkerOptions().position(new LatLng(clinicLocation.getLatitude(), clinicLocation.getLongitude())).title("Position 2");

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mBookAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(DoctorPresentationActivity.this, BookAppointmentActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void SetText(String name, String field) {

        mDoctorName.setText(name);
        mDoctorField.setText("Medical Field: " + field.toUpperCase(Locale.ROOT));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        Log.d(TAG, "Added Markers");
        mGoogleMap.addMarker(loc1);
        mGoogleMap.addMarker(loc2);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (polyline != null)
            polyline.remove();
        polyline = mGoogleMap.addPolyline((PolylineOptions) values[0]);
    }
}
