package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class recycle_view_adapter extends RecyclerView.Adapter<recycle_view_adapter.ViewHolder> {

    Context context;
    ArrayList<ten_days_model> modelArrayList;

    Temp temp;

    public recycle_view_adapter(Context context, ArrayList<ten_days_model> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;

    }

    @NonNull
    @Override
    public recycle_view_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view= LayoutInflater.from(context).inflate(R.layout.ten_days_model_class,null,false);
        context= parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recycle_view_adapter.ViewHolder holder, int position) {

        holder.day.setText(date_and_time_converter(modelArrayList.get(position).getDt()));
        holder.description.setText(modelArrayList.get(position).getWeather().get(0).getDescription());
     holder.min_t.setText(String.valueOf((int) Math.rint(modelArrayList.get(position).getTemp().getTemp_min()-273.15)));
     holder.max_t.setText(String.valueOf((int) Math.rint(modelArrayList.get(position).getTemp().getTemp_max()-273.15)));
     holder.imageView.setImageResource(updateWeatherIcon(modelArrayList.get(position).getWeather().get(0).getId()));

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      TextView day,description,max_t,min_t;
      ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           day= itemView.findViewById(R.id.day);
           description=itemView.findViewById(R.id.description_for_ten_days);
           max_t= itemView.findViewById(R.id.ten_days_max);
           min_t=itemView.findViewById(R.id.ten_days_min);
           imageView=itemView.findViewById(R.id.image_for_ten_days);
        }
    }

    private static int updateWeatherIcon(int condition){
        if(condition>=0 && condition<300){
            return R.drawable.thunderstorm;
        }
        else if(condition>=300 && condition<500){
            return R.drawable.cloudy_rain;
        }
        else if(condition>=500 && condition<600){
            return R.drawable.raining;
        }
        else if(condition>=600 && condition<701){
            return R.drawable.snowflake;
        }
        else if(condition == 800){
            return R.drawable.sun1;
        }
        else if(condition>=801 && condition<=804){
            return R.drawable.cloudy;
        }
        return R.drawable.meteorology;


    }

    private static String date_and_time_converter(long timestamp){
        String formattedDateTime=" ";
        Instant instant = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            instant = Instant.ofEpochSecond(timestamp);
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
             formattedDateTime = dateTime.format(formatter);
        }
      return formattedDateTime;
    }
}
