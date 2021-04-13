package com.example.bottlecollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import locationupdates.LocaitonUpdates;

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
        /*
        //Testdaten für den Speicher anlegen
        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int woche =  date.get(weekFields.weekOfWeekBasedYear());

        StatisikSpeicher speicher = new StatisikSpeicher(this);
        speicher.setGeganeneMeterGesamt(30000);
        speicher.setBesterTag(8342, LocalDate.of(2010, 03, 10));
        speicher.setBesteWoche(4200, woche, date.getYear());
        speicher.setAktuellerTag(160);
        speicher.setAktuelleWoche(1500);

        //Liste holen und anzeigen
        List<String> erg = Belohnung.belohnungeng(1200, 200);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, erg);
        ListView listView = (ListView) findViewById(R.id.ausgabe);
        listView.setAdapter(adapter);
        */

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
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, StatistikActivity.class);
        startActivity(intent);
    }

    public void startUpdatesButtonHandler(View view) {
        Toast.makeText(this, "btn test", Toast.LENGTH_SHORT).show();
        if (!lu.mRequestingLocationUpdates) {
            lu.mRequestingLocationUpdates = true;
            //lu.setButtonsEnabledState();
            lu.startLocationUpdates();
        }
        startActivity(new Intent(this, StatistikActivity.class));
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