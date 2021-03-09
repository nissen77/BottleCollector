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

        TextView textView = findViewById(R.id.kilometerGesamt);
        textView.setText(StatisikSpeicher.getGegangeneMeterGesamt(this));

        textView = findViewById(R.id.besterTag);
        textView.setText(StatisikSpeicher.getBesterTag(this));

        textView = findViewById(R.id.besteWoche);
        textView.setText(StatisikSpeicher.getBesteWoche(this));
    }
}