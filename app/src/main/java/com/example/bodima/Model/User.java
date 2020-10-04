package com.example.bodima.Model;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bodima.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {

    private String Name;
    private String Email;
    private String Password;
    private  String  phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private  String id;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User(){

    }

    public User(String name, String email, String password) {
        Name = name;
        Email = email;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public static class ExpensesHistory extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {

            TextView AmountView;
            TextView TypeView;
            TextView DateView;

            String Amount;
            String Type_;
            String Date;


            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_expenses_history);


            Intent intent = getIntent();

            AmountView = findViewById(R.id.amount_ex_text);
            TypeView = findViewById(R.id.type);
            DateView = findViewById(R.id.date_expense);


            // view data using intent


    //        Amount = AmountView.getText().toString();

    //        AmountView.setText(intent.getStringExtra("AmountEnter"));
    //        TypeView.setText(intent.getStringExtra("Type_in"));
    //
    ////        DateView.setText("");
    //        DateView.setText(intent.getStringExtra("DateAndTime"));
    // Write a message to the database


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Amount");

            myRef.setValue("hello");


        }


    }
}
