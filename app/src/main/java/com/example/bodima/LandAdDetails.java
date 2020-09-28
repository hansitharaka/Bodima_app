package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LandAdDetails extends AppCompatActivity {
    //variables
    TextView Title,City,Amount,LandSize,Description,Name,Phone;

    private DatabaseReference mDatabase;

    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_ad_details);


        Title=findViewById(R.id.txtTitle);
        City=findViewById(R.id.txtCity);
        Amount=findViewById(R.id.txtAmount);
        LandSize=findViewById(R.id.txtLandSize);
        Description=findViewById(R.id.txtDescription);
        Name=findViewById(R.id.txtName);
        Phone=findViewById(R.id.txtPhone);
        imageView =findViewById(R.id.img);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Lands").child("-MIHF2dBTYEBOTeMkcET");
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
                String pImgUrl=snapshot.child("imgUrl").getValue().toString();

                Title.setText(lTitle);
                City.setText(lCity);
                Amount.setText(String.format("Rs. %s ", lAmount));
                LandSize.setText(String.format("%s Perches", lLand)); //TODO: Perches print
                Description.setText(lDes);
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