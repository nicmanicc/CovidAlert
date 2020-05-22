package com.example.covidalert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final View mInfoView;
    private String mCountry, mConfirmed, mRecovered, mDeaths;

    //Constructor
    public InfoWindowAdapter(Context context, String confirmed, String recovered, String deaths, String country) {
        //Inflate the info window with the info_window layout
        mInfoView = LayoutInflater.from(context).inflate(R.layout.info_window, null);
        mCountry = country;
        mConfirmed = confirmed;
        mRecovered = recovered;
        mDeaths = deaths;
        render(mInfoView);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return mInfoView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return mInfoView;
    }

    /* Overview: Retrieve text views and set text with the cases retrieved
    in constructor */
    private void render(View view) {
        //Retrieve each text view
        TextView countryTitle = view.findViewById(R.id.countryTitle);
        TextView confirmedInfo = view.findViewById(R.id.confirmedInfo);
        TextView recoveredInfo = view.findViewById(R.id.recoveredInfo);
        TextView deathInfo = view.findViewById(R.id.deathInfo);
        //Set the cases for each text view
        countryTitle.setText(mCountry);
        confirmedInfo.setText(StatisticActivity.formatCases(mConfirmed));
        recoveredInfo.setText(StatisticActivity.formatCases(mRecovered));
        deathInfo.setText(StatisticActivity.formatCases(mDeaths));

    }
}
