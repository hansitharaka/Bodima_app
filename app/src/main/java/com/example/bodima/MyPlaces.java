package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.bodima.Model.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyPlaces extends AppCompatActivity {

    //variables
    private RecyclerView recyclerView;
    private List<Place> placeArrayList;
    private myplaceRecyclerViewAdapter recyclerAdapter;

    private DatabaseReference mreff;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places);

        //initialize
        recyclerView = (RecyclerView) findViewById(R.id.recylerView);

        //layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        //Database
        mreff = FirebaseDatabase.getInstance().getReference("Places");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //ArrayList
        placeArrayList = new ArrayList<>();

        //Clear
        ClearAll();

        //Get Data
        GetDataFromFirebase();

    }

    private void GetDataFromFirebase() {

        mreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Place place = dataSnapshot.getValue(Place.class);
                    placeArrayList.add(place);
                    recyclerAdapter = new myplaceRecyclerViewAdapter(placeArrayList);
                    recyclerView.setAdapter(recyclerAdapter);

//                    place.setTitle(dataSnapshot.child("title").getValue().toString());
//                    place.setCity(dataSnapshot.child("city").getValue().toString());
//                    place.setBaths(Integer.parseInt(dataSnapshot.child("baths").getValue().toString()));
//                    place.setBeds(Integer.parseInt(dataSnapshot.child("beds").getValue().toString()));
//                    place.setDate(dataSnapshot.child("date").getValue().toString());
                    //TODO: image should be there


                }
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ClearAll() {
        if (placeArrayList != null) {
            placeArrayList.clear();

            if (recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
            }
        } else {
            placeArrayList = new ArrayList<>();
        }
    }
}