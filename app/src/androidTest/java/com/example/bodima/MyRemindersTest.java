package com.example.bodima;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

//IT19184036
//Activity Test case for the MyReminders
public class MyRemindersTest {

    @Rule
    public ActivityTestRule<MyReminders> myRemindersActivityTestRule = new ActivityTestRule<MyReminders>(MyReminders.class);

    private MyReminders myReminders = null;

    @Before
    public void setUp() throws Exception {
        myReminders = myRemindersActivityTestRule.getActivity();
    }

    @Test
    public void testLunch(){
        View view = myReminders.findViewById(R.id.floatCall);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        myReminders = null;
    }
}