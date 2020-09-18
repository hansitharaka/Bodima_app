package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class AddReminder extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

/* * SPINNER CATEGORY * */

        Spinner spinnerCategory = (Spinner) findViewById(R.id.pickCategory);

        // Created ArrayAdapter with custom spinner layout
        ArrayAdapter adapterCategory = ArrayAdapter.createFromResource(this, R.array.category_array, R.layout.custom_spinner);

        // Specify the custom layout to use when the list of choices appears
        adapterCategory.setDropDownViewResource(R.layout.custom_spinner_item);

        // Apply the adapter to the spinner
        spinnerCategory.setAdapter(adapterCategory);
        spinnerCategory.setOnItemSelectedListener(this);

/* * END OF SPINNER CATEGORY * */



/* * SPINNER MONTH * */

        Spinner spinnerMonth = (Spinner) findViewById(R.id.pickMonth);

        // Created ArrayAdapter with custom spinner layout
        ArrayAdapter adapterMonth = ArrayAdapter.createFromResource(this, R.array.month_array, R.layout.custom_spinner);

        // Specify the custom layout to use when the list of choices appears
        adapterMonth.setDropDownViewResource(R.layout.custom_spinner_item);

        // Apply the adapter to the spinner
        spinnerMonth.setAdapter(adapterMonth);
        spinnerMonth.setOnItemSelectedListener(this);

 /* * END OF SPINNER MONTH * */



/* * SPINNER DAY * */

        Spinner spinnerDay = (Spinner) findViewById(R.id.pickDay);

        // Created ArrayAdapter with custom spinner layout
        ArrayAdapter adapterDay = ArrayAdapter.createFromResource(this, R.array.day_array, R.layout.custom_spinner);

        // Specify the custom layout to use when the list of choices appears
        adapterDay.setDropDownViewResource(R.layout.custom_spinner_item);

        // Apply the adapter to the spinner
        spinnerDay.setAdapter(adapterDay);
        spinnerDay.setOnItemSelectedListener(this);

/* * END OF SPINNER MONTH * */





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