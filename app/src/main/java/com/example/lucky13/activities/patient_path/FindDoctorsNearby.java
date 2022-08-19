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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_show);

        Intent incomingIntent = getIntent();
        number = incomingIntent.getStringExtra("number");
        if (Objects.equals(number, "1")) {
            field = incomingIntent.getStringExtra("field");
            GetLocation();
            InitializeCard();

        }
        else {
            GetLocation();
            InitializeCard();
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
                CreateClinicsList(loc.getLatitude(), loc.getLongitude());
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

    public void CreateClinicsList(double latitude, double longitude) {
        clinicArrayList = new ArrayList<>();
        clinicService.getAllClinics();
        clinicService.clinicList.observe(this, clinicList -> {
            clinicArrayList.addAll(clinicList);
            HashMap<Clinic, Double> clinicMap = OrderClinics(clinicArrayList, latitude, longitude);
            CreateDataForCardsLocation(latitude, longitude, clinicMap);
        });
    }

    private HashMap<Clinic, Double> OrderClinics(ArrayList<Clinic> clinicArrayList, double latitude, double longitude) {
        HashMap<Clinic, Double> clinicMap = new HashMap<>();
        for (Clinic clinic : clinicArrayList) {
            double distance = GetDistance(clinic.getLocation().getLatitude(), clinic.getLocation().getLongitude(),
                    latitude, longitude);
            Log.d(TAG, "DISTANCE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + distance + " " + clinic.getLocation().getLongitude());
            clinicMap.put(clinic, distance);
        }
        List<Map.Entry<Clinic, Double>> list = new LinkedList<Map.Entry<Clinic, Double>>(clinicMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Clinic, Double>>() {
            @Override
            public int compare(Map.Entry<Clinic, Double> loc1, Map.Entry<Clinic, Double> loc2) {
                return (loc1.getValue()).compareTo(loc2.getValue());
            }
        });
        HashMap<Clinic, Double> temp = new LinkedHashMap<Clinic, Double>();
        for (Map.Entry<Clinic, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    private void CreateDataForCardsLocation(double latitude, double longitude, HashMap<Clinic, Double> clinicMap) {
        doctorService.getAllDoctors();
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        doctorService.doctorList.observe(this, doctorList -> {
            doctorArrayList.addAll(doctorList);
            ModifyList(doctorArrayList, clinicMap, latitude, longitude);
            adapter.notifyDataSetChanged();
        });
    }

    private void ModifyList(ArrayList<Doctor> doctorList, HashMap<Clinic, Double> clinicMap, double latitude, double longitude) {
        //HashMap<Doctor, Double> DoctorsDistance = new HashMap<>();
        ArrayList<Doctor> doctorsNearby = new ArrayList<>();
        for (Clinic clinic : clinicMap.keySet()) {
            Log.d(TAG, "11111111111111111111111111111" + clinic);
            for (Doctor doctor : doctorList) {
                if (clinic.getDoctorUIDs().contains(doctor.getUID())) {
                    doctorsNearby.add(doctor);
                }
            }
        }
        doctorList.clear();
        doctorList.addAll(doctorsNearby);
    }

    private void CreateDataForCards(double longitude, double latitude, String field) {
        doctorService.getAllDoctors();
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        doctorService.doctorList.observe(this, doctorList -> {
            doctorArrayList.addAll(doctorList);
            SortByField(doctorArrayList, field);
            adapter.notifyDataSetChanged();
        });
    }

    private void SortByField(ArrayList<Doctor> list, String field) {
        ArrayList<Doctor> doctorsInField = new ArrayList<>();

        for (Doctor doctor : list) {
            if (field.equals(doctor.getMedicalField())) {
                doctorsInField.add(doctor);
            }
        }
        list.clear();
        list.addAll(doctorsInField);
    }

    private double GetDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        final int R = 6371;

        double latitudeDistance = Math.toRadians(latitude1 - latitude2);
        double longitudeDistance = Math.toRadians(longitude1 - longitude2);

        double a = Math.sin(latitudeDistance / 2) * Math.sin(latitudeDistance / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(longitudeDistance / 2) * Math.sin(longitudeDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c * 1000;
    }
}

