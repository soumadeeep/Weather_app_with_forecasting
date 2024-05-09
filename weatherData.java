package com.example.weatherapp;

import org.json.JSONException;
import org.json.JSONObject;

public class weatherData {
    private int w_condition;
    private String w_Temperature,w_description,w_icon,w_city,w_Feels_like,w_temp_max,w_temp_min,wind_speed,w_humidity;



    public static weatherData fromJson(JSONObject jsonObject){

        try {

            weatherData weatherD= new weatherData();
            //city name
            weatherD.w_city=jsonObject.getString("name");
            //weather id
            weatherD.w_condition=jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            //description
            weatherD.w_description=jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
            //weather icon and initialize method for icon
            weatherD.w_icon=updateWeatherIcon(weatherD.w_condition);
            // main temperature
            double tempResult=jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            int roundValue=(int)Math.rint(tempResult);
            weatherD.w_Temperature= Integer.toString(roundValue);
            // feels like
            double feelsLike= jsonObject.getJSONObject("main").getDouble("feels_like")-273.15;
            int roundValueOf_feelsLike=(int) Math.rint(feelsLike);
            weatherD.w_Feels_like= Integer.toString(roundValueOf_feelsLike);
           // minimum temperature
            double mean_temp=jsonObject.getJSONObject("main").getDouble("temp_min")-273.15;
            int roundValueOf_min=(int)Math.rint(mean_temp);
            weatherD.w_temp_min=Integer.toString(roundValueOf_min);

          // maximum temperature
            double max_temp=jsonObject.getJSONObject("main").getDouble("temp_max")-273.15;
            int roundValueOf_max=(int) Math.rint(max_temp);
            weatherD.w_temp_max=Integer.toString(roundValueOf_max);
         // humidity
            int humidity=jsonObject.getJSONObject("main").getInt("humidity");
            weatherD.w_humidity= Integer.toString(humidity);
         // wind
          double speedOfWind=jsonObject.getJSONObject("wind").getDouble("speed");
          int round_of_wind_speed=(int)Math.rint(speedOfWind);
          weatherD.wind_speed=Integer.toString(round_of_wind_speed);





            return weatherD;


        }
         catch (JSONException e) {
            throw new RuntimeException(e);
         }


    }

    private static String updateWeatherIcon(int condition){
        if(condition>=0 && condition<300){
            return "thunderstorm";
        }
        else if(condition>=300 && condition<500){
            return "cloudy_rain";
        }
       else if(condition>=500 && condition<600){
            return "raining";
        }
       else if(condition>=600 && condition<701){
            return "snowflake";
        }
        else if(condition == 800){
            return "sun1";
        }
        else if(condition>=801 && condition<=804){
            return "cloudy";
        }
        return "meteorology";


    }

    public String getW_Temperature() {
        return w_Temperature+"°C";
    }

    public int getW_condition() {
        return w_condition;
    }

    public String getW_description() {
        return w_description;
    }

    public String getW_icon() {
        return w_icon;
    }

    public String getW_city() {
        return w_city;
    }

    public String getW_Feels_like() {
        return w_Feels_like+"°C";
    }

    public String getW_temp_max() {
        return w_temp_max+"°C";
    }

    public String getW_temp_min() {
        return w_temp_min+"°C";
    }

    public String getW_humidity() {
        return w_humidity+"%";
    }

    public String getWind_speed() {
        return wind_speed+"km/h";
    }
}
