package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class place_details extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        /* IMAGE SLIDER */
        viewPager = (ViewPager) findViewById(R.id.viewPagerPlace);

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(this);

        viewPager.setAdapter(viewPageAdapter);


    }
}