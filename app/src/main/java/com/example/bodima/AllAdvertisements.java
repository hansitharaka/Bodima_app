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
import android.util.Log;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AllAdvertisements extends AppCompatActivity implements houseRecyclerViewAdapter.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {
    //variables
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private List<House> houseArrayList;
    private List<String> keyList;
    private String key;

    private String uId;
    private String type1;
    private houseRecyclerViewAdapter recyclerAdapter;

    //    private House house;
    private Button bHouse, bLand, bVehicle;
    private FloatingActionButton viewform;

    //firebase
    private DatabaseReference mDatabase;
    private FirebaseStorage mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_advertisements);

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
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements");
        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mStorage = FirebaseStorage.getInstance();

        //ArrayList
        houseArrayList = new ArrayList<>();
        keyList = new ArrayList<>();

        //get key
        key = getIntent().getStringExtra("key");


        recyclerAdapter = new houseRecyclerViewAdapter(getApplicationContext(), houseArrayList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(AllAdvertisements.this);

        //Clear ArrayList
        ClearAll();

        getUserdetails();
        System.out.println("BBB"+type1);

        //buttons
        bHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllAdvertisements.this, VehicleAllAds.class));
            }
        });

        bLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllAdvertisements.this, LandAllAds.class));
            }
        });

        bVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllAdvertisements.this, VehicleAllAds.class));
            }
        });


        //floation button
//        viewform.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(AllAdvertisements.this, HouseForm.class);
//                Toast.makeText(AllAdvertisements.this, "Redirecting to House Form ..", Toast.LENGTH_SHORT).show();
//                startActivity(i);
//            }
//        });

    }

    //get data to the recycler view
    private void GetBuyerDataFromFirebase() {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Houses");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    House house = dataSnapshot.getValue(House.class);
                    houseArrayList.add(house);
//                     recyclerAdapter = new houseRecyclerViewAdapter(getApplicationContext(),houseArrayList);
//                     recyclerView.setAdapter(recyclerAdapter);
                    keyList.add(dataSnapshot.getKey());
//                    houseArrayList.add(house);

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

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Houses");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    ClearAll();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        House house = dataSnapshot.getValue(House.class);
                        keyList.add(dataSnapshot.getKey());


                        if (uId.equals(house.getuId())) {
                            houseArrayList.add(house);
                        }

                    }
                    recyclerAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(AllAdvertisements.this, "No item found", Toast.LENGTH_SHORT).show();
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
                Intent i = new Intent(AllAdvertisements.this, HouseForm.class);
                Toast.makeText(AllAdvertisements.this, "Redirecting to House Form ..", Toast.LENGTH_SHORT).show();
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
        if (houseArrayList != null) {
            houseArrayList.clear();

            if (recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
            }
        } else {
            houseArrayList = new ArrayList<>();
        }
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(AllAdvertisements.this, HouseAdDetails.class);
        intent.putExtra("key", keyList.get(position));
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        House selectedItem = houseArrayList.get(position);
        final String selectedKey = keyList.get(position);

        StorageReference imgRef = mStorage.getReferenceFromUrl(selectedItem.getImgUrl());
        imgRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabase.child("Houses").child(selectedKey).removeValue();
                Toast.makeText(AllAdvertisements.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public void onEditClick(int position) {
        Intent i = new Intent(AllAdvertisements.this, HouseForm.class);
        i.putExtra("key", keyList.get(position));
        startActivity(i);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //menu items action
        switch (item.getItemId()) {
            case R.id.nav_places:
                startActivity(new Intent(AllAdvertisements.this, MyPlaces.class));
                break;

            case R.id.nav_ads:
                break;

            case R.id.nav_remind:
                startActivity(new Intent(AllAdvertisements.this, MyReminders.class));
                break;

            case R.id.nav_expense:
                startActivity(new Intent(AllAdvertisements.this, Expenses_Dashboard.class));
                break;

            case R.id.nav_profile:
                startActivity(new Intent(AllAdvertisements.this, UserProfile.class));
                break;

            case R.id.nav_logout:
                //logout
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AllAdvertisements.this, Login.class));
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