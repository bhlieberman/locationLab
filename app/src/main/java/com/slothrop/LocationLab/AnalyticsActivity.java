package com.slothrop.LocationLab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;


import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.core.Amplify;

import java.util.Date;

public class AnalyticsActivity extends AppCompatActivity {
    private static final String TAG = "analytics activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("Opened Analytics Activity")
                .addProperty("Time", Long.toString(new Date().getTime()))
                .addProperty("trackingEvent", "Analytics activity was opened")
                .build();

        Amplify.Analytics.recordEvent(event);
        Log.i(TAG, "recording event in analytics activity");
    }
}