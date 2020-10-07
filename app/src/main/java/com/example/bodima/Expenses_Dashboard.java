package com.example.bodima;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.bodima.Model.ExpenseData;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Date;

public class Expenses_Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //variables
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    TextView CurrentBalnacePreview;
    Button Minus;
    Button Add;
    FloatingActionButton Next;


    EditText amountInput;
    RadioButton Salary;
    RadioButton Bank_Intrest;
    RadioButton Loan;
    RadioButton Lending;
    RadioButton Other;
    RadioButton Food;
    RadioButton Transport;
    RadioButton Clothes;
    RadioButton Rent;
    RadioButton Bill;


    float EX_Total;
    float Re_Total;
    float Status;
    float amount;
    float amount1;


    String ValueA;
    String CurrentUser;
    String Type;
    String datetime;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ExpenseData expenseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses__dashboard);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /* HOOKS */
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        /* TOOLBAR */
        setSupportActionBar(toolbar);
        this.setTitle("Expenses Dashboard");

        /* NAVIGATION */
        navigationView.bringToFront();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //selected nav item
        navigationView.setCheckedItem(R.id.nav_places);

        navigationView.setNavigationItemSelectedListener(this);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        //get id's from resources
        Minus = findViewById(R.id.btnMinus);
        Add = findViewById(R.id.btnPlus);
        CurrentBalnacePreview = findViewById(R.id.current_balance);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            CurrentUser = user.getUid();
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

        }

        Next=findViewById(R.id.history);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Expenses_Dashboard.this, TypeSelector.class);
                startActivity(intent);
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        // start coding to minus button
        Add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                Context context = getApplicationContext();
                CharSequence message = "Revanue";

                int duration = Toast.LENGTH_SHORT; //How long the toastmessage will lasts
                Toast toast = Toast.makeText(context, message, duration);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 100);
                toast.show();

                Intent intent = new Intent(Expenses_Dashboard.this, TypeSelector.class);


                amountInput = findViewById(R.id.in_amount);
                Salary = findViewById(R.id.Salary);
                Bank_Intrest = findViewById(R.id.intrest);
                Loan = findViewById(R.id.Loan);
                Lending = findViewById(R.id.Lend);
                Other = findViewById(R.id.addother);
                Food = findViewById(R.id.food);
                Transport = findViewById(R.id.transport);
                Clothes = findViewById(R.id.clothes);
                Rent = findViewById(R.id.rent);
                Bill = findViewById(R.id.bill);


                if (Salary.isChecked()) {
                    Type = Salary.getText().toString();
                    Salary.setChecked(true);
                    Bank_Intrest.setChecked(false);
                    Loan.setChecked(false);
                    Lending.setChecked(false);
                    Other.setChecked(false);
                    Food.setChecked(false);
                    Transport.setChecked(false);
                    Clothes.setChecked(false);
                    Rent.setChecked(false);
                    Bill.setChecked(false);

                } else if (Loan.isChecked()) {

                    Type = Loan.getText().toString();
                    Loan.setChecked(true);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(false);
                    Lending.setChecked(false);
                    Other.setChecked(false);
                    Food.setChecked(false);
                    Transport.setChecked(false);
                    Clothes.setChecked(false);
                    Rent.setChecked(false);
                    Bill.setChecked(false);
                } else if (Bank_Intrest.isChecked()) {
                    Type = Bank_Intrest.getText().toString();
                    Loan.setChecked(false);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(true);
                    Lending.setChecked(false);
                    Other.setChecked(false);
                    Food.setChecked(false);
                    Transport.setChecked(false);
                    Clothes.setChecked(false);
                    Rent.setChecked(false);
                    Bill.setChecked(false);

                } else if (Lending.isChecked()) {
                    Type = Lending.getText().toString();
                    Loan.setChecked(false);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(false);
                    Lending.setChecked(true);
                    Other.setChecked(false);
                    Food.setChecked(false);
                    Transport.setChecked(false);
                    Clothes.setChecked(false);
                    Rent.setChecked(false);
                    Bill.setChecked(false);
                } else if (Other.isChecked()) {
                    Type = Other.getText().toString();
                    Loan.setChecked(false);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(false);
                    Lending.setChecked(false);
                    Other.setChecked(true);
                    Food.setChecked(false);
                    Transport.setChecked(false);
                    Clothes.setChecked(false);
                    Rent.setChecked(false);
                    Bill.setChecked(false);
                } else if (Food.isChecked()) {
                    Type = Food.getText().toString();
                    Loan.setChecked(false);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(false);
                    Lending.setChecked(false);
                    Other.setChecked(false);
                    Food.setChecked(true);
                    Transport.setChecked(false);
                    Clothes.setChecked(false);
                    Rent.setChecked(false);
                    Bill.setChecked(false);
                } else if (Transport.isChecked()) {
                    Type = Transport.getText().toString();
                    Loan.setChecked(false);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(false);
                    Lending.setChecked(false);
                    Other.setChecked(false);
                    Food.setChecked(false);
                    Transport.setChecked(true);
                    Clothes.setChecked(false);
                    Rent.setChecked(false);
                    Bill.setChecked(false);
                } else if (Clothes.isChecked()) {
                    Type = Clothes.getText().toString();
                    Loan.setChecked(false);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(false);
                    Lending.setChecked(false);
                    Other.setChecked(false);
                    Food.setChecked(false);
                    Transport.setChecked(false);
                    Clothes.setChecked(true);
                    Rent.setChecked(false);
                    Bill.setChecked(false);
                } else if (Rent.isChecked()) {
                    Type = Rent.getText().toString();
                    Loan.setChecked(false);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(false);
                    Lending.setChecked(false);
                    Other.setChecked(false);
                    Food.setChecked(false);
                    Transport.setChecked(false);
                    Clothes.setChecked(false);
                    Rent.setChecked(true);
                    Bill.setChecked(false);
                } else if (Bill.isChecked()) {
                    Type = Bill.getText().toString();
                    Loan.setChecked(false);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(false);
                    Lending.setChecked(false);
                    Other.setChecked(false);
                    Food.setChecked(false);
                    Transport.setChecked(false);
                    Clothes.setChecked(false);
                    Rent.setChecked(false);
                    Bill.setChecked(true);
                }


                ValueA = amountInput.getText().toString();

                datetime = java.text.DateFormat.getDateTimeInstance().format(new Date());



                if (ValueA.isEmpty()) {
                    Toast.makeText(context, "Empty amount", Toast.LENGTH_SHORT).show();
                } else {
                    sendDataToFireBaseforRevanue();
                }


                startActivity(intent);


            }
        });


        // start coding to minus button
        Minus.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                Context context = getApplicationContext();
                CharSequence message = "Expenses";
                int duration = Toast.LENGTH_SHORT; //How long the toastmessage will lasts
                Toast toast = Toast.makeText(context, message, duration);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 100);
                toast.show();

                Intent intent = new Intent(Expenses_Dashboard.this, TypeSelector.class);
                amountInput = findViewById(R.id.in_amount);


                Salary = findViewById(R.id.Salary);
                Bank_Intrest = findViewById(R.id.intrest);
                Loan = findViewById(R.id.Loan);
                Lending = findViewById(R.id.Lend);
                Other = findViewById(R.id.addother);


                Food = findViewById(R.id.food);
                Transport = findViewById(R.id.transport);
                Clothes = findViewById(R.id.clothes);
                Rent = findViewById(R.id.rent);
                Bill = findViewById(R.id.bill);


                if (Salary.isChecked()) {
                    Type = Salary.getText().toString();
                    Loan.setChecked(false);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(true);
                    Lending.setChecked(false);
                    Other.setChecked(false);
                    Food.setChecked(false);
                    Transport.setChecked(false);
                    Clothes.setChecked(false);
                    Rent.setChecked(false);
                    Bill.setChecked(false);
                } else if (Loan.isChecked()) {
                    Type = Loan.getText().toString();
                    Loan.setChecked(true);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(false);
                    Lending.setChecked(false);
                    Other.setChecked(false);
                    Food.setChecked(false);
                    Transport.setChecked(false);
                    Clothes.setChecked(false);
                    Rent.setChecked(false);
                    Bill.setChecked(false);
                } else if (Bank_Intrest.isChecked()) {
                    Type = Bank_Intrest.getText().toString();
                    Loan.setChecked(false);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(true);
                    Lending.setChecked(false);
                    Other.setChecked(false);
                    Food.setChecked(false);
                    Transport.setChecked(false);
                    Clothes.setChecked(false);
                    Rent.setChecked(false);
                    Bill.setChecked(false);

                } else if (Lending.isChecked()) {
                    Type = Lending.getText().toString();
                    Loan.setChecked(false);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(false);
                    Lending.setChecked(true);
                    Other.setChecked(false);
                    Food.setChecked(false);
                    Transport.setChecked(false);
                    Clothes.setChecked(false);
                    Rent.setChecked(false);
                    Bill.setChecked(false);
                } else if (Other.isChecked()) {
                    Type = Other.getText().toString();
                    Loan.setChecked(false);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(false);
                    Lending.setChecked(false);
                    Other.setChecked(true);
                    Food.setChecked(false);
                    Transport.setChecked(false);
                    Clothes.setChecked(false);
                    Rent.setChecked(false);
                    Bill.setChecked(false);
                } else if (Food.isChecked()) {
                    Type = Food.getText().toString();
                    Loan.setChecked(false);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(false);
                    Lending.setChecked(false);
                    Other.setChecked(false);
                    Food.setChecked(true);
                    Transport.setChecked(false);
                    Clothes.setChecked(false);
                    Rent.setChecked(false);
                    Bill.setChecked(false);
                } else if (Transport.isChecked()) {
                    Type = Transport.getText().toString();
                    Loan.setChecked(false);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(false);
                    Lending.setChecked(false);
                    Other.setChecked(false);
                    Food.setChecked(false);
                    Transport.setChecked(true);
                    Clothes.setChecked(false);
                    Rent.setChecked(false);
                    Bill.setChecked(false);
                } else if (Clothes.isChecked()) {
                    Type = Clothes.getText().toString();
                    Loan.setChecked(false);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(false);
                    Lending.setChecked(false);
                    Other.setChecked(false);
                    Food.setChecked(false);
                    Transport.setChecked(false);
                    Clothes.setChecked(true);
                    Rent.setChecked(false);
                    Bill.setChecked(false);
                } else if (Rent.isChecked()) {
                    Type = Rent.getText().toString();
                    Loan.setChecked(false);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(false);
                    Lending.setChecked(false);
                    Other.setChecked(false);
                    Food.setChecked(false);
                    Transport.setChecked(false);
                    Clothes.setChecked(false);
                    Rent.setChecked(true);
                    Bill.setChecked(false);
                } else if (Bill.isChecked()) {
                    Type = Bill.getText().toString();
                    Loan.setChecked(false);
                    Salary.setChecked(false);
                    Bank_Intrest.setChecked(false);
                    Lending.setChecked(false);
                    Other.setChecked(false);
                    Food.setChecked(false);
                    Transport.setChecked(false);
                    Clothes.setChecked(false);
                    Rent.setChecked(false);
                    Bill.setChecked(true);
                }

                //get values
                ValueA = amountInput.getText().toString();


                //get and set date and time
                datetime = java.text.DateFormat.getDateTimeInstance().format(new Date());


//10

                if (ValueA.isEmpty()) {
                    Toast.makeText(context, "Empty amount", Toast.LENGTH_SHORT).show();
                } else {
                    sendDataToFireBaseforExpenses();
                }


                startActivity(intent);
            }
        });

        //set value to text field
        getStatusOfValut();


    }



    @Override
    protected void onPause() {
        super.onPause();

        Status=0;
        EX_Total = 0;
        Re_Total = 0;

        Log.d("TAG","onPuase Called");
        System.out.println("QQQQ"+Status);
    }



    public void sendDataToFireBaseforExpenses() {
//send data to a firebse
        //create datebase path
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ExpenseManeger");
        expenseData = new ExpenseData();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            CurrentUser = user.getUid();
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

        }


        expenseData.setAmount(ValueA);
        expenseData.setDate(datetime);
        expenseData.setType(Type);



        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // use to upadte with addlistnerfor
//                        databaseReference.child("Expenses").setValue(expenseData);

                databaseReference.child("Expenses").child(CurrentUser).push().setValue(expenseData);
                Toast.makeText(Expenses_Dashboard.this, "data Inserted", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void sendDataToFireBaseforRevanue() {
        //create datebase path
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ExpenseManeger");
        expenseData = new ExpenseData();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


//        UserID=firebaseAuth

//        CurrentUser = user.getUid();
        expenseData.setAmount(ValueA);
        expenseData.setDate(datetime);
        expenseData.setType(Type);
//        expenseData.setUid("123");


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        databaseReference.child("Revanue").setValue(expenseData);use for update
                databaseReference.child("Revanue").child(CurrentUser).push().setValue(expenseData);


                Toast.makeText(Expenses_Dashboard.this, "data Inserted", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void getStatusOfValut() {
        databaseReference = FirebaseDatabase.getInstance().getReference("ExpenseManeger").child("Expenses").child(CurrentUser);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    ExpenseData data = ds.getValue(ExpenseData.class);

                                     //////////////////////////////////////////////////////
                    amount = Float.parseFloat(data.getAmount());
                    getTotalExpense(amount);
//                    EX_Total = amount + EX_Total;
                    ////////////////////////////////////////////////////////


                }

                databaseReference = FirebaseDatabase.getInstance().getReference("ExpenseManeger").child("Revanue").child(CurrentUser);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {

                            ExpenseData data = ds.getValue(ExpenseData.class);
                            amount1 = Float.parseFloat(data.getAmount());
                            getTotalRevanue(amount1);
//                            Re_Total = amount1 + Re_Total;

                        }

                        //get diffrence
//                        Status = Re_Total - EX_Total;
                        getDifference(Re_Total,EX_Total);

                        CurrentBalnacePreview.setText(String.valueOf(Status));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    //get the total of expenses
    public float getTotalExpense( float amount){

        EX_Total = amount + EX_Total;
        return EX_Total;
    }

    //get the totla of revanue
    public float getTotalRevanue(float amount1){
        Re_Total = amount1 + Re_Total;
        return  Re_Total;
    }

    //get the difference of Expences and Revanues
    public float getDifference(float Re,float Ex){
        Re_Total=Re;
        EX_Total=Ex;
        Status = Re_Total - EX_Total;
        return Status;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //menu items action
        switch (item.getItemId()) {
            case R.id.nav_places:
                startActivity(new Intent(Expenses_Dashboard.this, MyPlaces.class));
                break;

            case R.id.nav_ads:
                startActivity(new Intent(Expenses_Dashboard.this, AllAdvertisements.class));
                break;

            case R.id.nav_remind:
                startActivity(new Intent(Expenses_Dashboard.this, MyReminders.class));
                break;

            case R.id.nav_expense:
                break;

            case R.id.nav_profile:
                startActivity(new Intent(Expenses_Dashboard.this, UserProfile.class));
                break;

            case R.id.nav_logout:
                //logout
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Expenses_Dashboard.this, Login.class));
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