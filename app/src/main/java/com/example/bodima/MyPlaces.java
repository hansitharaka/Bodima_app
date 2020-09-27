package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.bodima.Model.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyPlaces extends AppCompatActivity {

    //variables
    private RecyclerView recyclerView;
    private ArrayList<Place> placeArrayList;

    private DatabaseReference mreff;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places);

        //initialize
        recyclerView =(RecyclerView) findViewById(R.id.recylerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //Database
        mreff = FirebaseDatabase.getInstance().getReference().child("Places");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //ArrayList
        placeArrayList = new ArrayList<>();

        //Clear
        ClearAll();

        //Get Data
        GetDataFromFirebase();

    }

    private void GetDataFromFirebase() {

    }

    private void ClearAll() {
        if (placeArrayList != null) {
            placeArrayList.clear();
        } else {
            placeArrayList = new ArrayList<>();
        }
    }
}