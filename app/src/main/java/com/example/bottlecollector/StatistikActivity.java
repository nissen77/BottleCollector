package com.example.bottlecollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

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

        // Rest aufruf

        final TextView test = findViewById(R.id.testView);
        int id = 1;
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.207.13:8080/BottleCollectorREST/rest/benutzer/statistik/"+id;

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        test.setText("Respone: " + response.toString());
                        // einen einzelnen wert holen
                        //test.setText("Response: " + response.getString("gesamt"));
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        test.setText("That didn't work!\n" + error.toString());

                    }
                });

        queue.add(jsonArrayRequest);

    }
}