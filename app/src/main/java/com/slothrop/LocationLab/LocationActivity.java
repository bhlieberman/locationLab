package com.slothrop.LocationLab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;

import java.io.IOException;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {
    private static final String TAG = "location_activity";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Geocoder geocoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());
        getLocations();
//        getLocationUpdates();
    }

    private void getLocations() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location == null) {
                Log.w(TAG, "Location was null");
            }
            String lastLatitude = Double.toString(location.getLatitude());
            String lastLongitude = Double.toString(location.getLongitude());
            Log.i(TAG, "Our last latitude " + lastLatitude);
            Log.i(TAG, "Our last Longitude " + lastLongitude);
        });
        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }

            @Override
            public boolean isCancellationRequested() {
                return false;
            }
        }).addOnSuccessListener(location -> {
            if (location == null) {
                Log.e(TAG, "Location callback was null!");
            }
            Log.i(TAG, "Our latitude: " + location.getLatitude());
            Log.i(TAG, "Our longitude: " + location.getLongitude());
        });

    }

    private void getLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(5 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                try {
                    String address = geocoder.getFromLocation(
                                    locationResult.getLastLocation().getLatitude(),
                                    locationResult.getLastLocation().getLongitude(),
                                    1) // 1 -> give the best result
                            .get(0) // the best address given
                            .getAddressLine(0); // first line of the address
                    Log.i(TAG, "Repeating current location is: " + address);
                } catch (IOException ioe) {
                    Log.e(TAG, "Could not get subscribed location: " + ioe.getMessage(), ioe);
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
    }
}