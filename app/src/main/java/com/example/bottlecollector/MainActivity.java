package com.example.bottlecollector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import belohnungsKlassen.Gegenstand;

public class MainActivity extends AppCompatActivity {
    static List<Gegenstand> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> erg = belohnungeng(1200, 200);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, erg);
        ListView listView = (ListView) findViewById(R.id.ausgabe);
        listView.setAdapter(adapter);
    }

    private List<String> belohnungeng(int entfernung, final int belohnungsWert){
        List<String> beute = new ArrayList<>();
        items.add(new Gegenstand("Glas Flasche", 8, 40));
        items.add(new Gegenstand("PET Flasche", 15, 30));
        items.add(new Gegenstand("Bier Dose", 25, 3));
        for(int i = 0; i < entfernung; i+=belohnungsWert) {
            for (Gegenstand g : items) {
                if (g.getDropChance() >= Math.random() * 100) {
                    beute.add(g.getBezeichnung());
                }
            }
        }
        return beute;
    }
}