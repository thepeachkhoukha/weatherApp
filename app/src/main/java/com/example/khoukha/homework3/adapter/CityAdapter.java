package com.example.khoukha.homework3.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.khoukha.homework3.MainActivity;
import com.example.khoukha.homework3.R;
import com.example.khoukha.homework3.data.City;
import com.example.khoukha.homework3.touch.TodoTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmResults;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder>  implements TodoTouchHelperAdapter {

    Context context;
    Realm realm;
    ArrayList<City> cities;

    public CityAdapter(Context context, Realm realm)
    {
        this.realm = realm;
        this.context = context;
        cities = new ArrayList<City>();
        RealmResults<City> citiesResult =  realm.where(City.class).findAll();
        for (City city: citiesResult) {
            cities.add(city);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

            TextView cityTextView;
            Button detailsButton;
            ConstraintLayout cityConstraintLayout;

        public ViewHolder(View itemView) {

            super(itemView);
            cityTextView = (TextView) itemView.findViewById(R.id.cityTextView);
            detailsButton = (Button) itemView.findViewById(R.id.imageView);
            cityConstraintLayout = (ConstraintLayout) itemView.findViewById(R.id.cityConstraintLayout);
        }
    }

    @NonNull
    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.add_new_city_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CityAdapter.ViewHolder viewHolder, final int position) {
        City city = cities.get(position);

        TextView cityTextView = (TextView) viewHolder.cityTextView;
        cityTextView.setText(city.getCityName());

        ConstraintLayout cityConstraintLayout = (ConstraintLayout) viewHolder.cityConstraintLayout ;



        Button detailsButton = (Button) viewHolder.detailsButton;
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();

                ((MainActivity)context).openWeatherDetails(position, cities.get(position).getCityName());
            }
        });
    }


    @Override
    public int getItemCount() {
        return cities.size();
    }

    @Override
    public void onItemDismiss(int position) {
        realm.beginTransaction();
        cities.get(position).deleteFromRealm();
        realm.commitTransaction();


        cities.remove(position);


        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {


        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(cities, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(cities, i, i - 1);
            }
        }


        notifyItemMoved(fromPosition, toPosition);
    }

}
