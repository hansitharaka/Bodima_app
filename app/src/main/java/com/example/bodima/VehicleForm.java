package com.example.bodima;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VehicleForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText vehicleTitle,vehicleCity,brand,model,vehicleDesc,vehicleAmount,name,phone;
    Spinner Type,Condition,Fuel;
    Button addImg,btnSave;
    //Vehicle vehicle;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_form);

/* * SPINNER VEHICLE TYPE * */

        final Spinner spinnerCategory = (Spinner) findViewById(R.id.pickVtype);

        // Created ArrayAdapter with custom spinner layout
        ArrayAdapter adapterCategory = ArrayAdapter.createFromResource(this, R.array.type_id, R.layout.custom_spinner);

        // Specify the custom layout to use when the list of choices appears
        adapterCategory.setDropDownViewResource(R.layout.custom_spinner_item);

        // Apply the adapter to the spinner
        spinnerCategory.setAdapter(adapterCategory);
        spinnerCategory.setOnItemSelectedListener(this);

/* * END OF SPINNER VEHICLE TYPE * */



/* * SPINNER CONDITION * */

        final Spinner spinnerCondtion = (Spinner) findViewById(R.id.pickVcondition);

        // Created ArrayAdapter with custom spinner layout
        ArrayAdapter adapterCondition= ArrayAdapter.createFromResource(this, R.array.condition_id, R.layout.custom_spinner);

        // Specify the custom layout to use when the list of choices appears
        adapterCondition.setDropDownViewResource(R.layout.custom_spinner_item);

        // Apply the adapter to the spinner
        spinnerCondtion.setAdapter(adapterCondition);
        spinnerCondtion.setOnItemSelectedListener(this);

/* * END OF SPINNER CONDITION * */



/* * SPINNER FUEL * */

        final Spinner spinnerFuel = (Spinner) findViewById(R.id.pickVfuel);

        // Created ArrayAdapter with custom spinner layout
        ArrayAdapter adapterFuel = ArrayAdapter.createFromResource(this, R.array.fuel_id, R.layout.custom_spinner);

        // Specify the custom layout to use when the list of choices appears
        adapterFuel.setDropDownViewResource(R.layout.custom_spinner_item);

        // Apply the adapter to the spinner
        spinnerFuel.setAdapter(adapterFuel);
        spinnerFuel.setOnItemSelectedListener(this);

/* * END OF SPINNER FUEL * */

        vehicleTitle=findViewById(R.id.vehicleTitle);
        vehicleCity=findViewById(R.id.vehicleCity);
        brand=findViewById(R.id.brand);
        model =findViewById(R.id.model);
        vehicleDesc=findViewById(R.id.vehicleDescription);
        vehicleAmount=findViewById(R.id.vehicleAmount);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        addImg=findViewById(R.id.addImg);
        btnSave=findViewById(R.id.btnSave);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Vehicles");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                vehicle.setTitle(vehicleTitle.getText().toString()); //TODO:Spinner null point exception check (not working)
//                vehicle.setCity(vehicleCity.getText().toString());
//                vehicle.setBrand(brand.getText().toString());
//                vehicle.setModel(model.getText().toString());
//                vehicle.setDes(vehicleDesc.getText().toString());
//                vehicle.setAmount(vehicleAmount.getText().toString());
//                vehicle.setName(name.getText().toString());
//                vehicle.setPhone(phn);
//
//                vehicle.setType(spinnerCategory.getSelectedItem().toString());
//                vehicle.setCondition(spinnerCondtion.getSelectedItem().toString());
//                vehicle.setFuel(spinnerFuel.getSelectedItem().toString());


                String vTitle = vehicleTitle.getText().toString();
                String vCity = vehicleCity.getText().toString();
                String vBrand = brand.getText().toString();
                String vModel = model.getText().toString();
                String vDesc = vehicleDesc.getText().toString();
                String vAmount = vehicleAmount.getText().toString();
                String vName = name.getText().toString();
                String vPhone = phone.getText().toString();

                String vType = spinnerCategory.getSelectedItem().toString();
                String vCondition = spinnerCondtion.getSelectedItem().toString();
                String vFuel = spinnerFuel.getSelectedItem().toString();

                Vehicle vehicle = new Vehicle(vTitle, vCity, vType, vCondition, vFuel, vBrand, vModel, vDesc, vAmount, vName, vPhone);


                mDatabase.push().setValue(vehicle);
                Toast.makeText(VehicleForm.this, "Vehicle Data inserted successfully!!", Toast.LENGTH_LONG).show();


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