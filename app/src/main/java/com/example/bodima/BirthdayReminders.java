package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bodima.Model.Reminders;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class BirthdayReminders extends AppCompatActivity implements ReminderHelperAdapter.OnItemClickListener {

    List<String> keyList;
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
        setContentView(R.layout.activity_birthday_reminders);
        //setContentView(R.layout.activity_ratings_recyclerview);

        Intent intent = getIntent();

        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(reminderHelperAdapter);
        reminders = new ArrayList<>();
        keyList = new ArrayList<>();

        reminderHelperAdapter = new ReminderHelperAdapter(reminders);
        recyclerView.setAdapter(reminderHelperAdapter);
        reminderHelperAdapter.setOnItemClickListener(BirthdayReminders.this);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
        final String month = monthName[calendar.get(Calendar.MONTH)];

        int date = calendar.get(Calendar.DATE);
        final String C_date = String.valueOf(date);

        databaseReference = FirebaseDatabase.getInstance().getReference("Reminders").child("Birthday");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for (DataSnapshot ds:snapshot.getChildren()){
                    Reminders data = ds.getValue(Reminders.class);
                    reminders.add(data);
                    keyList.add(ds.getKey());

                    if (data.getMonth().equalsIgnoreCase(month) || data.getMonth().equalsIgnoreCase("Every Month")){
                        if (data.getDay().equalsIgnoreCase(C_date)){
                            addNotification();
                        }
                    }
                }
                reminderHelperAdapter.notifyDataSetChanged();
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
                startActivity(new Intent(BirthdayReminders.this, MyReminders.class));
            }
        });

        bBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BirthdayReminders.this, BirthdayReminders.class));
            }
        });

        bOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((new Intent(BirthdayReminders.this, OtherReminders.class)));
            }
        });

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((new Intent(BirthdayReminders.this, AddReminder.class)));
            }
        });
    }

    public void addNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("You have a Birthday Reminder.")
                .setContentText("Your Friend's Birthday is today.");

        Intent notificationIntent = new Intent(this, MyReminders.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    @Override
    public void onDeleteClick(int position) {

        String key = keyList.get(position);
        databaseReference.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent = new Intent(BirthdayReminders.this, BirthdayReminders.class);
                startActivity(intent);
                Toast.makeText(BirthdayReminders.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ClearAll() {
        if (reminders != null) {
            reminders.clear();

            if (reminderHelperAdapter != null) {
                reminderHelperAdapter.notifyDataSetChanged();
            }
        } else {
            reminders = new ArrayList<>();
        }
    }
}