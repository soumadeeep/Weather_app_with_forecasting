package com.example.weatherapp;

public class Weather {
    private String description;
    private int id;
    private String icon;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Weather(String description, String icon) {
        this.description = description;
        this.icon = icon;
    }
}
