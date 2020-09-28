package com.example.bodima;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class VehicleForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    EditText vehicleTitle,vehicleCity,brand,model,vehicleDesc,vehicleAmount,name,phone;
    Spinner spinnerCategory,spinnerCondtion,spinnerFuel;
    String vType, vCondition, vFuel;
    private ImageView img3;
    Button addImg,btnSave;

    Vehicle vehicle;

    private Uri imgUri;
    private LinearLayout imgLayout;
    private ProgressBar progBar;
    private int upload_count = 0;

    private DatabaseReference mDatabase;
    private StorageReference storageRef;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_form);

        storageRef = FirebaseStorage.getInstance().getReference("AdImages");

/* * SPINNER VEHICLE TYPE * */

        spinnerCategory = (Spinner) findViewById(R.id.pickVtype);

        // Created ArrayAdapter with custom spinner layout
        ArrayAdapter adapterCategory = ArrayAdapter.createFromResource(this, R.array.type_id, R.layout.custom_spinner);

        // Specify the custom layout to use when the list of choices appears
        adapterCategory.setDropDownViewResource(R.layout.custom_spinner_item);

        // Apply the adapter to the spinner
        spinnerCategory.setAdapter(adapterCategory);
        spinnerCategory.setOnItemSelectedListener(this);

/* * END OF SPINNER VEHICLE TYPE * */



/* * SPINNER CONDITION * */

        spinnerCondtion = (Spinner) findViewById(R.id.pickVcondition);

        // Created ArrayAdapter with custom spinner layout
        ArrayAdapter adapterCondition= ArrayAdapter.createFromResource(this, R.array.condition_id, R.layout.custom_spinner);

        // Specify the custom layout to use when the list of choices appears
        adapterCondition.setDropDownViewResource(R.layout.custom_spinner_item);

        // Apply the adapter to the spinner
        spinnerCondtion.setAdapter(adapterCondition);
        spinnerCondtion.setOnItemSelectedListener(this);

/* * END OF SPINNER CONDITION * */



/* * SPINNER FUEL * */

        spinnerFuel = (Spinner) findViewById(R.id.pickVfuel);

        // Created ArrayAdapter with custom spinner layout
        ArrayAdapter adapterFuel = ArrayAdapter.createFromResource(this, R.array.fuel_id, R.layout.custom_spinner);

        // Specify the custom layout to use when the list of choices appears
        adapterFuel.setDropDownViewResource(R.layout.custom_spinner_item);

        // Apply the adapter to the spinner
        spinnerFuel.setAdapter(adapterFuel);
        spinnerFuel.setOnItemSelectedListener(this);

/* * END OF SPINNER FUEL * */

        vType = spinnerCategory.getSelectedItem().toString();
        vCondition = spinnerCondtion.getSelectedItem().toString();
        vFuel = spinnerFuel.getSelectedItem().toString();

        vehicleTitle=findViewById(R.id.vehicleTitle);
        vehicleCity=findViewById(R.id.vehicleCity);
        brand=findViewById(R.id.brand);
        model =findViewById(R.id.model);
        vehicleDesc=findViewById(R.id.vehicleDescription);
        vehicleAmount=findViewById(R.id.vehicleAmount);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);

        imgLayout = (LinearLayout) findViewById(R.id.imagesLayout);
        img3 = (ImageView) findViewById(R.id.img);

        addImg=findViewById(R.id.addImg);
        btnSave=findViewById(R.id.btnSave);
        progBar=findViewById(R.id.progressBar);

        //image onclick
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChoose();
            }
        });

        vehicle = new Vehicle();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Vehicles");

        //btn save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddVehicle();
            }
        });

    }

    //TODO:Validation
    public boolean isValid(){
      String vTitle = vehicleTitle.getText().toString();
      String vCity= vehicleCity.getText().toString();
      String vBrand =brand.getText().toString();
      String Vmodel=model.getText().toString();
      String vDesc=vehicleDesc.getText().toString();
      String  vAmount=vehicleAmount.getText().toString();
      String Sname = name.getText().toString();
      String Sphone = phone.getText().toString();
      String vType = spinnerCategory.getSelectedItem().toString();
      String vCondition = spinnerCondtion.getSelectedItem().toString();
      String vFuel = spinnerFuel.getSelectedItem().toString();

        if (TextUtils.isEmpty(vTitle)) {
            vehicleTitle.setError("This field cannot be empty");
            vehicleTitle.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(vCity)) {
            vehicleCity.setError("This field cannot be empty");
            vehicleCity.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(vDesc)) {
            vehicleDesc.setError("This field cannot be empty");
            vehicleDesc.requestFocus();
            return false;

        } else if (vDesc.length() < 10) {
            vehicleDesc.setError("Description should have at least 10 characters");
            vehicleDesc.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(vAmount)) {
            vehicleAmount.setError("This field cannot be empty");
            vehicleAmount.requestFocus();
            return false;

        } else if (Integer.parseInt(vAmount) < 1000) {
            vehicleAmount.setError("Amount should be at least Rs.1000");
            vehicleAmount.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(vBrand)) {
            brand.setError("This field cannot be empty");
            brand.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(Vmodel)) {
            model.setError("This field cannot be empty");
            model.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(Sphone)) {
            phone.setError("This field cannot be empty");
            phone.requestFocus();
            return false;

        } else if (Sphone.length() < 10) {
            phone.setError("Phone number should have 10 digits");
            phone.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(Sname)) {
            name.setError("This field cannot be empty");
            name.requestFocus();
            return false;
        }
        else if (vType.equals("Select the type")){
            Toast.makeText(VehicleForm.this, "Select a type", Toast.LENGTH_SHORT).show();

        }
        else if (vCondition.equals("Select the Condition")){
            Toast.makeText(VehicleForm.this, "Select the condition", Toast.LENGTH_SHORT).show();

        }
        else if (vFuel.equals("Select Fuel Type")){
            Toast.makeText(VehicleForm.this, "Select Fuel Type", Toast.LENGTH_SHORT).show();

        }
        else {
            return true;
        }
        return false;
    }

    //image upload
    private void imageChoose() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
//      intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    //get image extension
    private String getExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //display the selection in a toast
        Toast.makeText(this,parent.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imgUri=data.getData();

            imgLayout.setVisibility(View.VISIBLE);
            img3.setImageURI(imgUri);

            Toast.makeText(this, " 1 Image Selected",Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(this, "Please Select an image",Toast.LENGTH_SHORT).show();
        }

    }

//insert vehicle
    private void AddVehicle() {

        if (isValid()) {

            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(VehicleForm.this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {

                if (imgUri != null) {

                    final StorageReference sRef = storageRef.child(System.currentTimeMillis() + upload_count + "." + getExtension(imgUri));
                    sRef.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    if (uri == null) {
                                        Toast.makeText(VehicleForm.this, "No images selected", Toast.LENGTH_SHORT).show();
                                    }

                                    String vType = spinnerCategory.getSelectedItem().toString();
                                    String vCondition = spinnerCondtion.getSelectedItem().toString();
                                    String vFuel = spinnerFuel.getSelectedItem().toString();

                                    vehicle.setTitle(vehicleTitle.getText().toString());
                                    vehicle.setCity(vehicleCity.getText().toString());
                                    vehicle.setBrand(brand.getText().toString());
                                    vehicle.setModel(model.getText().toString());
                                    vehicle.setDes(vehicleDesc.getText().toString());
                                    vehicle.setAmount(vehicleAmount.getText().toString());
                                    vehicle.setName(name.getText().toString());
                                    vehicle.setPhone(phone.getText().toString());
                                    vehicle.setType(vType);
                                    vehicle.setCondition(vCondition);
                                    vehicle.setFuel(vFuel);
                                    vehicle.setImgUrl(String.valueOf(uri));

                                    mDatabase.push().setValue(vehicle).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //if the upload is success, reset the progress bar to 0
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progBar.setProgress(0);
                                                }
                                            }, 500); //delays the progress by half seconds
                                        }
                                    });
                                }
                            });
                            Toast.makeText(VehicleForm.this, "Vehicle Data inserted successfully!!", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(VehicleForm.this, VehicleAllAds.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(VehicleForm.this, "Error Occured: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            progBar.setProgress((int) progress);
                        }
                    });

                } else {
                    Toast.makeText(VehicleForm.this, "No images selected", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}