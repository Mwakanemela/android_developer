package com.example.fusedlocationprovider.javacode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.Manifest;
import com.example.fusedlocationprovider.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Granularity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String TAG = "Location";

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private Location lastLocation;

    Context context;

    TextView tv_lat, tv_lon, tv_address, tv_city;
    Double d_lat, d_long;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv_lat = findViewById(R.id.latitude);
        tv_lon = findViewById(R.id.longitude);
        tv_address = findViewById(R.id.address);

        Log.d(TAG, "In the on create");
        context = getApplicationContext();

        checkLocationPermission();
        init();
    }

    public void checkLocationPermission() {

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 1);
            }else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "Permission granted");

                        init();

                    }
                }
                return;
        }
    }

    private class GeocodingTask extends AsyncTask<Location, Void, String> {
        @Override
        protected String doInBackground(Location... locations) {
            Location location = locations[0];
            try {
                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                if (addresses != null && addresses.size() > 0) {
                    return addresses.get(0).getAddressLine(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String address) {
            if (address != null) {
                tv_address.setText(address);
            } else {
                tv_address.setText("Address not available");
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
           settingsClient.checkLocationSettings(locationSettingsRequest)
                   .addOnSuccessListener(locationSettingsResponse -> {
                       Log.d(TAG, "Location settings are ok");
                       fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                   })
                   .addOnFailureListener(e->{
                       int statusCode = ((ApiException) e).getStatusCode();
                       Log.d(TAG, "Error : "+statusCode);
                       Log.d(TAG, "Error in location : "+e.getMessage());
                   });
    }

    public void stopLocationUpdates() {
        fusedLocationProviderClient
                .removeLocationUpdates(locationCallback)
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "stop location updates");
                });
    }

    String fetched_address = "";

    private void receiveLocation(LocationResult locationResult) {
        lastLocation = locationResult.getLastLocation();

        Log.d(TAG, "Latitude: "+lastLocation.getLatitude());
        Log.d(TAG, "Longitude: "+lastLocation.getLongitude());
        Log.d(TAG, "Altitude: "+lastLocation.getAltitude());

        String s_lat = String.format(Locale.ROOT, "%.6f", lastLocation.getLatitude());
        String s_lon = String.format(Locale.ROOT, "%.6f", lastLocation.getLongitude());

        d_lat = lastLocation.getLatitude();
        d_long = lastLocation.getLongitude();

        tv_lat.setText(""+s_lat);
        tv_lon.setText(""+s_lon);
        new GeocodingTask().execute(lastLocation);


//        try {
//
//            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(d_lat, d_long, 1);
//
//            fetched_address = addresses.get(0).getAddressLine(0);
//
//
//            Log.d(TAG, fetched_address);
//            tv_address.setText(fetched_address);
//
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
    }

    public void init() {
        Log.d(TAG, "Init function");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                receiveLocation(locationResult);
            }
        };

//        locationRequest = LocationRequest.create()
//                .setInterval(5000)
//                .setFastestInterval(500)
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                .setMaxWaitTime(100);

        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
                .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                .setWaitForAccurateLocation(true)
                .setMinUpdateIntervalMillis(500)
                .setMinUpdateDistanceMeters(1)
                .build();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();
        startLocationUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }
}