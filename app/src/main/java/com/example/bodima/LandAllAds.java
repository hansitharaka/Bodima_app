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
import com.example.bodima.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class LandAllAds extends AppCompatActivity implements landRecyclerViewAdapter.OnItemClickListener {
    //variables
    private RecyclerView recyclerView; //TODO: Not sure if this is the right place to put the
    private List<Land> landArrayList;
    private List<String> keyList;
    private String key;

    private String uId;
    private String type1;
    //private Land land;
    private landRecyclerViewAdapter recyclerAdapter;

    private Button bHouse, bLand, bVehicle;
    private FloatingActionButton viewform;

    //firebase
    private DatabaseReference mDatabase;
    private FirebaseStorage mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_all_ads);

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

        //get the current user id
        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mStorage= FirebaseStorage.getInstance();

        //ArrayList
        landArrayList= new ArrayList<>();
        keyList=new ArrayList<>();

        //get key
        key = getIntent().getStringExtra("key");

        recyclerAdapter= new landRecyclerViewAdapter(getApplicationContext(), landArrayList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(LandAllAds.this);

        //Clear ArrayList
        ClearAll();

        getUserdetails();
        System.out.println("BBB"+type1);

        //buttons
        bHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandAllAds.this, AllAdvertisements.class));
            }
        });

        bLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandAllAds.this, LandAllAds.class));
            }
        });

        bVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandAllAds.this, VehicleAllAds.class));
            }
        });

//        viewform.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(LandAllAds.this, LandForm.class);
//                Toast.makeText(LandAllAds.this, "Redirecting to Land Form ..", Toast.LENGTH_SHORT).show();
//                startActivity(i);
//            }
//        });
    }

    private void GetBuyerDataFromFirebase() {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Lands");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Land land = dataSnapshot.getValue(Land.class);
                    landArrayList.add(land);
//                    recyclerAdapter = new landRecyclerViewAdapter(getApplicationContext(),landArrayList);
//                    recyclerView.setAdapter(recyclerAdapter);
                    keyList.add(dataSnapshot.getKey());
//                    landArrayList.add(land);
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
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Lands");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    ClearAll();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Land land = dataSnapshot.getValue(Land.class);
                        keyList.add(dataSnapshot.getKey());


                        if (uId.equals(land.getuId())) {
                            landArrayList.add(land);
                        }

                    }
                    recyclerAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(LandAllAds.this, "No item found", Toast.LENGTH_SHORT).show();
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
                Intent i = new Intent(LandAllAds.this, LandForm.class);
                Toast.makeText(LandAllAds.this, "Redirecting to Land Form ..", Toast.LENGTH_SHORT).show();
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
                    GetBuyerDataFromFirebase();
                }
                else if(type1.equals("seller")) {
                    GetSellerDataFromFirebase();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void ClearAll(){
        if(landArrayList!= null){
            landArrayList.clear();

            if(recyclerAdapter != null){
                recyclerAdapter.notifyDataSetChanged();
            }
        }else {
            landArrayList = new ArrayList<>();
        }
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(LandAllAds.this, LandAdDetails.class);
        intent.putExtra("key", keyList.get(position));
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        Land selectedItem =  landArrayList.get(position);
        final String selectedKey = keyList.get(position);

        StorageReference imgRef = mStorage.getReferenceFromUrl(selectedItem.getImgUrl() );
        imgRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                mDatabase.child("Lands").child(selectedKey).removeValue();
                Toast.makeText(LandAllAds.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public void onEditClick(int position) {
        Intent i = new Intent(LandAllAds.this, LandForm.class);
        i.putExtra("key", keyList.get(position));
        startActivity(i);
    }
}