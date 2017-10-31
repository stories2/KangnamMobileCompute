package com.compute.mobile.ex6;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity{

    TextView txtGps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtGps = (TextView) findViewById(R.id.txtGps);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        final Location lastMyLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(lastMyLocation != null) {
            Log.d("Test", "lat: " + lastMyLocation.getLatitude() + " long: " + lastMyLocation.getLongitude());
        }
        else {
            Log.d("Test", "lat long is null");
        }
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Test", "gps lat: " + location.getLatitude() + " long: " + location.getLongitude());
                txtGps.setText("gps " + location.getLatitude() + " / " + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        Log.d("Test", "request location update");
        LocationListener locationListener2 = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Test", "net lat: " + location.getLatitude() + " long: " + location.getLongitude());
                txtGps.setText("net " + location.getLatitude() + " / " + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, locationListener2);
    }
}
