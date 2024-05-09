package com.example.weatherapp;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
String Base_url= "https://api.openweathermap.org/data/2.5/";
    @GET("forecast?")
    Call<Main_weather_Data> getWeatherData(
            @Query("lat")  double lat,
            @Query("lon")  double lon,
            @Query("appid") String appid
    );

}
