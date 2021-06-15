package com.example.bottlecollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextClock;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import sharedPrefSpeicherKlassen.StatisikSpeicher;

public class StatistikActivity extends AppCompatActivity {
    public RequestQueue queue;
    public static final String TAG = "MyTag";
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

        final TextView hs_user = findViewById(R.id.hs_user);
        final TextView hs_wert = findViewById(R.id.hs_wert);
        final TextView error_msg = findViewById(R.id.error_msg);

        String url = "http://10.0.207.13:8080/BottleCollectorREST/rest/benutzer/highscore/";
        queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //test.setText("Respone: " + response.toString());
                        // einen einzelnen wert holen
                        try {
                            error_msg.setText("");
                            hs_user.setText("Benutzer: "+response.getString("username"));
                            hs_wert.setText(response.getString("highscore")+"m");
                        } catch (JSONException e) {
                            hs_wert.setText("");
                            hs_user.setText("");
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error_msg.setText("503 Server nicht verfügbar");

                    }
                });
        jsonArrayRequest.setTag(TAG);
        queue.add(jsonArrayRequest);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }
}