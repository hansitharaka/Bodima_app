package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bodima.Model.Reminders;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddReminder extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner category;
    private Spinner month;
    private Spinner day;
    private EditText amount;
    private EditText description;

    private Button save;

    DatabaseReference database;

    String categoryText;
    String monthText;
    String dayText;
    String amountText;
    String descriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

/* * SPINNER CATEGORY * */

        final Spinner spinnerCategory = (Spinner) findViewById(R.id.pickCategory);

        // Created ArrayAdapter with custom spinner layout
        ArrayAdapter adapterCategory = ArrayAdapter.createFromResource(this, R.array.category_array, R.layout.custom_spinner);

        // Specify the custom layout to use when the list of choices appears
        adapterCategory.setDropDownViewResource(R.layout.custom_spinner_item);

        // Apply the adapter to the spinner
        spinnerCategory.setAdapter(adapterCategory);
        spinnerCategory.setOnItemSelectedListener(this);

/* * END OF SPINNER CATEGORY * */



/* * SPINNER MONTH * */

        final Spinner spinnerMonth = (Spinner) findViewById(R.id.pickMonth);

        // Created ArrayAdapter with custom spinner layout
        ArrayAdapter adapterMonth = ArrayAdapter.createFromResource(this, R.array.month_array, R.layout.custom_spinner);

        // Specify the custom layout to use when the list of choices appears
        adapterMonth.setDropDownViewResource(R.layout.custom_spinner_item);

        // Apply the adapter to the spinner
        spinnerMonth.setAdapter(adapterMonth);
        spinnerMonth.setOnItemSelectedListener(this);

 /* * END OF SPINNER MONTH * */



/* * SPINNER DAY * */

        final Spinner spinnerDay = (Spinner) findViewById(R.id.pickDay);

        // Created ArrayAdapter with custom spinner layout
        ArrayAdapter adapterDay = ArrayAdapter.createFromResource(this, R.array.day_array, R.layout.custom_spinner);

        // Specify the custom layout to use when the list of choices appears
        adapterDay.setDropDownViewResource(R.layout.custom_spinner_item);

        // Apply the adapter to the spinner
        spinnerDay.setAdapter(adapterDay);
        spinnerDay.setOnItemSelectedListener(this);

/* * END OF SPINNER MONTH * */

        amount = findViewById(R.id.amount);
        description = findViewById(R.id.description);

        database = FirebaseDatabase.getInstance().getReference().child("Reminders");

        save = findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoryText = spinnerCategory.getSelectedItem().toString();
                String monthText = spinnerMonth.getSelectedItem().toString();
                String dayText = spinnerDay.getSelectedItem().toString();
                String amountText = amount.getText().toString();
                String descriptionText = description.getText().toString();

                if (categoryText.equals("Category")){
                    Toast.makeText(AddReminder.this, "Enter Category", Toast.LENGTH_SHORT).show();
                }
                else if (dayText.equals("Day")){
                    Toast.makeText(AddReminder.this, "Enter Day", Toast.LENGTH_SHORT).show();
                }
                else if (descriptionText.isEmpty()){
                    Toast.makeText(AddReminder.this, "Enter description", Toast.LENGTH_SHORT).show();
                }
                else {
                    Reminders reminders = new Reminders(categoryText, monthText, dayText, amountText, descriptionText);
                    if(categoryText.equals("Payment")){
                        database.child("Payment").push().setValue(reminders);
                        Toast.makeText(AddReminder.this, "Successfully Added the Payment Reminder", Toast.LENGTH_SHORT).show();
                        startActivity((new Intent(AddReminder.this, MyReminders.class)));
                    }
                    else if(categoryText.equals("Birthday")){
                        database.child("Birthday").push().setValue(reminders);
                        Toast.makeText(AddReminder.this, "Successfully Added the Birthday Reminder", Toast.LENGTH_SHORT).show();
                        startActivity((new Intent(AddReminder.this, BirthdayReminders.class)));
                    }
                    else {
                        database.child("Other").push().setValue(reminders);
                        Toast.makeText(AddReminder.this, "Successfully Added the Other Reminder", Toast.LENGTH_SHORT).show();
                        startActivity((new Intent(AddReminder.this, OtherReminders.class)));
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //display the selection in a toast
        Toast.makeText(this,parent.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}