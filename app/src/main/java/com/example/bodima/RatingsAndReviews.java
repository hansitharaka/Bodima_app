package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bodima.Model.Reviews;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RatingsAndReviews extends AppCompatActivity {

    List<Reviews> reviews;
    RecyclerView recyclerView;
    ReviewsHelperAdapter reviewsHelperAdapter;
    DatabaseReference database;

    TextView userName;
    TextView review;
    RatingBar userRate;

    Button deleteButton;
    FloatingActionButton rAdd;

    String reviewText;
    String usernameText;
    int starRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings_recyclerview);

        Intent intent = getIntent();

        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(reviewsHelperAdapter);
        reviews = new ArrayList<>();

       rAdd = (FloatingActionButton) findViewById(R.id.floatCallRate);

        database = FirebaseDatabase.getInstance().getReference("Ratings");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    Reviews data = ds.getValue(Reviews.class);
                    reviews.add(data);
                    reviewsHelperAdapter = new ReviewsHelperAdapter(reviews);
                    recyclerView.setAdapter(reviewsHelperAdapter);
                }
                reviewsHelperAdapter = new ReviewsHelperAdapter(reviews);
                recyclerView.setAdapter(reviewsHelperAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        rAdd = (FloatingActionButton) findViewById(R.id.floatCallRate);

        rAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RatingsAndReviews.this, AddReview.class));
            }
        });

    }
}