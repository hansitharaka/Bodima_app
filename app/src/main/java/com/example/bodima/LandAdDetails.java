package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LandAdDetails extends AppCompatActivity {
    //variables
    TextView Title,City,Amount,LandSize,Description,Name,Phone;

    private DatabaseReference mDatabase;

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_ad_details);

        /* IMAGE SLIDER */
        viewPager = (ViewPager) findViewById(R.id.viewPagerHouse);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(this);
        viewPager.setAdapter(viewPageAdapter);

        Title=findViewById(R.id.txtTitle);
        City=findViewById(R.id.txtCity);
        Amount=findViewById(R.id.txtAmount);
        LandSize=findViewById(R.id.txtLandSize);
        Description=findViewById(R.id.txtDescription);
        Name=findViewById(R.id.txtName);
        Phone=findViewById(R.id.txtPhone);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Lands").child("-MHySeAqQjDTwfFboZE0");

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String lTitle =snapshot.child("title").getValue().toString();
                String lCity =snapshot.child("city").getValue().toString();
                String lAmount=snapshot.child("amount").getValue().toString();
                String lDes=snapshot.child("des").getValue().toString();
                String lLand=snapshot.child("landSize").getValue().toString();
                String Sname= snapshot.child("name").getValue().toString();
                String Sphone=snapshot.child("phone").getValue().toString();

                Title.setText(lTitle);
                City.setText(lCity);
                Amount.setText(lAmount);
                LandSize.setText(lLand);
                Description.setText(lDes);
                Name.setText(Sname);
                Phone.setText(Sphone);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}