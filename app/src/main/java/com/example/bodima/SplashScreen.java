package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    //Variables
    Animation fade_out, fade_in, bottomAnim;
    ImageView imgLogo, splashbg;
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
        splashbg = (ImageView) findViewById(R.id.splashscreen);


        /*  ANIMATIONS  */
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        //Animation fade_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        imgLogo.setAnimation(fade_in);
        appName.setAnimation(bottomAnim);
        splashbg.setAnimation(fade_in);


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