package com.slothrop.LocationLab;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.analytics.AnalyticsEvent;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main activity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.GoToLocationActivity).setOnClickListener(v -> {
            Intent goToLocationActivity=  new Intent(this, LocationActivity.class);
            startActivity(goToLocationActivity);
        });

        findViewById(R.id.GoToAnalyticsActivity).setOnClickListener(v -> {
            Intent goToAnalyticsActivity = new Intent(this, AnalyticsActivity.class);
            startActivity(goToAnalyticsActivity);
        });

        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("Opened App")
                .addProperty("Time", Long.toString(new Date().getTime()))
                .addProperty("trackingEvent", "Main activity was opened")
                .build();

        Amplify.Analytics.recordEvent(event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("Paused main activity")
                .addProperty("Time", Long.toString(new Date().getTime()))
                .addProperty("trackingEvent", "Main activity was paused")
                .build();

        Amplify.Analytics.recordEvent(event);
        Log.i(TAG, "recording analytics event in main");
    }
}