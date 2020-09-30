package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bodima.Model.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MyPlaces extends AppCompatActivity implements myplaceRecyclerViewAdapter.OnItemClickListener{

    //variables
    private RecyclerView recyclerView;
    private List<Place> placeArrayList;
    private List<String> keyList;
    private myplaceRecyclerViewAdapter recyclerAdapter;

    private FirebaseStorage mStorage;
    private DatabaseReference mreff;
    private FirebaseUser currentUser;

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
        recyclerView = (RecyclerView) findViewById(R.id.recylerView);

        //layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        //Database
        mStorage = FirebaseStorage.getInstance();
        mreff = FirebaseDatabase.getInstance().getReference("Places");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //ArrayList
        placeArrayList = new ArrayList<>();

        keyList = new ArrayList<>();

        recyclerAdapter = new myplaceRecyclerViewAdapter(getApplicationContext(), placeArrayList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(MyPlaces.this);

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


}

