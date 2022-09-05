package com.example.lucky13.activities.patient_path;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lucky13.R;
import com.example.lucky13.adapter.DoctorAdapter;
import com.example.lucky13.models.Clinic;
import com.example.lucky13.models.Doctor;
import com.example.lucky13.service.ClinicService;
import com.example.lucky13.service.DoctorService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FindDoctorsNearby extends AppCompatActivity implements LocationListener {
    private static final String TAG = "DOCTORS LIST";

    DoctorService doctorService = new DoctorService();
    ClinicService clinicService = new ClinicService();

    private RecyclerView recyclerView;
    private DoctorAdapter adapter;

    private ArrayList<Doctor> doctorArrayList;
    private ArrayList<Clinic> clinicArrayList;

    String[] permissions_all = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int PERMISSION_CODE = 101;

    LocationManager locationManager;
    boolean isGpsProvider;
    boolean isNetworkProvider;

    Location location;

    String number;
    String field;

    Clinic clinic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_show);

        Intent incomingIntent = getIntent();
        number = incomingIntent.getStringExtra("number");
        Log.d(TAG, number);
        if (Objects.equals(number, "1")) {
            field = incomingIntent.getStringExtra("field");
            Log.d(TAG, field);
            GetLocation();
            InitializeCard();

        }
        else {
            String clinicUID = incomingIntent.getStringExtra("clinic");
            clinicService.getAllClinics();
            clinicService.clinicList.observe(this, clinicList -> {
                for (Clinic clinic1 : clinicList) {
                    if (Objects.equals(clinic1.getUID(), clinicUID)) {
                        clinic = clinic1;
                        Log.d(TAG, clinic.getName());
                        GetLocation();
                        InitializeCard();
                    }
                }
            });
        }

    }

    //GET USER LOCATION

    private void GetLocation() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (CheckPermission()) {
                GetCurrentLocation();
            } else {
                RequestPermission();
            }
        } else {
            GetCurrentLocation();
        }
    }

    private void GetCurrentLocation() {
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        isGpsProvider = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkProvider = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGpsProvider && !isNetworkProvider) {
            showAlert();
            GetLastLocation();
        } else {
            GetFinalLocation();
        }
    }

    private void GetLastLocation() {
        if(locationManager!=null) {
            try {
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria,false);
                Location location=locationManager.getLastKnownLocation(provider);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private void GetFinalLocation() {
        try{
            if(isGpsProvider){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60,10, (LocationListener) FindDoctorsNearby.this);
                if(locationManager!=null){
                    location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(location!=null){
                        updateUi(location);
                    }
                }
            }
            else if(isNetworkProvider){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 60,10, (LocationListener) FindDoctorsNearby.this);
                if(locationManager!=null){
                    location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if(location!=null){
                        updateUi(location);
                    }
                }
            }
            else{
                Toast.makeText(this, "Can't Get Location", Toast.LENGTH_SHORT).show();
            }

        }catch (SecurityException e){
            Toast.makeText(this, "Can't Get Location" + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUi(Location loc) {
        if(loc.getLatitude()==0 && loc.getLongitude()==0){
            GetCurrentLocation();
        }
        else{
            Log.d(TAG, "Location : "+loc.getLatitude()+" , "+loc.getLongitude());
            if (!Objects.equals(number, "1")) {
                CreateDataForCardsClinic();
            }
            else CreateDataForCards(loc.getLatitude(), loc.getLongitude(), field);
        }

    }

    private void showAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(FindDoctorsNearby.this);

        alert.setMessage("Enable Gps Services");
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();

    }

    private boolean CheckPermission() {
        for (int i = 0; i < permissions_all.length; i++) {
            int result = ContextCompat.checkSelfPermission(FindDoctorsNearby.this, permissions_all[i]);
            if(result== PackageManager.PERMISSION_GRANTED){
                continue;
            }
            else {
                return false;
            }
        }
        return true;
    }

    private void RequestPermission() {
        ActivityCompat.requestPermissions(FindDoctorsNearby.this, permissions_all, PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                GetCurrentLocation();
            } else {
                Toast.makeText(this, "Permission Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
    }


    //MAKE CARDS

    private void InitializeCard() {
        recyclerView = findViewById(R.id.doctors_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        doctorArrayList = new ArrayList<>();

        adapter = new DoctorAdapter(this, doctorArrayList, location);
        recyclerView.setAdapter(adapter);
    }

    private void CreateDataForCardsClinic() {
        doctorService.getAllDoctors();
        doctorService.doctorList.observe(this, doctorList -> {
            doctorArrayList.addAll(doctorList);
            Log.d(TAG, clinic.getName());
            ModifyList(doctorArrayList);
            adapter.notifyDataSetChanged();
        });
    }

    private void ModifyList(ArrayList<Doctor> doctorList) {
        //HashMap<Doctor, Double> DoctorsDistance = new HashMap<>();
        ArrayList<Doctor> doctorsClinic = new ArrayList<>();
        Log.d(TAG, clinic.getName());
        for (Doctor doctor : doctorList) {
            if (clinic.getDoctorUIDs().contains(doctor.getUID())) {
                doctorsClinic.add(doctor);
            }
        }
        doctorList.clear();
        doctorList.addAll(doctorsClinic);
    }

    private void CreateDataForCards(double latitude, double longitude, String field) {
        doctorService.getAllDoctors();
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        doctorService.doctorList.observe(this, doctorList -> {
            doctorArrayList.addAll(doctorList);
            SortByField(doctorArrayList, field);
            Log.d(TAG, field);
            adapter.notifyDataSetChanged();
        });
    }

    private void SortByField(ArrayList<Doctor> list, String field) {
        ArrayList<Doctor> doctorsInField = new ArrayList<>();
        Log.d(TAG, field);
        for (Doctor doctor : list) {
            if (field.equals(doctor.getMedicalField())) {
                doctorsInField.add(doctor);
            }
        }
        list.clear();
        list.addAll(doctorsInField);
    }

}

