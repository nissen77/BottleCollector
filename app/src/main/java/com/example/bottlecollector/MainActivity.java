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
    public static  final  String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public int highScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int woche =  date.get(weekFields.weekOfWeekBasedYear());


        StatisikSpeicher.setGeganeneMeterGesamt(this, 30);
        StatisikSpeicher.setBesterTag(this, 8342, LocalDate.of(2010, 03, 10));
        StatisikSpeicher.setBesteWoche(this, 1000, woche);

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