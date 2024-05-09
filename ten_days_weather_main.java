package com.example.weatherapp;

import static java.security.AccessController.getContext;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ten_days_weather_main extends AppCompatActivity {
    final String api = "af72503cbf72c4c387a1da8728ce7fff";
    FusedLocationProviderClient fusedLocationProviderClient;
    ArrayList<ten_days_model> modelArrayList;

  recycle_view_adapter adapter;

 Context context;
 RecyclerView recyclerView;


   private Toolbar ten_toolbar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ten_days_view_class);
        ten_toolbar= findViewById(R.id.ten_days_toolbar);
        setSupportActionBar(ten_toolbar);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        initRecycleView();
        //getWeatherForCurrentLocation();




    }
    private void initRecycleView(){

        modelArrayList=new ArrayList<>();

        recyclerView= findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter= new recycle_view_adapter(this,modelArrayList);
        recyclerView.setAdapter(adapter);
        getWeatherForCurrentLocation();
    }
    private void getWeatherForCurrentLocation() {

        if(ContextCompat.checkSelfPermission(ten_days_weather_main.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null){
                        Geocoder geocoder=new Geocoder(ten_days_weather_main.this, Locale.getDefault());
                        List<Address> addressList= null;
                        try {
                            addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            assert addressList != null;
                            Address address = addressList.get(0);
                            double latitude = address.getLatitude();
                            double longitude = address.getLongitude();

                            fetch_weather_data(latitude,longitude);

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }


                }
            });
        }

    }
 void fetch_weather_data(double latitude,double longitude){

     ApiUtility.getApiInterface().getWeatherData(latitude, longitude,  api)
             .enqueue(new Callback<Main_weather_Data>() {
                 @Override
                 public void onResponse(Call<Main_weather_Data> call, Response<Main_weather_Data> response) {
                     if (response.isSuccessful()) {
                         Main_weather_Data mainWeatherData = response.body();
                         if (mainWeatherData != null && mainWeatherData.getList() != null) {
                             // Clear previous data
                             modelArrayList.clear();
                             // Add new weather data to the list
                             modelArrayList.addAll(mainWeatherData.getList());
                             // Notify the adapter that the data set has changed
                             adapter.notifyDataSetChanged();
                         }
                     } else {
                         // Handle unsuccessful response
                         Toast.makeText(ten_days_weather_main.this, "Failed to fetch weather data", Toast.LENGTH_SHORT).show();
                     }
                 }

                 @Override
                 public void onFailure(Call<Main_weather_Data> call, Throwable t) {
                     // Handle failure
                     Toast.makeText(ten_days_weather_main.this, "Failed to fetch weather data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                 }
             });

 }

 }
