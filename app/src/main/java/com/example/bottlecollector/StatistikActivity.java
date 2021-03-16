package com.example.bottlecollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StatistikActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik);

        Intent intent = getIntent();
        StatisikSpeicher speicher = new StatisikSpeicher(this);

        TextView textView = findViewById(R.id.kilometerGesamt);
        textView.setText(speicher.getGegangeneMeterGesamt());

        textView = findViewById(R.id.besterTag);
        textView.setText(speicher.getBesterTag());

        textView = findViewById(R.id.besteWoche);
        textView.setText(speicher.getBesteWoche());

        textView = findViewById(R.id.aktuellerTag);
        textView.setText(speicher.getAktuellerTag());

        textView = findViewById(R.id.aktuelleWoche);
        textView.setText(speicher.getAktuelleWoche());
    }
}