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

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gegenstandspeicher initialisieren

        GegenstandSpeicher speicher = new GegenstandSpeicher(this);

        //Liste holen und anzeigen
        speicher.speicherDaten(Belohnung.belohnungeng(600, 200));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, speicher.ladeDaten());
        ListView listView = (ListView) findViewById(R.id.ausgabe);
        listView.setAdapter(adapter);

    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, StatistikActivity.class);
        startActivity(intent);
    }

}
