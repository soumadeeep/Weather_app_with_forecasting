package com.example.weatherapp;

import java.util.List;

public class ten_days_model {
    private long dt;
    private Temp main;
    private List<Weather> weather;

    public ten_days_model(long dt, Temp temp, List<Weather> weather) {
        this.dt = dt;
        this.main = temp;
        this.weather = weather;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public Temp getTemp() {
        return main;
    }

    public void setTemp(Temp temp) {
        this.main = temp;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}
