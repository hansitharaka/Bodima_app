package com.example.bodima;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

//IT19176420
// Intent Test case
public class AllAdvertisementsTest {

    @Rule
    public ActivityTestRule<AllAdvertisements> allAdvertisementsActivityTestRule = new ActivityTestRule<AllAdvertisements>(AllAdvertisements.class);

    private AllAdvertisements allAdvertisements= null;

    @Before
    public void setUp() throws Exception {
        allAdvertisements = allAdvertisementsActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = allAdvertisements.findViewById(R.id.floatCall);
        assertNotNull(view);
    }
    @After
    public void tearDown() throws Exception {
        allAdvertisements=null;
    }

}