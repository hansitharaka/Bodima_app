package com.example.bodima;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bodima.Model.ExpenseData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Date;

public class Expenses_Dashboard extends AppCompatActivity {

    TextView CurrentBalnacePreview;
    Button Minus;
    Button Add;


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


    int EX_Total;
    int Re_Total;

    int Status;

    int amount;
    int amount1;


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
                } else if (Loan.isChecked()) {
                    Type = Loan.getText().toString();
                } else if (Bank_Intrest.isChecked()) {
                    Type = Bank_Intrest.getText().toString();
                } else if (Lending.isChecked()) {
                    Type = Lending.getText().toString();
                } else if (Other.isChecked()) {
                    Type = Other.getText().toString();
                } else if (Food.isChecked()) {
                    Type = Food.getText().toString();
                } else if (Transport.isChecked()) {
                    Type = Transport.getText().toString();
                } else if (Clothes.isChecked()) {
                    Type = Clothes.getText().toString();
                } else if (Rent.isChecked()) {
                    Type = Rent.getText().toString();
                } else if (Bill.isChecked()) {
                    Type = Bill.getText().toString();
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

                } else if (Loan.isChecked()) {
                    Type = Loan.getText().toString();
                } else if (Bank_Intrest.isChecked()) {
                    Type = Bank_Intrest.getText().toString();

                } else if (Lending.isChecked()) {
                    Type = Lending.getText().toString();
                } else if (Other.isChecked()) {
                    Type = Other.getText().toString();
                } else if (Food.isChecked()) {
                    Type = Food.getText().toString();
                } else if (Transport.isChecked()) {
                    Type = Transport.getText().toString();
                } else if (Clothes.isChecked()) {
                    Type = Clothes.getText().toString();
                } else if (Rent.isChecked()) {
                    Type = Rent.getText().toString();
                } else if (Bill.isChecked()) {
                    Type = Bill.getText().toString();
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
                    amount = Integer.parseInt(data.getAmount());
                    EX_Total = amount + EX_Total;
                    ////////////////////////////////////////////////////////


                }

                databaseReference = FirebaseDatabase.getInstance().getReference("ExpenseManeger").child("Revanue").child(CurrentUser);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ExpenseData data = ds.getValue(ExpenseData.class);
                            amount1 = Integer.parseInt(data.getAmount());
                            Re_Total = amount1 + Re_Total;

                        }

                        //get diffrence
                        Status = Re_Total - EX_Total;
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

}