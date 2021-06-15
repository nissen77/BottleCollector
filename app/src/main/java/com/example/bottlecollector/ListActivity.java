package com.example.bottlecollector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import belohnungsKlassen.Belohnung;
import belohnungsKlassen.Gegenstand;
import sharedPrefSpeicherKlassen.GegenstandSpeicher;

public class ListActivity extends AppCompatActivity {

    static public int wert = 0;
    double wertGerundet = 0;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gegenstandspeicher initialisieren

        GegenstandSpeicher speicher = GegenstandSpeicher.getInstance(this);

        //Liste holen und anzeigen
        speicher.speicherDaten(Belohnung.belohnungeng(600, 200));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, speicher.ladeDaten());
        ListView listView = (ListView) findViewById(R.id.ausgabe);
        listView.setAdapter(adapter);
        TextView gesamtWert = (TextView) findViewById(R.id.gesamtwert);
        wertGerundet = wert / 100.0;
        gesamtWert.setText("Gesamtwert: "+ wertGerundet +"€");


    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, StatistikActivity.class);
        startActivity(intent);
    }

}
