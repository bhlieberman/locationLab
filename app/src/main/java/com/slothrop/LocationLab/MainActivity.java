package com.slothrop.LocationLab;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.GoToLocationActivity).setOnClickListener(v -> {
            Intent goToLocationActivity=  new Intent(this, LocationActivity.class);
            startActivity(goToLocationActivity);
        });
    }
}