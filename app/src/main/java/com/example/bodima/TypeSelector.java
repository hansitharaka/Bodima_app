package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TypeSelector extends AppCompatActivity {

    Button Revanue;
    Button Expense;
    Button Restbtn;

    String Type;


    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_selector);
        Revanue=findViewById(R.id.re);
        Expense= findViewById(R.id.ex);
        Restbtn= findViewById(R.id.reset);

        Intent intent = new Intent(this, ExpensesHistory.class);

        Revanue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("typepass","Revanue");
                startActivity(intent);
            }
        });

        Expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("typepass","Expense");
                startActivity(intent);
            }
        });

        Restbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("ExpenseManeger");


                databaseReference.child("Revanue").push().removeValue();

            }
        });




    }


}