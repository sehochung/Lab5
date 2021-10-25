package com.example.notes2_lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button loginButton;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "notes2_lab5";
    private static final String KEY_NAME = "username";
    private static final String KEY_PASS = "password";


//    public void loginB(View v){
//        username = (EditText) findViewById(R.id.editTextTextPersonName);
//        String name = username.getText().toString();
//        goToHome(name);
//    }


    public void goToHome (String s) {
        Intent intent = new Intent(this, HomePage.class);
        intent.putExtra("message", s);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME, null);

        if (name != null) {
            goToHome(name);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_NAME, username.getText().toString());
                editor.putString(KEY_PASS, password.getText().toString());
                editor.apply();
                goToHome(username.getText().toString());
            }
        });
    }
}