package com.example.bodima;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bodima.Model.Vehicle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VehicleAllAds extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //variables
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private RecyclerView recyclerView;
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

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /* HOOKS */
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        /* TOOLBAR */
        setSupportActionBar(toolbar);
        this.setTitle("Advertisements");

        /* NAVIGATION */
        navigationView.bringToFront();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //selected nav item
        navigationView.setCheckedItem(R.id.nav_ads);

        navigationView.setNavigationItemSelectedListener(this);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //menu items action
        switch (item.getItemId()) {
            case R.id.nav_places:
                startActivity(new Intent(VehicleAllAds.this, MyPlaces.class));
                break;

            case R.id.nav_ads:
                break;

            case R.id.nav_remind:
                startActivity(new Intent(VehicleAllAds.this, MyReminders.class));
                break;

            case R.id.nav_expense:
                startActivity(new Intent(VehicleAllAds.this, Expenses_Dashboard.class));
                break;

            case R.id.nav_profile:
                startActivity(new Intent(VehicleAllAds.this, UserProfile.class));
                break;

            case R.id.nav_logout:
                //logout
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(VehicleAllAds.this, Login.class));
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        //if back pressed while the navigation drawer is open, it will close the drawer
        // instead of exiting from the app

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


}