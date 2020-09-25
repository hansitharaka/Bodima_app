package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HouseForm extends AppCompatActivity {
    private EditText houseTitle,houseCity,houseSize,houseLand,houseDescription,houseAmount,beds_num,baths_num,name,phone;
    private Button addImg,btnSave;
//    long maxid=0;
    House house;
//    private ProgressDialog mdialog;
    FirebaseDatabase rootNode;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_form);
        houseTitle=findViewById(R.id.houseTitle);
        houseCity=findViewById(R.id.houseCity);
        houseSize=findViewById(R.id.houseSize);
        houseLand=findViewById(R.id.houseLand);
        houseDescription=findViewById(R.id.houseDescription);
        houseAmount=findViewById(R.id.houseAmount);
        beds_num=findViewById(R.id.beds_num);
        beds_num=findViewById(R.id.beds_num);
        baths_num=findViewById(R.id.baths_num);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        addImg=findViewById(R.id.addImg);
        btnSave=findViewById(R.id.btnSave);
        house= new House();
        mDatabase =FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Houses");
        //autoincrement
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists())
//                    maxid=(snapshot.getChildrenCount());
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

       //save data in firebase on btn click
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    int beds = Integer.parseInt(beds_num.getText().toString().trim());
                    int baths = Integer.parseInt(baths_num.getText().toString().trim());
                    Long phn = Long.parseLong(phone.getText().toString().trim());

                    house.setTitle(houseTitle.getText().toString().trim());
                    house.setCity(houseCity.getText().toString().trim());
                    house.setSize(houseSize.getText().toString().trim());
                    house.setLandSize(houseLand.getText().toString().trim());
                    house.setDes(houseDescription.getText().toString().trim());
                    house.setAmount(houseAmount.getText().toString().trim());
                    house.setBeds(beds);
                    house.setBaths(baths);
                    house.setName(name.getText().toString().trim());
                    house.setPhone(phn);
                    mDatabase.push().setValue(house);
                    Toast.makeText(HouseForm.this, "House Data inserted successfully!!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //validation
    public boolean isValid(){
        String hTitle = houseTitle.getText().toString().trim();
        String hCity =  houseCity.getText().toString().trim();
        String hAmount=houseAmount.getText().toString().trim();
        String hBeds=beds_num.getText().toString().trim();
        String hBaths=baths_num.getText().toString().trim();
        String hSize=houseSize.getText().toString().trim();
        String hLand=houseLand.getText().toString().trim();
        String hDes=houseDescription.getText().toString().trim();
        String Sname = name.getText().toString().trim();
        String Sphone= phone.getText().toString().trim();

        if (TextUtils.isEmpty(hTitle)) {
            houseTitle.setError("This field cannot be empty");
            houseTitle.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(hCity)) {
            houseCity.setError("This field cannot be empty");
            houseCity.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(hDes)) {
            houseDescription.setError("This field cannot be empty");
            houseDescription.requestFocus();
            return false;

        } else if (hDes.length() < 10) {
            houseDescription.setError("Description should have at least 10 characters");
            houseDescription.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(hAmount)) {
            houseAmount.setError("This field cannot be empty");
            houseAmount.requestFocus();
            return false;

        } else if (Integer.parseInt(hAmount) < 1000) {
            houseAmount.setError("Amount should be at least Rs.1000");
            houseAmount.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(hBeds)) {
            beds_num.setError("This field cannot be empty");
            beds_num.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(hBaths)) {
            baths_num.setError("This field cannot be empty");
            baths_num.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(Sphone)) {
            phone.setError("This field cannot be empty");
            phone.requestFocus();
            return false;

        } else if (Sphone.length() < 10) {
            phone.setError("Phone number should have 10 digits");
            phone.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(hSize)) {
            houseSize.setError("This field cannot be empty");
            houseSize.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(hLand)) {
            houseLand.setError("This field cannot be empty");
            houseLand.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(Sname)) {
            name.setError("This field cannot be empty");
            name.requestFocus();
            return false;
        }
        else {
            return true;
        }
  }


}