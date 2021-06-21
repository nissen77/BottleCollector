package com.example.bottlecollector;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText userPassword;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
    }

    public void anmeldenButtonHandler(View view) {
        anmelden();
        //start next activity
        //startActivity(new Intent(this, ListActivity.class));
    }

    public void anmelden(){

        String name = String.valueOf(username.getText());
        String password = String.valueOf(userPassword.getText());

        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, password, Toast.LENGTH_SHORT).show();



    }
}


