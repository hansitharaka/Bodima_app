package com.example.bodima;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class VehicleForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_form);

/* * SPINNER VEHICLE TYPE * */

        Spinner spinnerCategory = (Spinner) findViewById(R.id.pickVtype);

        // Created ArrayAdapter with custom spinner layout
        ArrayAdapter adapterCategory = ArrayAdapter.createFromResource(this, R.array.type_id, R.layout.custom_spinner);

        // Specify the custom layout to use when the list of choices appears
        adapterCategory.setDropDownViewResource(R.layout.custom_spinner_item);

        // Apply the adapter to the spinner
        spinnerCategory.setAdapter(adapterCategory);
        spinnerCategory.setOnItemSelectedListener(this);

/* * END OF SPINNER VEHICLE TYPE * */



/* * SPINNER CONDITION * */

        Spinner spinnerMonth = (Spinner) findViewById(R.id.pickVcondition);

        // Created ArrayAdapter with custom spinner layout
        ArrayAdapter adapterMonth = ArrayAdapter.createFromResource(this, R.array.condition_id, R.layout.custom_spinner);

        // Specify the custom layout to use when the list of choices appears
        adapterMonth.setDropDownViewResource(R.layout.custom_spinner_item);

        // Apply the adapter to the spinner
        spinnerMonth.setAdapter(adapterMonth);
        spinnerMonth.setOnItemSelectedListener(this);

/* * END OF SPINNER CONDITION * */



/* * SPINNER FUEL * */

        Spinner spinnerDay = (Spinner) findViewById(R.id.pickVfuel);

        // Created ArrayAdapter with custom spinner layout
        ArrayAdapter adapterDay = ArrayAdapter.createFromResource(this, R.array.fuel_id, R.layout.custom_spinner);

        // Specify the custom layout to use when the list of choices appears
        adapterDay.setDropDownViewResource(R.layout.custom_spinner_item);

        // Apply the adapter to the spinner
        spinnerDay.setAdapter(adapterDay);
        spinnerDay.setOnItemSelectedListener(this);

/* * END OF SPINNER FUEL * */


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