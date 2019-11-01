package com.example.khoukha.homework3;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.khoukha.homework3.model.WeatherData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherMapModel  implements Callback<WeatherData> {

    private String API_KEY="5b585153dc383c18c88e795daa18d8c0";
    private String nameOfCity;
    WeatherData weatherData;
    public static final String TAG = OpenWeatherMapModel.class.getSimpleName();


    @Override
    public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
        if(response.isSuccessful()) {
            weatherData = response.body();

            nameOfCity = weatherData.getName();

        } else {

            Log.v(TAG, response.message());
        }
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public void getCityData(final String location, final Context context) {
        nameOfCity = location;
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OpenWeatherMapAPI.Base_URL).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        OpenWeatherMapAPI openWeatherAPIInterface = retrofit.create(OpenWeatherMapAPI.class);

        Call<WeatherData> call = openWeatherAPIInterface.getWeatherInfo(nameOfCity,
                "metric",
                API_KEY);
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                WeatherDetailsPerCity wdpc = new WeatherDetailsPerCity();
                Log.e("",String.valueOf( response.body().getMain().getTemp_min()));
                Log.e("",String.valueOf( response.body().getMain().getHumidity()));

            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {

            }
        });

    }

    @Override
    public void onFailure(Call<WeatherData> call, Throwable t) {
        Log.v(TAG, t.getMessage());
    }


}
