package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bodima.Model.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPlaceForm extends AppCompatActivity {

    //variables
    private TextView username, date;
    private EditText title, desc, amount, nBeds, nBaths, phone, city,address;
    private Button addImg, btnSave;

    private String pUsername;
    private Date pDate;
    private String pTitle, pDesc, pAmount, pBaths, pBeds, pPhone, pCity, pAddress;

    DatabaseReference mReff;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place_form);

        //initialize
        username = (TextView) findViewById(R.id.username);
        date = (TextView) findViewById(R.id.date);

        title = (EditText) findViewById(R.id.title);
        desc = (EditText) findViewById(R.id.description);
        amount = (EditText) findViewById(R.id.amount);
        nBaths = (EditText) findViewById(R.id.baths_num);
        nBeds = (EditText) findViewById(R.id.beds_num);
        phone = (EditText) findViewById(R.id.phone);
        city = (EditText) findViewById(R.id.city);
        address = (EditText) findViewById(R.id.address);

        addImg = (Button) findViewById(R.id.addImg);
        btnSave = (Button) findViewById(R.id.btnSave);


        //Loading current user username and current date to the form
        pUsername = "John Doe";
        username.setText(pUsername);
        //TODO: change the dummy pUsername; it should be received by the user profile

        DateFormat dateFormat = DateFormat.getDateInstance();
        date.setText( dateFormat.format(new Date()) );
        //TODO: check how expense root track date




        //add images button
//        addImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        //submit
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPlace();
            }
        });


    }

    public boolean isValid() {
        pDate = new Date();
        pTitle = title.getText().toString().trim();
        pDesc = desc.getText().toString().trim();
        pAmount = amount.getText().toString().trim();
        pBaths = nBaths.getText().toString().trim();
        pBeds = nBeds.getText().toString().trim();
        pPhone = phone.getText().toString().trim();
        pCity = city.getText().toString().trim();
        pAddress = address.getText().toString().trim();


        //validate
        if (TextUtils.isEmpty(pTitle)) {
            title.setError("This field cannot be empty");
            title.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(pDesc)) {
            desc.setError("This field cannot be empty");
            desc.requestFocus();
            return false;

        } else if (pDesc.length() < 10) {
            desc.setError("Description should have at least 10 characters");
            desc.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(pAmount)) {
            amount.setError("This field cannot be empty");
            amount.requestFocus();
            return false;

        } else if (Integer.parseInt(pAmount) < 1000) {
            amount.setError("Amount should be at least Rs.1000");
            amount.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(pBeds)) {
            nBeds.setError("This field cannot be empty");
            nBeds.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(pBaths)) {
            nBaths.setError("This field cannot be empty");
            nBaths.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(pPhone)) {
            phone.setError("This field cannot be empty");
            phone.requestFocus();
            return false;

        } else if (pPhone.length() < 10) {
            phone.setError("Phone number should have 10 digits");
            phone.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(pCity)) {
            city.setError("This field cannot be empty");
            city.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(pAddress)) {
            address.setError("This field cannot be empty");
            address.requestFocus();
            return false;

        } else {
            return true;
        }
    }

    //Insert data method
    public void AddPlace() {
        //Database
        mReff = FirebaseDatabase.getInstance().getReference().child("Places");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (isValid()) {

            //TODO: add images should be validate too
            //if validation is success, insert data

            int beds = Integer.parseInt(pBeds);
            int baths = Integer.parseInt(pBaths);
            double amount = Double.parseDouble(pAmount);

            Place myPlace= new Place(pTitle, pDesc, pCity, pAddress, pDate, beds, baths, pPhone, amount);

            //TODO: user uid or phone number must be used instead of username as child(pUsername)
            mReff.child(pUsername).setValue(myPlace);
            //mReff.child(pUsername).child(currentUser).setValue(myPlace);

            Toast.makeText(AddPlaceForm.this, "Successfull",Toast.LENGTH_SHORT).show();
        }

    }

}