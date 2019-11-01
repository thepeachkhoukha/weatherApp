package com.example.khoukha.homework3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class WeatherDetailsPerCity extends AppCompatActivity {

    TextView cityNameTextView;
    TextView degreeTextView;
    TextView windTextView;
    TextView humidityTextView;
    TextView sunRiseTextView;
    TextView sunSetTextView;
    TextView temperatureMinTextView;
    TextView temperatureMaxTextView;
    TextView conditionTextView;
    ImageView imageView;
    ImageView weatherImageView;


    private static String cityName;
    private static String degree;
    private static String wind;
    private static String humidity;
    private static Long sunRise;
    private static Long sunSet;
    private static String temperaturemax;
    private static String temperaturemin;
    private static String condition;
    private static int drawable_index;
    private static String icon;

    public static void setIcon(String icon) {
        WeatherDetailsPerCity.icon = icon;
    }

    public static void setDrawable_index(int drawable_index) {
        WeatherDetailsPerCity.drawable_index = drawable_index;
    }

    public static void setDegree(String degree) {
        WeatherDetailsPerCity.degree = degree;

    }

    public static void setWind(String wind) {
        WeatherDetailsPerCity.wind = wind;
    }

    public static void setHumidity(String humidity) {
        WeatherDetailsPerCity.humidity = humidity;
    }

    public static void setSunRise(Long sunRise) {
        WeatherDetailsPerCity.sunRise = sunRise;
    }

    public static void setSunSet(Long sunSet) {
        WeatherDetailsPerCity.sunSet = sunSet;
    }

    public static void setTemperaturemax(String temperaturemax) {
        WeatherDetailsPerCity.temperaturemax = temperaturemax;
    }

    public static void setTemperaturemin(String temperaturemin) {
        WeatherDetailsPerCity.temperaturemin = temperaturemin;
    }

    public static void setCondition(String condition) {
        WeatherDetailsPerCity.condition = condition;
    }

    public static void setCityName(String nameOfCity)
    {
        cityName = nameOfCity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_details);
        cityNameTextView = (TextView) findViewById(R.id.cityNameTextView);
        conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        temperatureMaxTextView = (TextView) findViewById(R.id.temperatureMaxTextView);
        temperatureMinTextView = (TextView) findViewById(R.id.temperatureMinTextView);
        windTextView = (TextView) findViewById(R.id.windTextView);
        sunSetTextView = (TextView) findViewById(R.id.sunSetTextView);
        sunRiseTextView = (TextView) findViewById(R.id.sunRiseTextView);
        degreeTextView = (TextView) findViewById(R.id.degreeTextView);
        humidityTextView = (TextView) findViewById(R.id.humidityTextView);
        imageView = (ImageView) findViewById(R.id.imageView);
        weatherImageView = (ImageView) findViewById(R.id.weatherImageView);

        setTheInfo(cityName, temperaturemin, temperaturemax, humidity, formatTime(sunRise), formatTime(sunSet), wind, condition, degree, drawable_index, icon);

    }



    private String formatTime(Long unixTime) {
        Long unixTimeToMillis = unixTime * 1000;
        return DateFormat.getTimeFormat(this).format(unixTimeToMillis);

    }

    private void setTheInfo(String name, String minTem, String maxTem,
                            String humid, String sunrise, String sunset, String wind, String con, String degree, int drawable, String icon) {


        //cityNameTextView.setText(cityName);
        cityNameTextView.setText(name);
        temperatureMaxTextView.setText(maxTem);
        temperatureMinTextView.setText(minTem);
        humidityTextView.setText(humid);
        sunRiseTextView.setText(sunrise);
        degreeTextView.setText(degree);
        sunSetTextView.setText(sunset);
        windTextView.setText(wind);
        conditionTextView.setText(con);
        imageView.setImageResource(drawable);
        Glide.with(getApplicationContext()).load("https://openweathermap.org/img/w/"+icon+".png").into(weatherImageView);
    }
}
