package com.example.bottlecollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.LocationServices;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import locationupdates.LocaitonUpdates;
import sharedPrefSpeicherKlassen.StatisikSpeicher;

public class MainActivity extends AppCompatActivity {

    LocaitonUpdates lu = new LocaitonUpdates();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lu.main = MainActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (!lu.checkPermissions()) {
            lu.requestPermissions();
        }

        lu.mStartUpdatesButton = (Button) findViewById(R.id.btn_Weiter);
        lu.mStopUpdatesButton = (Button) findViewById(R.id.btn_stop);

        lu.mRequestingLocationUpdates = false;

        // Update values using data stored in the Bundle.
        lu.updateValuesFromBundle(savedInstanceState);

        lu.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        lu.mSettingsClient = LocationServices.getSettingsClient(this);

        // Kick off the process of building the LocationCallback, LocationRequest, and
        // LocationSettingsRequest objects.
        lu.createLocationCallback();
        lu.createLocationRequest();
        lu.buildLocationSettingsRequest();

        //Testdaten für den Speicher anlegen

        StatisikSpeicher speicher = new StatisikSpeicher(this);
        speicher.initSharedPref();
        /*

         */
        speicher.setGeganeneMeterGesamt(30000);
        speicher.setBesterTag(8342, LocalDate.of(2010, 3, 10));
        // set aktuelle woche muss vor aktueller tag aufgerufen werden
        speicher.setAktuelleWoche(150);
        speicher.setAktuellerTag(160);

    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void startUpdatesButtonHandler(View view) {
        if (!lu.mRequestingLocationUpdates) {
            lu.mRequestingLocationUpdates = true;
            //lu.setButtonsEnabledState();
            lu.startLocationUpdates();
        }
        startActivity(new Intent(this, ListActivity.class));
    }

    /**
     * Handles the Stop Updates button, and requests removal of location updates.
     */
    public void stopUpdatesButtonHandler(View view) {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        lu.stopLocationUpdates();
    }

}