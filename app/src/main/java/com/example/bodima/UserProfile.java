package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.bodima.Model.ExpenseData;
import com.example.bodima.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //variables
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

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

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /* HOOKS */
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        /* TOOLBAR */
        setSupportActionBar(toolbar);
        this.setTitle("My Profile");

        /* NAVIGATION */
        navigationView.bringToFront();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //selected nav item
        navigationView.setCheckedItem(R.id.nav_profile);

        navigationView.setNavigationItemSelectedListener(this);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("User");

                userprofile = new User();

                userprofile.setId(CurrentUser);
                name = Username.getText().toString();
                phone = PhoneNumber.getText().toString();


                userprofile.setName(name);
                userprofile.setEmail(mail);
                userprofile.setPhone(phone);



                databaseReference.child(CurrentUser).setValue(userprofile);

                Intent intent = new Intent(UserProfile.this, Login.class);
                startActivity(intent);

            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //menu items action
        switch (item.getItemId()) {
            case R.id.nav_places:
                startActivity(new Intent(UserProfile.this, MyPlaces.class));
                break;

            case R.id.nav_ads:
                startActivity(new Intent(UserProfile.this, AllAdvertisements.class));
                break;

            case R.id.nav_remind:
                startActivity(new Intent(UserProfile.this, MyReminders.class));
                break;

            case R.id.nav_expense:
                startActivity(new Intent(UserProfile.this, Expenses_Dashboard.class));
                break;

            case R.id.nav_profile:
                break;

            case R.id.nav_logout:
                //logout
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserProfile.this, Login.class));
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        //if back pressed while the navigation drawer is open, it will close the drawer
        // instead of exiting from the app

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }



}