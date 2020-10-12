package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bodima.Model.House;
import com.example.bodima.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class VehicleAllAds extends AppCompatActivity implements vehicleRecyclerViewAdapter.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {
    //variables
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private List<Vehicle> vehicleArrayList;
    private List<String> keyList;
    private String key;

    private String uId;
    private String type1;
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

        //layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        //database
        mDatabase= FirebaseDatabase.getInstance().getReference("Advertisements");
        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mStorage= FirebaseStorage.getInstance();

        //ArrayList
        vehicleArrayList= new ArrayList<>();
        keyList=new ArrayList<>();

        //get key
        key = getIntent().getStringExtra("key");

        recyclerAdapter= new vehicleRecyclerViewAdapter(getApplicationContext(), vehicleArrayList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(VehicleAllAds.this);


        //Clear ArrayList
        ClearAll();

        getUserdetails();
        System.out.println("BBB"+type1);

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

//        viewform.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(VehicleAllAds.this, VehicleForm.class);
//                Toast.makeText(VehicleAllAds.this, "Redirecting to Vehicle Form ..", Toast.LENGTH_SHORT).show();
//                startActivity(i);
//            }
//        });

    }

    private void GetBuyerDataFromFirebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Vehicles");
        mDatabase.addValueEventListener(new ValueEventListener() {
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

        viewform = findViewById(R.id.floatCall);
        viewform.setVisibility(View.GONE);


    }

    //    seller data retreive
    public void GetSellerDataFromFirebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Vehicles");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    ClearAll();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                      Vehicle vehicle = dataSnapshot.getValue(Vehicle.class);
                        keyList.add(dataSnapshot.getKey());


                        if (uId.equals(vehicle.getuId())) {
                            vehicleArrayList.add(vehicle);
                        }

                    }
                    recyclerAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(VehicleAllAds.this, "No item found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //button float
        viewform = findViewById(R.id.floatCall);
        viewform.setVisibility(View.VISIBLE);


        viewform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VehicleAllAds.this, VehicleForm.class);
                Toast.makeText(VehicleAllAds.this, "Redirecting to Vehicle Form ..", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

    }

    //get the user type from userDetails
    public void getUserdetails() {

        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        System.out.println(uId);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(uId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                User user = snapshot.getValue(User.class);
                if (uId.equals(user.getId())) {
                    type1 = user.getType();

                }
                if(type1.equals("buyer")) {
                    recyclerAdapter.setUsertype("buyer");
                    GetBuyerDataFromFirebase();
                }
                else if(type1.equals("seller")) {
                    recyclerAdapter.setUsertype("seller");
                    GetSellerDataFromFirebase();
                }

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