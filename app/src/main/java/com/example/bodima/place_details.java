package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class place_details extends AppCompatActivity {

    //variables
    private TextView username, date, title, desc, amount, nBeds, nBaths, phone, city,address;
    private FloatingActionButton viewRating;
    private ImageView imageView;

    private String pUsername, pDate, pTitle, pDesc, pAmount, pBaths, pBeds, pPhone, pCity, pAddress, pImgUrl;

    private DatabaseReference mreff;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);


        //initialize
        username = (TextView) findViewById(R.id.txtUser);
        date = (TextView) findViewById(R.id.txtDate);
        title = (TextView) findViewById(R.id.txtTitle);
        desc = (TextView) findViewById(R.id.txtDescription);
        amount = (TextView) findViewById(R.id.txtAmount);
        nBeds = (TextView) findViewById(R.id.txtBedsNo);
        nBaths = (TextView) findViewById(R.id.txtBathsNo);
        phone = (TextView) findViewById(R.id.txtPhone);
        city = (TextView) findViewById(R.id.txtCity);
        address = (TextView) findViewById(R.id.txtAddress);
        imageView = (ImageView) findViewById(R.id.img);

        viewRating = (FloatingActionButton) findViewById(R.id.floatCall);

        //for now
        final String key = getIntent().getStringExtra("key");

        //Database
        mreff = FirebaseDatabase.getInstance().getReference().child("Places").child(key);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

            //retrieve data from the database
            mreff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    pUsername = getString( R.string.postUser, (snapshot.child("username").getValue().toString()) );
                    pDate = getString( R.string.postDate, (snapshot.child("date").getValue().toString()) );
                    pTitle = snapshot.child("title").getValue().toString();
                    pDesc = getString( R.string.details, (snapshot.child("desc").getValue().toString()) );
                    pAmount = getString( R.string.price, (snapshot.child("amount").getValue().toString()) );
                    pBaths = snapshot.child("baths").getValue().toString();
                    pBeds = snapshot.child("beds").getValue().toString();
                    pPhone = snapshot.child("phone").getValue().toString();
                    pCity = snapshot.child("city").getValue().toString();
                    pAddress = snapshot.child("address").getValue().toString();
                    pImgUrl = snapshot.child("imgUrl").getValue().toString();

                    //set data to the view
                    username.setText(pUsername);
                    date.setText(pDate);
                    title.setText(pTitle);
                    desc.setText(pDesc);
                    amount.setText(pAmount);
                    nBeds.setText(pBeds);
                    nBaths.setText(pBaths);
                    phone.setText(pPhone);
                    city.setText(pCity);
                    address.setText(pAddress);

                    Glide.with(getApplicationContext())
                            .load(pImgUrl)
                            .into(imageView);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            viewRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //got to ratings and reviews page
                    Intent i = new Intent(place_details.this, RatingsAndReviews.class);
                    startActivity(i);
                }
            });



    }
}