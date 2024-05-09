package com.example.weatherapp;

import java.util.ArrayList;
import java.util.List;

public class Main_weather_Data {

    private int cnt;
    private List<ten_days_model> list;

    public List<ten_days_model> getList() {
        return list;
    }

    public void setList(List<ten_days_model> list) {
        this.list = list;
    }

    public Main_weather_Data(int cnt, List<ten_days_model> list) {
        this.cnt = cnt;
        this.list = list;
    }
}
