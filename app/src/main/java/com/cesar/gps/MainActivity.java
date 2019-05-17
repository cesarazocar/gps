package com.cesar.gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvUbication;
    Button btnGetGps;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvUbication = (TextView) findViewById(R.id.tvUbication);

        btnGetGps = (Button) findViewById(R.id.btnGetGps);

        btnGetGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });










        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, new Listener());
        // Have another for GPS provider just in case.
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new Listener());
        // Try to request the location immediately
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null){
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (location != null){
            handleLatLng(location.getLatitude(), location.getLongitude());
        }
        Toast.makeText(getApplicationContext(),
                "Trying to obtain GPS coordinates. Make sure you have location services on.",
                Toast.LENGTH_SHORT).show();



    }
    /**
     * Handle lat lng.
     */
    private void handleLatLng(double latitude, double longitude){
        tvUbication.setText("Latitud: "+String.valueOf(latitude)+" Longitud: "+String.valueOf(longitude));
        Log.v("GPS", "(" + latitude + "," + longitude + ")");
    }

    /**
     * Listener for changing gps coords.
     */
    private class Listener implements LocationListener {

        public void onLocationChanged(Location location) {

            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            handleLatLng(latitude, longitude);
        }

        public void onProviderDisabled(String provider){}
        public void onProviderEnabled(String provider){}
        public void onStatusChanged(String provider, int status, Bundle extras){}
    }
}
