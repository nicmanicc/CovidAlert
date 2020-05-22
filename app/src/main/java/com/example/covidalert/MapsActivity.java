package com.example.covidalert;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //Stores latitude and longitude
    private Double mCountryLatitude;
    private Double mCountryLongitude;
    //Statistic cases
    private String mCountry, mConfirmed, mRecovered, mDeaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Get strings stored in intent
        String location = this.getIntent().getStringExtra("Slug");
        mCountry = this.getIntent().getStringExtra("country");
        mConfirmed = this.getIntent().getStringExtra("confirmed");
        mRecovered = this.getIntent().getStringExtra("recovered");
        mDeaths = this.getIntent().getStringExtra("deaths");

        //Create a geocoder object
        Geocoder geocoder = new Geocoder(this);
        //Create a list for the address retrieved from geocoder function
        List<Address> countryCoordinates;

        try {
            //Get address of location entered from intent
            countryCoordinates = geocoder.getFromLocationName(location, 1);
            //Get the latitude of the address
            mCountryLatitude = countryCoordinates.get(0).getLatitude();
            //Get the longitude of the address
            mCountryLongitude = countryCoordinates.get(0).getLongitude();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Set custom info window to display the country name and confirmed cases
        mMap.setInfoWindowAdapter(new InfoWindowAdapter(this, mConfirmed, mRecovered, mDeaths, mCountry));


        // Add a marker in country selected from recycler view
        LatLng countryPos = new LatLng(mCountryLatitude, mCountryLongitude);
        Marker country = mMap.addMarker(new MarkerOptions()
                                    .position(countryPos));
        //Use a camera animation to move the screen towards the market
        CameraPosition cameraPosition = new CameraPosition.Builder().target(countryPos).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate);
        //Set an onclick listener to show/hide the info window
        ImageButton infoButton =  findViewById(R.id.infoButton);
        infoButton.setOnClickListener(new onInfoClick(country));

    }
    /* Overview: OnClickListener that opens and closes the marker info window
    when the button is pressed. */
    private class onInfoClick implements View.OnClickListener {
        //Marker accessed by onClick method
        private Marker mMarker;
        //Set private marker
        public onInfoClick(Marker marker) {
            mMarker = marker;
        }
        //Set infoShowing to false
        Boolean infoShowing = false;
        @Override
        public void onClick(View v) {
            //Show info window when infoShowing is false
            if (!infoShowing) {
                mMarker.showInfoWindow();
                infoShowing = true;
            //Hide info window when infoShowing is true
            } else {
                mMarker.hideInfoWindow();
                infoShowing = false;
            }
        }
    }
}