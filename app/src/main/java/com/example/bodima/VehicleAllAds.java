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

import com.example.bodima.Model.House;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class VehicleAllAds extends AppCompatActivity implements vehicleRecyclerViewAdapter.OnItemClickListener{
    //variables
    private RecyclerView recyclerView;
    private List<Vehicle> vehicleArrayList;
    private List<String> keyList;

    private vehicleRecyclerViewAdapter recyclerAdapter;
    private Button bHouse, bLand, bVehicle;
    private FloatingActionButton viewform;

    //firebase
    private DatabaseReference mDatabase;
    private FirebaseStorage mStorage;

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
        mStorage= FirebaseStorage.getInstance();

        //ArrayList
        vehicleArrayList= new ArrayList<>();
        keyList=new ArrayList<>();

        recyclerAdapter= new vehicleRecyclerViewAdapter(getApplicationContext(), vehicleArrayList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(VehicleAllAds.this);


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
//                    recyclerAdapter = new vehicleRecyclerViewAdapter(getApplicationContext(),vehicleArrayList);
//                    recyclerView.setAdapter(recyclerAdapter);
                    keyList.add(dataSnapshot.getKey());

                }
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //checking if arraylist id empty
    private void ClearAll() {
        if (vehicleArrayList != null) {
            vehicleArrayList.clear();

            if (recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
            }
        } else {
            vehicleArrayList = new ArrayList<>();
        }
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(VehicleAllAds.this, Vehicle_Ad_Details.class);
        intent.putExtra("key", keyList.get(position));
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        Vehicle selectedItem =  vehicleArrayList.get(position);
        final String selectedKey = keyList.get(position);

        StorageReference imgRef = mStorage.getReferenceFromUrl( selectedItem.getImgUrl() );
        imgRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabase.child("Vehicles").child(selectedKey).removeValue();
                Toast.makeText(VehicleAllAds.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public void onEditClick(int position) {
        Intent i = new Intent(VehicleAllAds.this, VehicleForm.class);
        i.putExtra("key", keyList.get(position));
        startActivity(i);
    }
}