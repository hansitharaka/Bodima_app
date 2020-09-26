package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bodima.Model.ExpenseAdapter;
import com.example.bodima.Model.ExpenseData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TypeSelector extends AppCompatActivity {

    Button Revanue;
    Button Expense;
    Button Restbtn;

    String Type;



    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ExpenseData ed;

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

//        Restbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                firebaseDatabase = FirebaseDatabase.getInstance();
//                databaseReference = firebaseDatabase.getReference("ExpenseManeger");
//                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot ds : snapshot.getChildren()) {
//                            ExpenseData data = ds.getValue(ExpenseData.class);
//
//
//                            System.out.println( databaseReference.getKey());
//                            //////////////////////////////////////////////////////
//
//
//                            ////////////////////////////////////////////////////////
//
//
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//
//
//
//            }
//        });




    }


}