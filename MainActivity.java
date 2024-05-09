package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import android.Manifest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    final String api = "af72503cbf72c4c387a1da8728ce7fff";
    final String base_url = "https://api.openweathermap.org/data/2.5/weather";
    final long Min_time = 1000 * 60 * 15;
    final float Min_distance = 1000;
    final int Request_code = 101;
    // We use FusedLocationProviderClient to retrieve the device's last known location
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView wmain_temp, wfeels_like, wmax_temp, wmin_temp, whumidity, wwind, wdescription,location;
    ImageView cloud;
    LocationManager wlocationManager;
    LocationListener wlocationListener;


     private ProgressDialog  progressDialog=null;


    private Toolbar wtoolbar;
    Button button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);
        wtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(wtoolbar);


        wmain_temp = findViewById(R.id.maintemp);
        wfeels_like = findViewById(R.id.feelslike);
        wmax_temp = findViewById(R.id.max_temp);
        wmin_temp = findViewById(R.id.min_temp);
        whumidity = findViewById(R.id.humidity);
        wwind = findViewById(R.id.wind);
        wdescription = findViewById(R.id.description);
        cloud = findViewById(R.id.cloudimage);
        location=findViewById(R.id.location);
        button=findViewById(R.id.f_button);

        Toast.makeText(MainActivity.this,"Welcome to the delightful INVYU Weather Report experience!",Toast.LENGTH_SHORT).show();
      fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);


      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent= new Intent(MainActivity.this,ten_days_weather_main.class);
              startActivity(intent);
          }
      });

    }



    protected void onResume() {
        super.onResume();
        getWeatherForCurrentLocation();
        //** progressbar//
        showProgressbar();
    }


    private void getWeatherForCurrentLocation() {

        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null){
                        Geocoder geocoder=new Geocoder(MainActivity.this, Locale.getDefault());
                        List<Address> addressList= null;
                        try {
                            addressList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            assert addressList != null;
                            Address address = addressList.get(0);
                            double latitude = address.getLatitude();
                            double longitude = address.getLongitude();
                            location_fetch(latitude,longitude);

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }


                }
            });
        }
        else {
            ask_Permission();
        }
    }
    public void location_fetch(double Latitude,double Longitude){
        RequestParams params=new RequestParams();
        params.put("lat",Latitude);
        params.put("lon",Longitude);
        params.put("appid",api);
        letsDoSomeNetworking(params);

    }

    public void ask_Permission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_code);
    }

    @Override
         public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
           super.onRequestPermissionsResult(requestCode, permissions, grantResults);

           if (requestCode == Request_code) {
               if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   Toast.makeText(this," Thanks for Giving the Access",Toast.LENGTH_SHORT).show();
                   getWeatherForCurrentLocation();

               }
               else {
                   Toast.makeText(this,"please allow the location",Toast.LENGTH_SHORT).show();
               }
           }

       }

       private void letsDoSomeNetworking(RequestParams  params){
           AsyncHttpClient client =new AsyncHttpClient();
           client.get(base_url,params,new JsonHttpResponseHandler(){

               @Override
               public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                   //super.onSuccess(statusCode, headers, response);
                   Toast.makeText(MainActivity.this,"Your Weather Report is Ready",Toast.LENGTH_SHORT).show();
                   //** progressbar//
                   dismissProgressBar();
                   weatherData weatherD= weatherData.fromJson(response);
                   updateUi(weatherD);


               }

               @Override
               public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                   //super.onFailure(statusCode, headers, throwable, errorResponse);
                   Toast.makeText(MainActivity.this,"Please wait Some Issue is Happen",Toast.LENGTH_SHORT).show();
               }
           });

       }
         private void updateUi(weatherData weather){
            wmain_temp.setText(weather.getW_Temperature());
            wdescription.setText(weather.getW_description());
            location.setText(weather.getW_city());
            wmin_temp.setText(weather.getW_temp_min());
            wmax_temp.setText(weather.getW_temp_max());
            wfeels_like.setText(weather.getW_Feels_like());
            whumidity.setText(weather.getW_humidity());
            wwind.setText(weather.getWind_speed());

            @SuppressLint("DiscouragedApi")
            int resourceId=getResources().getIdentifier(weather.getW_icon(),"drawable",getPackageName());
            cloud.setImageResource(resourceId);
         }
         @Override
         protected void onPause() {
          super.onPause();
          if(wlocationManager!=null) {
             wlocationManager.removeUpdates(wlocationListener);

          }
       }

       private void showProgressbar(){
           progressDialog = new ProgressDialog(this);
           progressDialog.setMessage("Loading...");
           progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
           progressDialog.setCancelable(false);
           progressDialog.show();

       }

     private void dismissProgressBar() {
         if (progressDialog != null) {
             progressDialog.dismiss();
         }
     }



 }
