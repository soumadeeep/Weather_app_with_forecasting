package com.example.weatherapp;

public class Temp {

    private double temp_min;
    private double temp_max;

    public Temp(double temp_min, double temp_max) {
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }


    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }
}
