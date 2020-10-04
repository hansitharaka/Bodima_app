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
import android.widget.EditText;
import android.widget.Toast;

import com.example.bodima.Model.Place;
import com.example.bodima.Model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MyPlaces extends AppCompatActivity implements myplaceRecyclerViewAdapter.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    //variables
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private List<Place> placeArrayList;
    private List<String> keyList;
    private myplaceRecyclerViewAdapter recyclerAdapter;

    private FirebaseStorage mStorage;
    private DatabaseReference mreff;
    DatabaseReference user;
    private FirebaseUser currentUser;

    private FloatingActionButton floatingActionButton;
    private Button bSearch;
    private EditText searchText;

    private String utype;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /* HOOKS */
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        /* TOOLBAR */
        setSupportActionBar(toolbar);
        this.setTitle("Places");

        /* NAVIGATION */
        navigationView.bringToFront();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //selected nav item
        navigationView.setCheckedItem(R.id.nav_places);

        navigationView.setNavigationItemSelectedListener(this);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        //initialize
        recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.addPlaceFab);
        bSearch = (Button) findViewById(R.id.btnSearch);
        searchText = (EditText) findViewById(R.id.search);

        //layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        //Database
        mStorage = FirebaseStorage.getInstance();
        mreff = FirebaseDatabase.getInstance().getReference("Places");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //get from user node
        userId = currentUser.getUid();
        user = FirebaseDatabase.getInstance().getReference("User").child(userId);

        //ArrayList
        placeArrayList = new ArrayList<>();

        keyList = new ArrayList<>();

        recyclerAdapter = new myplaceRecyclerViewAdapter(getApplicationContext(), placeArrayList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(MyPlaces.this);


        //get current user type
        GetCurrentUserType();


        //floatingAction button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyPlaces.this, AddPlaceForm.class));
            }
        });

        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchText.getText().toString().isEmpty()) {
                    searchText.getText().clear();

                    GetDataFromFirebase();
                }
            }
        });

        //search
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchCity = searchText.getText().toString().trim();

                if(searchCity.isEmpty()) {
                    Toast.makeText(MyPlaces.this, "Enter a city", Toast.LENGTH_SHORT).show();
                } else {
                    searchCity(searchCity);
                }
            }
        });


    }

    private void GetCurrentUserType() {
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    utype = user.getType();

                    if (utype.equals("seller")) {

                        ClearAll();
                        GetSellerDataFromDatabase();

                    } else if (utype.equals("buyer")) {

                        ClearAll();
                        GetDataFromFirebase();
                        floatingActionButton.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("GetUserType", error.getMessage());
            }

        });

    }

    //search function
    private void searchCity(String city) {
        mreff.orderByChild("city").equalTo(city).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    ClearAll();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Place place = dataSnapshot.getValue(Place.class);
                        keyList.add(dataSnapshot.getKey());
                        placeArrayList.add(place);
                    }
                    recyclerAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MyPlaces.this, "No item found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //retrieve method for seller
    private void GetSellerDataFromDatabase() {
        mreff.orderByChild("uid").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    ClearAll();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Place place = dataSnapshot.getValue(Place.class);
                        keyList.add(dataSnapshot.getKey());
                        placeArrayList.add(place);
                    }
                    recyclerAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MyPlaces.this, "No item found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //retrieve method for buyer
    private void GetDataFromFirebase() {
        mreff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Place place = dataSnapshot.getValue(Place.class);
                    keyList.add(dataSnapshot.getKey());
                    placeArrayList.add(place);
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

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MyPlaces.this, place_details.class);
        intent.putExtra("key", keyList.get(position));
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        Place selectedItem =  placeArrayList.get(position);
        final String selectedKey = keyList.get(position);

        StorageReference imgRef = mStorage.getReferenceFromUrl( selectedItem.getImgUrl() );
        imgRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mreff.child(selectedKey).removeValue();
                Toast.makeText(MyPlaces.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public void onEditClick(int position) {
        Intent i = new Intent(MyPlaces.this, AddPlaceForm.class);
        i.putExtra("key", keyList.get(position));
        startActivity(i);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //menu items action
        switch (item.getItemId()) {
            case R.id.nav_places:
                break;

            case R.id.nav_ads:
                startActivity(new Intent(MyPlaces.this, AllAdvertisements.class));
                break;

            case R.id.nav_remind:
                startActivity(new Intent(MyPlaces.this, MyReminders.class));
                break;

            case R.id.nav_expense:
                startActivity(new Intent(MyPlaces.this, Expenses_Dashboard.class));
                break;

            case R.id.nav_profile:
                startActivity(new Intent(MyPlaces.this, UserProfile.class));
                break;

            case R.id.nav_logout:
                //logout
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MyPlaces.this, Login.class));
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

