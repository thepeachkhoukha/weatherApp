package com.example.khoukha.homework3;

import com.example.khoukha.homework3.model.WeatherData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface OpenWeatherMapAPI {
    public static final String Base_URL = "http://api.openweathermap.org/";

    @GET("/data/2.5/weather")
    Call<WeatherData> getWeatherInfo (@Query("q") String name,
                                      @Query("units") String unit,
                                      @Query("APPID") String appid);
}

