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
import com.example.lucky13.adapter.ClinicAdapter;
import com.example.lucky13.adapter.DoctorAdapter;
import com.example.lucky13.models.Clinic;
import com.example.lucky13.service.ClinicService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShowClinics extends AppCompatActivity implements LocationListener {

    private static final String TAG = "CLINICS LIST";

    ClinicService clinicService = new ClinicService();

    private RecyclerView recyclerView;
    private ClinicAdapter adapter;

    private ArrayList<Clinic> clinicArrayList;

    String[] permissions_all = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int PERMISSION_CODE = 101;

    LocationManager locationManager;
    boolean isGpsProvider;
    boolean isNetworkProvider;

    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_show);


        GetLocation();
        InitializeCard();

//        Intent intent = new Intent(ShowClinics.this, FindDoctorsNearby.class);
//        intent.putExtra("number", "2");
//
//        startActivity(intent);

    }

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
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60,10, (LocationListener) ShowClinics.this);
                if(locationManager!=null){
                    location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(location!=null){
                        updateUi(location);
                    }
                }
            }
            else if(isNetworkProvider){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 60,10, (LocationListener) ShowClinics.this);
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
            CreateClinicsList(loc.getLatitude(), loc.getLongitude());
        }

    }

    private void showAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(ShowClinics.this);

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
            int result = ContextCompat.checkSelfPermission(ShowClinics.this, permissions_all[i]);
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
        ActivityCompat.requestPermissions(ShowClinics.this, permissions_all, PERMISSION_CODE);
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


    private void InitializeCard() {
        recyclerView = findViewById(R.id.doctors_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        clinicArrayList = new ArrayList<>();

        adapter = new ClinicAdapter(this, clinicArrayList);
        recyclerView.setAdapter(adapter);
    }

    public void CreateClinicsList(double latitude, double longitude) {
        clinicArrayList = new ArrayList<>();
        clinicService.getAllClinics();
        clinicService.clinicList.observe(this, clinicList -> {
            clinicArrayList.addAll(clinicList);
            ArrayList<Clinic> clinics = OrderClinics(clinicArrayList, latitude, longitude);
            clinicArrayList.clear();
            clinicArrayList.addAll(clinics);
            Log.d(TAG, "$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + clinicArrayList.get(1));
            adapter.notifyDataSetChanged();
        });
    }

    private ArrayList<Clinic> OrderClinics(ArrayList<Clinic> clinicArrayList, double latitude, double longitude) {
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
        ArrayList<Clinic> temp = new ArrayList<>();
        for (Map.Entry<Clinic, Double> aa : list) {
            temp.add(aa.getKey());
        }
        return temp;
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
