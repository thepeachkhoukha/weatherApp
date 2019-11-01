package com.example.khoukha.homework3.data;

import io.realm.RealmObject;

public class City  extends RealmObject{
    private String cityName;


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
