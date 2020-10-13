package com.example.bodima;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

//IT19188850
public class MyPlacesTest {
    @Rule
    public ActivityTestRule<MyPlaces> myPlacesActivityTestRule = new ActivityTestRule<MyPlaces>(MyPlaces.class);

    private MyPlaces myPlaces = null;

    @Before
    public void setUp() throws Exception {
        myPlaces = myPlacesActivityTestRule.getActivity();
    }

    @Test
    public void testLunch(){
        View view = myPlaces.findViewById(R.id.addPlaceFab);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        myPlaces = null;
    }
}