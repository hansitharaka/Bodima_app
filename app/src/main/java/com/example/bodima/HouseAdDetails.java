package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HouseAdDetails extends AppCompatActivity {
    //variables
    TextView Title,City,Amount,BedsNo,BathsNo,HouseSize,LandSize,Description,Name,Phone;

    private DatabaseReference mDatabase;
//    private FloatingActionButton viewform;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_ad_details);

        Title=findViewById(R.id.txtTitle);
        City=findViewById(R.id.txtCity);
        Amount=findViewById(R.id.txtAmount);
        BedsNo=findViewById(R.id.txtBathsNo);
        BathsNo=findViewById(R.id.txtBathsNo);
        HouseSize=findViewById(R.id.txtHouseSize);
        LandSize=findViewById(R.id.txtLandSize);
        Description=findViewById(R.id.txtDescription);
        Name=findViewById(R.id.txtName);
        Phone=findViewById(R.id.txtPhone);
        imageView =findViewById(R.id.img);
//        viewform = findViewById(R.id.floatCall);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Houses").child("-MIAKd48p9gPE9XkwlYm");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String hTitle=snapshot.child("title").getValue().toString();
                String hCity=snapshot.child("city").getValue().toString();
                String hAmount=snapshot.child("amount").getValue().toString();
                String hBeds=snapshot.child("beds").getValue().toString();
                String hBaths=snapshot.child("baths").getValue().toString();
                String hSize=snapshot.child("size").getValue().toString();
                String hLand=snapshot.child("landSize").getValue().toString();
                String hDes=snapshot.child("des").getValue().toString();
                String Sname= snapshot.child("name").getValue().toString();
                String Sphone=snapshot.child("phone").getValue().toString();
                String pImgUrl=snapshot.child("imgUrl").getValue().toString();

                Title.setText(hTitle);
                City.setText(hCity);
                Amount.setText(String.format("Rs. %s /month", hAmount)); //TODO:print RS layout in the details form
                BedsNo.setText(hBeds);
                BathsNo.setText(hBaths);
                HouseSize.setText(String.format("%s Sqft",hSize));
                LandSize.setText(String.format("%s Perches",hLand));
                Description.setText(hDes);
                Name.setText(Sname);
                Phone.setText(Sphone);

                Glide.with(getApplicationContext())
                        .load(pImgUrl)
                        .into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}