package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VehicleAllAds extends AppCompatActivity {
    //variables
    private RecyclerView recyclerView; //TODO: Not sure if this is the right place to put the
    private ArrayList<Vehicle> vehicleArrayList;
    private vehicleRecyclerViewAdapter recyclerAdapter;
    private Button bHouse, bLand, bVehicle;
    private FloatingActionButton viewform;

    //firebase
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_all_ads);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        bHouse = (Button) findViewById(R.id.btnHouse);
        bLand = (Button) findViewById(R.id.btnLand);
        bVehicle = (Button) findViewById(R.id.btnVehicle);
        viewform = findViewById(R.id.floatCall);



        //layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        //database
        mDatabase= FirebaseDatabase.getInstance().getReference("Advertisements");

        //ArrayList
        vehicleArrayList= new ArrayList<>();

        //Clear ArrayList
        ClearAll();

        //GetDAtae method
        GetDataFromFirebase();

        //buttons
        bHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VehicleAllAds.this, AllAdvertisements.class));
            }
        });

        bLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VehicleAllAds.this, LandAllAds.class));
            }
        });

        bVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VehicleAllAds.this, VehicleAllAds.class));
            }
        });

        viewform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VehicleAllAds.this, VehicleForm.class);
                Toast.makeText(VehicleAllAds.this, "Redirecting to Vehicle Form ..", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

    }

    private void GetDataFromFirebase() {
//        Query query = mDatabase.child("Houses");
        mDatabase.child("Vehicles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Vehicle vehicle = dataSnapshot.getValue(Vehicle.class);
                    vehicleArrayList.add(vehicle);
                    recyclerAdapter = new vehicleRecyclerViewAdapter(getApplicationContext(),vehicleArrayList);
                    recyclerView.setAdapter(recyclerAdapter);

                    //TODO:image
                }
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //checking if arraylist id empty
    private void ClearAll(){
        if(vehicleArrayList!= null){
            vehicleArrayList.clear();

            if(recyclerAdapter != null){
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        vehicleArrayList=new ArrayList<>();
    }





}