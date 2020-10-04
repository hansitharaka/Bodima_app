package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.bodima.Model.ExpenseData;
import com.example.bodima.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {


    EditText UserEmail;
    EditText UserId;
    EditText Username;
    EditText PhoneNumber;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    User userprofile;

    Button Submitbtn;

    String name;
    String mail;
    String phone;
    String CurrentUser;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        UserEmail = findViewById(R.id.useremail);
        UserId = findViewById(R.id.userID);
        Username = findViewById(R.id.username);
        PhoneNumber = findViewById(R.id.userphone);
        Submitbtn = findViewById(R.id.btnUpdate);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            mail = user.getEmail();
            UserEmail.setText(mail);
            CurrentUser = user.getUid();
            UserId.setText(CurrentUser);
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        sendUsetDetailsToDataBase();

    }

    public void sendUsetDetailsToDataBase() {

        Submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                System.out.println("SSSSSSSSSSSSSSS");

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("User");

                userprofile = new User();

                userprofile.setName(name);
                userprofile.setEmail(mail);
                userprofile.setPhone(phone);
                userprofile.setId(CurrentUser);

                name = Username.getText().toString();
                phone = PhoneNumber.getText().toString();
                databaseReference.child(CurrentUser).setValue(userprofile);

                Intent intent = new Intent(UserProfile.this, AllAdvertisements.class);
                startActivity(intent);

            }
        });
    }
}