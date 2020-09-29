package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class WelcomPage extends AppCompatActivity {
    public static final String TAG = "LifeCycle";

    //Variables
    Button btnReg, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom_page);

        //initialize
        btnReg = (Button) findViewById(R.id.signup);
        btnLogin = (Button) findViewById(R.id.login);

        Log.i(TAG, "onCreate");

        //reg button
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomPage.this, Register.class);
                startActivity(i);
            }
        });

        //login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomPage.this, Login.class);
                startActivity(i);
            }
        });
    }

}