package com.example.bodima;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

//IT19184036
//Ratings activity Testing
public class RatingsAndReviewsTest {

    @Rule
    public ActivityTestRule<RatingsAndReviews> ratingsAndReviewsActivityTestRule = new ActivityTestRule<RatingsAndReviews>(RatingsAndReviews.class);

    private RatingsAndReviews ratingsAndReviews = null;

    @Before
    public void setUp() throws Exception {
        ratingsAndReviews = ratingsAndReviewsActivityTestRule.getActivity();
    }

    @Test
    public void testLunch(){
        View view = ratingsAndReviews.findViewById(R.id.username);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        ratingsAndReviews = null;
    }
}