package com.example.khoukha.homework3;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.khoukha.homework3.adapter.CityAdapter;
import com.example.khoukha.homework3.data.City;
import com.example.khoukha.homework3.model.WeatherData;
import com.example.khoukha.homework3.touch.TodoItemTouchHelperCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements OnMessageFragmentAnswer {

    public static final String KEY_MSG = "KEY_MSG";
    public static final String KEY_LOCATION_ID = "KEY_LOCATION_ID";
    public static final String API_KEY="5b585153dc383c18c88e795daa18d8c0";
    public static final String TAG = MainActivity.class.getSimpleName();
    Realm realm;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private ConstraintLayout layoutContent;
    private int drawableIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MyApplication)getApplication()).openRealm();

        layoutContent = (ConstraintLayout) findViewById(R.id.contentLayout);
        CityAdapter cityAdapter = new CityAdapter(this,((MyApplication)getApplication()).getRealmLoc() );
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(cityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.Callback callback = new TodoItemTouchHelperCallback(cityAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        FloatingActionButton addNewCity = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        addNewCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        setUpDrawer();
    }

    public void showDialog()
    {
        MessageFragment messageFragment = new MessageFragment();
        messageFragment.setCancelable(false);

        Bundle bundle = new Bundle();
        bundle.putString(KEY_MSG,"Please add a new City");
        messageFragment.setArguments(bundle);

        messageFragment.show(getSupportFragmentManager(),
                "MessageFragment");
    }
    public void setUpDrawer()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.action_add:
                                showDialog();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                            case R.id.action_about:
                                Snackbar.make(layoutContent,

                                        getString(R.string.about),
                                        Snackbar.LENGTH_LONG
                                ).setAction(R.string.actionHide, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //...
                                    }
                                }).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                        }

                        return false;
                    }

                });
    }

    @Override
    public void onPositiveSelected(TextView cityName) {

        boolean flag = false;

        for (String city: MessageFragment.cities) {
            if((city.toUpperCase()).equals(cityName.getText().toString().toUpperCase()))
            {
                Log.e("", city+ " "+ cityName.getText().toString());
                flag = true;

            }

        }

        if (flag==true)
        {
            Toast.makeText(this, "City was added", Toast.LENGTH_SHORT).show();

            writeToRealm(cityName.getText().toString());
        }
        else
        {
            Toast.makeText(this, "City was NOT added! Check spelling", Toast.LENGTH_SHORT).show();
        }

        this.recreate();
    }

    public void writeToRealm(final String cityName)
    {
        realm = getRealm();
        realm.executeTransactionAsync(new Realm.Transaction() {
                                          @Override
                                          public void execute(Realm dbRealm) {
                                              City city = dbRealm.createObject(City.class);
                                              city.setCityName(cityName);
                                          }
                                      },
                new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.v("database","database");
                    }
                },
                new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Log.e("error","here");
                    }
                }


        );

    }

    @Override
    public void onNegativeSelected() {
        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((MyApplication)getApplication()).closeRealm();
    }



    public void openWeatherDetails(int position, final String cityName)
    {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OpenWeatherMapAPI.Base_URL).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        OpenWeatherMapAPI openWeatherAPIInterface = retrofit.create(OpenWeatherMapAPI.class);

        Call<WeatherData> call = openWeatherAPIInterface.getWeatherInfo(cityName,
                "metric",
                API_KEY);
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                    int i =0;
                    int drawable_index=0;
                    for (String city : MessageFragment.cities)
                    {
                        if ((city.toUpperCase()).equals(cityName.toUpperCase())){
                            drawable_index = i;
                        }
                        i++;
                    }


                    WeatherDetailsPerCity.setDrawable_index( MessageFragment.icons[drawable_index]);
                    WeatherDetailsPerCity.setCityName(cityName);
                    WeatherDetailsPerCity.setDegree( String.valueOf( response.body().getMain().getTemp()));
                    WeatherDetailsPerCity.setCondition(response.body().getWeather().get(0).getDescription());
                    WeatherDetailsPerCity.setTemperaturemax(String.valueOf( response.body().getMain().getTemp_max()));
                    WeatherDetailsPerCity.setTemperaturemin(String.valueOf( response.body().getMain().getTemp_min()));
                    WeatherDetailsPerCity.setSunRise(response.body().getSys().getSunrise());
                    WeatherDetailsPerCity.setSunSet(response.body().getSys().getSunset());
                    WeatherDetailsPerCity.setWind(String.valueOf( response.body().getMain().getHumidity()));
                    WeatherDetailsPerCity.setHumidity(String.valueOf( response.body().getWind().getDeg()));
                    WeatherDetailsPerCity.setIcon(response.body().getWeather().get(0).getIcon());

                   // Glide.with(getApplicationContext()).load("https://openweathermap.org/img/w/"+response.body().getWeather().get(0).getIcon()+"png");

                    Intent intent = new Intent(getApplicationContext(), WeatherDetailsPerCity.class);
                    startActivity(intent);


            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.e("","Something went wrong API");
                Log.e(TAG, t.getMessage());
            }
        });


    }

    public Realm getRealm() {
        return ((MyApplication) getApplication()).getRealmLoc();
    }


}
