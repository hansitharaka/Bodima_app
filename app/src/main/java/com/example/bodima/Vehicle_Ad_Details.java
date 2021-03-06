package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Vehicle_Ad_Details extends AppCompatActivity {

    TextView Title,City,Type,Condition,Fuel,Brand,Model,Amount,Description,Name,Phone;

    private DatabaseReference mDatabase;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle__ad__details);

        //fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

         Title=findViewById(R.id.txtTitle);
         Brand=findViewById(R.id.txtBrand);
         Model=findViewById(R.id.txtModel);
         City=findViewById(R.id.txtCity);
         Condition=findViewById(R.id.txtCondition);
         Type=findViewById(R.id.txtType);
         Fuel=findViewById(R.id.txtfuel);
         Amount=findViewById(R.id.txtAmount);
         Description=findViewById(R.id.txtDescription);
         Name=findViewById(R.id.txtName);
         Phone=findViewById(R.id.txtPhone);
         imageView =findViewById(R.id.img);

        final String key = getIntent().getStringExtra("key");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Vehicles").child(key);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String vTitle =snapshot.child("title").getValue().toString();
                String vCity =snapshot.child("city").getValue().toString();
                String vAmount=snapshot.child("amount").getValue().toString();
                String vDes=snapshot.child("des").getValue().toString();
                String sName= snapshot.child("name").getValue().toString();
                String sPhone=snapshot.child("phone").getValue().toString();
                String vBrand=snapshot.child("brand").getValue().toString();
                String vModel=snapshot.child("model").getValue().toString();
                String vCondition=snapshot.child("condition").getValue().toString();
                String vType=snapshot.child("type").getValue().toString();
                String vFuel=snapshot.child("fuel").getValue().toString();
                String pImgUrl=snapshot.child("imgUrl").getValue().toString();

                Title.setText(vTitle);
                Brand.setText(vBrand);
                Model.setText(vModel);
                City.setText(vCity);
                Amount.setText(String.format("Rs. %s ",vAmount));
                Description.setText(vDes);
                Name.setText(sName);
                Phone.setText(sPhone);
                Condition.setText(vCondition);
                Type.setText(vType);
                Fuel.setText(vFuel);

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