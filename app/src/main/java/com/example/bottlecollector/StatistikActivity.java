package com.example.bottlecollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import sharedPrefSpeicherKlassen.StatisikSpeicher;

public class StatistikActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik);

        Intent intent = getIntent();
        StatisikSpeicher speicher = StatisikSpeicher.getInstance(this);

        TextView textView = findViewById(R.id.kilometerGesamt);
        textView.setText(speicher.getGegangeneMeterGesamtFS());

        textView = findViewById(R.id.besterTag);
        textView.setText(speicher.getBesterTagFS());

        textView = findViewById(R.id.besteWoche);
        textView.setText(speicher.getBesteWocheFS());

        textView = findViewById(R.id.aktuellerTag);
        textView.setText(speicher.getAktuellerTagFS());

        textView = findViewById(R.id.aktuelleWoche);
        textView.setText(speicher.getAktuelleWocheFS());
    }
}