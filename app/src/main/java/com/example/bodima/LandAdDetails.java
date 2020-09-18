package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class LandAdDetails extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_ad_details);

        /* IMAGE SLIDER */
        viewPager = (ViewPager) findViewById(R.id.viewPagerLand);

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(this);

        viewPager.setAdapter(viewPageAdapter);

    }
}