package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LandForm extends AppCompatActivity {
    //variables
    EditText landTitle, landCity, landSize, landDesc, landAmount, name, phone;
    Button addImg, btnSave;
    Land land;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_form);

        landTitle = findViewById(R.id.title);
        landCity = findViewById(R.id.city);
        landSize = findViewById(R.id.landS);
        landDesc = findViewById(R.id.description);
        landAmount = findViewById(R.id.amount);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        addImg = findViewById(R.id.addImg);
        btnSave = findViewById(R.id.btnSave);
        land = new Land();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Lands");


        //onclick save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    Long phn = Long.parseLong(phone.getText().toString());

                    land.setTitle(landTitle.getText().toString());
                    land.setCity(landCity.getText().toString());
                    land.setLandSize(landSize.getText().toString());
                    land.setDes(landDesc.getText().toString());
                    land.setAmount(landAmount.getText().toString());
                    land.setName(name.getText().toString());
                    land.setPhone(phn);
                    //pushing thr values to firebase
                    mDatabase.push().setValue(land);
                    Toast.makeText(LandForm.this, "land Data inserted successfully!!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public boolean isValid() {
        String lTitle = landTitle.getText().toString().trim();
        String lCity = landCity.getText().toString().trim();
        String lAmount = landAmount.getText().toString().trim();
        String lSize = landSize.getText().toString().trim();
        String lDes = landDesc.getText().toString().trim();
        String Sname = name.getText().toString().trim();
        String Sphone = phone.getText().toString().trim();

        if (TextUtils.isEmpty(lTitle)) {
            landTitle.setError("This field cannot be empty");
            landTitle.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(lCity)) {
            landCity.setError("This field cannot be empty");
            landCity.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(lDes)) {
            landDesc.setError("This field cannot be empty");
            landDesc.requestFocus();
            return false;

        } else if (lDes.length() < 10) {
            landDesc.setError("Description should have at least 10 characters");
            landDesc.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(lAmount)) {
            landAmount.setError("This field cannot be empty");
            landAmount.requestFocus();
            return false;

        } else if (Integer.parseInt(lAmount) < 1000) {
            landAmount.setError("Amount should be at least Rs.1000");
            landAmount.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(Sphone)) {
            phone.setError("This field cannot be empty");
            phone.requestFocus();
            return false;

        } else if (Sphone.length() < 10) {
            phone.setError("Phone number should have 10 digits");
            phone.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(lSize)) {
            landSize.setError("This field cannot be empty");
            landSize.requestFocus();
            return false;

        }
//        else if (Integer.parseInt(lSize) <= 0) {
//            landSize.setError("Please enter a valid land size");
//            landSize.requestFocus();
//            return false;
//
//        }
        else if (TextUtils.isEmpty(Sname)) {
            name.setError("This field cannot be empty");
            name.requestFocus();
            return false;
        } else {
            return true;
        }
    }
}