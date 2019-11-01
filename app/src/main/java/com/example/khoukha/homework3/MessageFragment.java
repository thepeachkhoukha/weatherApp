package com.example.khoukha.homework3;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;


public class MessageFragment extends DialogFragment {
    private OnMessageFragmentAnswer onMessageFragmentAnswer = null;

    public static final String[] cities = new String[]{"Budapest", "Vienna", "New York", "Bratislava", "Paris","Amsterdam", "Tallinn", "Riga", "Podgorica", "London", "Berlin", "Sarajevo",
            "Oslo", "Dublin", "Lisbon", "Belgrade", "Copenhagen", "Warsaw", "Krakow", "Bern", "Reykjavik", "Ljubljana", "Rome", "Milan", "Venice", "Kiev",
            "Madrid", "Barcelona", "Tirana", "Stockholm", "Prague","Vilnius", "Moscow", "Skopje", "Minsk","Valleta", "Helsinki", "Athens", "Zagreb",
            "Sofia", "Pristina", "Bucharest","Nicosia","Vaduz","Baku","Tbilisi","San Marino", "Ankara", "Atlanta", "Monaco", "Tunis", "Edinburgh", "San francisco", "Cairo",
            "Pretoria", "Johannesburg", "Jakarta", "Nairobi", "Hawaii", "Dallas"};

    public static int[] icons = new int[] {R.drawable.budapest, R.drawable.vienna, R.drawable.new_york, R.drawable.bratislava, R.drawable.paris, R.drawable.amsterdam,
    R.drawable.seasons, R.drawable.riga,R.drawable.podgorica, R.drawable.london, R.drawable.berlin, R.drawable.slovenia, R.drawable.oslo, R.drawable.dublin, R.drawable.lisbon, R.drawable.belgrade,
    R.drawable.copenhagen, R.drawable.warsaw, R.drawable.krakow, R.drawable.bern, R.drawable.reykjavik, R.drawable.ljubljana, R.drawable.rome, R.drawable.milan, R.drawable.venice, R.drawable.kiev,
    R.drawable.madrid, R.drawable.barcelona, R.drawable.seasons,R.drawable.stockholm, R.drawable.prague, R.drawable.seasons, R.drawable.moscow,
    R.drawable.skopje, R.drawable.seasons, R.drawable.seasons, R.drawable.helsinki, R.drawable.athena, R.drawable.zagreb, R.drawable.sofia,
    R.drawable.seasons, R.drawable.seasons, R.drawable.seasons, R.drawable.seasons, R.drawable.baku, R.drawable.tbilisi, R.drawable.seasons,
    R.drawable.anakara, R.drawable.atlanta, R.drawable.monaco, R.drawable.tunisia, R.drawable.edinbrugh, R.drawable.san_francisco, R.drawable.egypt,
    R.drawable.pretoria, R.drawable.johannesburg, R.drawable.jakarta, R.drawable.nairobi, R.drawable.hawaii, R.drawable.dallas};

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnMessageFragmentAnswer) {
            onMessageFragmentAnswer = (OnMessageFragmentAnswer) context;
        } else {
            throw new RuntimeException(
                    "This Activity is not implementing the " +
                            "OnMessageFragmentAnswer interface");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(MainActivity.KEY_MSG);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogLayout = inflater.inflate(R.layout.add_new_city_fragment,null);
        final AutoCompleteTextView etName = (AutoCompleteTextView) dialogLayout.findViewById(R.id.etName);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_dropdown_item_1line, cities);

        alertDialogBuilder.setView(dialogLayout);
        etName.setAdapter(cityAdapter);


        alertDialogBuilder.setTitle("Add new City");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                onMessageFragmentAnswer.onPositiveSelected(etName);
            }
        });
        alertDialogBuilder.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                onMessageFragmentAnswer.onNegativeSelected();
            }
        });


        return alertDialogBuilder.create();
    }
}
