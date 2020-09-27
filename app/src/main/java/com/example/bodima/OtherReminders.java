package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bodima.Model.Reminders;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OtherReminders extends AppCompatActivity {

    List<Reminders> reminders;
    RecyclerView recyclerView;
    ReminderHelperAdapter reminderHelperAdapter;
    DatabaseReference databaseReference;

    TextView description;
    TextView day;
    TextView month;
    TextView amount;

    String descriptionText;
    String dayText;
    String monthText;
    String amountText;

    Button bPay, bBday, bOther;
    FloatingActionButton bAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_reminders);
        //setContentView(R.layout.activity_ratings_recyclerview);

        Intent intent = getIntent();

        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(reminderHelperAdapter);
        reminders = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Reminders").child("Other");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    Reminders data = ds.getValue(Reminders.class);
                    reminders.add(data);
                    reminderHelperAdapter = new ReminderHelperAdapter(reminders);
                    recyclerView.setAdapter(reminderHelperAdapter);
                }
                reminderHelperAdapter = new ReminderHelperAdapter(reminders);
                recyclerView.setAdapter(reminderHelperAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bPay = (Button) findViewById(R.id.pay);
        bBday = (Button) findViewById(R.id.bday);
        bOther = (Button) findViewById(R.id.other);
        bAdd = (FloatingActionButton) findViewById(R.id.floatCall);

        bPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtherReminders.this, MyReminders.class));
            }
        });

        bBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtherReminders.this, BirthdayReminders.class));
            }
        });

        bOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((new Intent(OtherReminders.this, OtherReminders.class)));
            }
        });

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((new Intent(OtherReminders.this, AddReminder.class)));
            }
        });
    }
}