package com.example.bottlecollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Testdaten für den Speicher anlegen
        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int woche =  date.get(weekFields.weekOfWeekBasedYear());

        StatisikSpeicher speicher = new StatisikSpeicher(this);

        speicher.setGeganeneMeterGesamt(30);
        speicher.setBesterTag(8342, LocalDate.of(2010, 03, 10));
        speicher.setBesteWoche(1870, woche, date.getYear());
        speicher.setAktuellerTag(150);
        speicher.setAktuelleWoche(1500);

        //Liste holen und anzeigen
        List<String> erg = Belohnung.belohnungeng(1200, 200);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, erg);
        ListView listView = (ListView) findViewById(R.id.ausgabe);
        listView.setAdapter(adapter);
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, StatistikActivity.class);
        startActivity(intent);
    }

}