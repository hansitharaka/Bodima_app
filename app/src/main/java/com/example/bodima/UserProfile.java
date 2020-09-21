package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.bodima.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {


    EditText UserEmail;
    EditText Username;



    Button Submitbtn;

    String name;
    String mail;
    String uid;
    String idT;

    private FirebaseAuth firebaseAuth;

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        UserEmail = findViewById(R.id.useremail);
        Username = findViewById(R.id.username);
        Submitbtn = findViewById(R.id.btnUpdate);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            mail = user.getEmail();

            UserEmail.setText(mail);

            String CurrentUser = user.getUid();
            Username.setText(CurrentUser);

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

        }





    }


}