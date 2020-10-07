package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    //Variables
    Animation topAnim, bottomAnim;
    ImageView imgLogo;
    TextView appName, app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Hooks
        imgLogo = (ImageView) findViewById(R.id.frontlogo);
        appName = (TextView) findViewById(R.id.appName);
        app = (TextView) findViewById(R.id.app);


        /*  ANIMATIONS  */
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        imgLogo.setAnimation(topAnim);
        appName.setAnimation(bottomAnim);
        app.setAnimation(bottomAnim);

        /*  GO TO WELCOME SCREEN  */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, WelcomPage.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}