package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class Vehicle_Ad_Details extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle__ad__details);

        /* IMAGE SLIDER */
        viewPager = (ViewPager) findViewById(R.id.viewPagerVehicle);

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(this);

        viewPager.setAdapter(viewPageAdapter);

    }
}