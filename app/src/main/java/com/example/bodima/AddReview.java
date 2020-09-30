package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.bodima.Model.Reviews;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddReview extends AppCompatActivity {
    private EditText review;
    private RatingBar rate;
    private String userId = "User01";
    private Button post;

//    private  int count;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        database = FirebaseDatabase.getInstance().getReference().child("Ratings");

        review = findViewById(R.id.review);
        rate = findViewById(R.id.starRate);

        post = findViewById(R.id.btnPost);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviewText = review.getText().toString().trim();
                int rateNumber = (int) rate.getRating();

                if (rate == null){
                    Toast.makeText(AddReview.this, "Error", Toast.LENGTH_SHORT).show();
                }
                else if(reviewText.isEmpty()){
                    Toast.makeText(AddReview.this, "Error", Toast.LENGTH_SHORT).show();
                }
                else {
                    Reviews reviews = new Reviews(userId, rateNumber, reviewText);
                    database.push().setValue(reviews);
                    Toast.makeText(AddReview.this, "success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddReview.this, RatingsAndReviews.class));
                }
            }
        });


    }
}