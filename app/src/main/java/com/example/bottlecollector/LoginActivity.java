package com.example.bottlecollector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText userPassword;
    final Context cont = this;
    SharedPreferences sharedPref;
    RequestQueue queue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
        sharedPref =  cont.getSharedPreferences(cont.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    public void anmeldenButtonHandler(View view) {
        if(anmelden()){
            startActivity(new Intent(this, ListActivity.class));
        }
    }

    public boolean anmelden() {

        final String name = String.valueOf(username.getText());
        final String password = String.valueOf(userPassword.getText());
        if(name.length() == 0 || password.length() == 0) return false;
        final TextView errorView = findViewById(R.id.login_error);
        final String url = "http://10.0.207.13:8080/BottleCollectorREST/rest/account/login/";

        JSONObject jsonO = new JSONObject();

        queue = Volley.newRequestQueue(this);
        StringRequest loginRequest = new StringRequest
                (Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(cont.getString(R.string.userToken), response);
                            editor.apply();
                        } catch (Exception e) {
                            errorView.setText("Benutzername oder Passwort falsch!");
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                errorView.setText("503 Server nicht verfügbar");
                            }
                        }){
            // body muss ueberschrieben werden um parameter mitzugeben
            @Override
            public byte[] getBody()
            {

                JSONObject jsonObject = new JSONObject();
                String body = null;
                try
                {
                    jsonObject.put("benutzername", name);
                    jsonObject.put("passwort", password);

                    body = jsonObject.toString();
                } catch (JSONException e)
                {}

                try
                {
                    return body.toString().getBytes("utf-8");
                } catch (Exception e)
                {}
                return null;
            }

            // header ueberschreiben um den content-typ zu uebergeben
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        loginRequest.setTag("LoginTag");
        queue.add(loginRequest);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll("LoginTag");
        }
    }

}



