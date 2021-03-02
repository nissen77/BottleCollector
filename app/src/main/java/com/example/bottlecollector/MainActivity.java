package com.example.bottlecollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static  final  String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public int highScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StatisikSpeicher.setGeganeneKilometer(this, 30);

        //Liste holen und anzeigen
        List<String> erg = Belohnung.belohnungeng(1200, 200);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, erg);
        ListView listView = (ListView) findViewById(R.id.ausgabe);
        listView.setAdapter(adapter);
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, StatistikActivity.class);
        String message = StatisikSpeicher.getGegangeneKilometer(this);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}