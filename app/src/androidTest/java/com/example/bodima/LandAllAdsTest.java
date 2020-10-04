package com.example.bodima;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

//IT19176420
//Activity Launch Test Case
public class LandAllAdsTest {
    @Rule
    public ActivityTestRule<LandAllAds> landAllAdsActivityTestRule=new ActivityTestRule<LandAllAds>(LandAllAds.class);
    private LandAllAds landAllAds=null;

    Instrumentation.ActivityMonitor monitor=getInstrumentation().addMonitor(VehicleAllAds.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        landAllAds = landAllAdsActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchOfVehicleActivityOnClick(){
        assertNotNull(landAllAds.findViewById(R.id.btnVehicle));

        onView(withId(R.id.btnVehicle)).perform(click());

        Activity vehicleAllAds = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(vehicleAllAds);
        vehicleAllAds.finish();
    }


    @After
    public void tearDown() throws Exception {
        landAllAds = null;
    }

}