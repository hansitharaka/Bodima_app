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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MyReminders extends AppCompatActivity implements ReminderHelperAdapter.OnItemClickListener {

    List<String> keyList;
    List<Reminders> reminders;
    RecyclerView recyclerView;
    ReminderHelperAdapter reminderHelperAdapter;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    TextView description;
    TextView day;
    TextView month;
    TextView amount;

    String descriptionText;
    String dayText;
    String monthText;
    String amountText;

    Button bPay, bday, bOther;
    FloatingActionButton bAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reminders);
        //setContentView(R.layout.activity_ratings_recyclerview);

        final Intent intent = getIntent();

        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(reminderHelperAdapter);
        reminders = new ArrayList<>();
        keyList = new ArrayList<>();

        reminderHelperAdapter = new ReminderHelperAdapter(reminders);
        recyclerView.setAdapter(reminderHelperAdapter);
        reminderHelperAdapter.setOnItemClickListener(MyReminders.this);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int date = calendar.get(Calendar.DATE);

        String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};
        final String month = monthName[calendar.get(Calendar.MONTH)];

        final String C_date = String.valueOf(date);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("Reminders").child(String.valueOf(user)).child("Payment");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for (DataSnapshot ds:snapshot.getChildren()){
                    Reminders data = ds.getValue(Reminders.class);
                    keyList.add(ds.getKey());
                    reminders.add(data);

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
        bday = (Button) findViewById(R.id.bday);
        bOther = (Button) findViewById(R.id.other);
        bAdd = (FloatingActionButton) findViewById(R.id.floatCall);

        bPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyReminders.this, MyReminders.class));
            }
        });

        bday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MyReminders.this,BirthdayReminders.class);
                startActivity(intent1);
            }
        });

        bOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyReminders.this, OtherReminders.class));
            }
        });

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyReminders.this, AddReminder.class));
            }
        });

    }

    public void addNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("You have a Payment Reminder.")
                .setContentText("Payment due on today.");

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
                Intent intent = new Intent(MyReminders.this, MyReminders.class);
                startActivity(intent);
                Toast.makeText(MyReminders.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
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