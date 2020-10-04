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

import com.example.bodima.Model.House;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class VehicleForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    EditText Title,City,brand,model,Description,Amount,name,phone;
    Spinner spinnerCategory,spinnerCondtion,spinnerFuel;
    String vType, vCondition, vFuel;
    private ImageView img3;
    Button addImg,btnSave;

    private Uri imgUri;
    private LinearLayout imgLayout;
    private ProgressBar progBar;
    private int upload_count = 0;
    private String uId;

    private DatabaseReference mDatabase;
    private StorageReference storageRef;
    private StorageTask uploadTask;

    //key variable
    private String key;

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

        Title=findViewById(R.id.vehicleTitle);
        City=findViewById(R.id.vehicleCity);
        brand=findViewById(R.id.brand);
        model =findViewById(R.id.model);
        Description=findViewById(R.id.vehicleDescription);
        Amount=findViewById(R.id.vehicleAmount);
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

        //get key
        key = getIntent().getStringExtra("key");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Vehicles");
        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //btn save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddVehicle();
            }
        });

        //if not null change to update
        if (key != null) {
            GetDataFromFirebase(key);
            btnSave.setText("Update");

            AddVehicle();
        }
    }

    //TODO:Validation
    public boolean isValid(){
      String vTitle = Title.getText().toString();
      String vCity= City.getText().toString();
      String vBrand =brand.getText().toString();
      String Vmodel=model.getText().toString();
      String vDesc=Description.getText().toString();
      String  vAmount=Amount.getText().toString();
      String Sname = name.getText().toString();
      String Sphone = phone.getText().toString();
      String vType = spinnerCategory.getSelectedItem().toString();
      String vCondition = spinnerCondtion.getSelectedItem().toString();
      String vFuel = spinnerFuel.getSelectedItem().toString();

        if (TextUtils.isEmpty(vTitle)) {
            Title.setError("This field cannot be empty");
            Title.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(vCity)) {
            City.setError("This field cannot be empty");
            City.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(vDesc)) {
            Description.setError("This field cannot be empty");
            Description.requestFocus();
            return false;

        } else if (vDesc.length() < 10) {
            Description.setError("Description should have at least 10 characters");
            Description.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(vAmount)) {
            Amount.setError("This field cannot be empty");
            Amount.requestFocus();
            return false;

        } else if (Integer.parseInt(vAmount) < 1000) {
            Amount.setError("Amount should be at least Rs.1000");
            Amount.requestFocus();
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

                                    Vehicle vehicle = new Vehicle();

                                    String vType = spinnerCategory.getSelectedItem().toString();
                                    String vCondition = spinnerCondtion.getSelectedItem().toString();
                                    String vFuel = spinnerFuel.getSelectedItem().toString();


                                    vehicle.setTitle(Title.getText().toString());
                                    vehicle.setCity(City.getText().toString());
                                    vehicle.setBrand(brand.getText().toString());
                                    vehicle.setModel(model.getText().toString());
                                    vehicle.setDes(Description.getText().toString());
                                    vehicle.setAmount(Amount.getText().toString());
                                    vehicle.setName(name.getText().toString());
                                    vehicle.setPhone(phone.getText().toString());
                                    vehicle.setType(vType);
                                    vehicle.setCondition(vCondition);
                                    vehicle.setFuel(vFuel);
                                    vehicle.setImgUrl(String.valueOf(uri));
                                    vehicle.setuId(uId);


                                    if (key != null) {
                                        mDatabase.child(key).setValue(vehicle).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(VehicleForm.this, "Vehicle Data has been updated!!", Toast.LENGTH_LONG).show();

                                                startActivity(new Intent(VehicleForm.this, VehicleAllAds.class));
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(VehicleForm.this, "Error Occured: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else if (key == null) {
                                        mDatabase.push().setValue(vehicle).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(VehicleForm.this, "Vehicle Data inserted successfully!!", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(VehicleForm.this, VehicleAllAds.class));
                                            }
                                        });
                                    }
                                }
                            });
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

    private void GetDataFromFirebase(String mKey) {

        mDatabase.child(mKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Vehicle vehicle = snapshot.getValue(Vehicle.class);

                if (vehicle != null) {
                    Title.setText(vehicle.getTitle());
                    City.setText(vehicle.getCity());
                    brand.setText(vehicle.getBrand());
                    model.setText(vehicle.getModel());
                    Description.setText(vehicle.getDes());
                    Amount.setText(vehicle.getAmount());
//                    vType.setText(vehicle.getType());
//                    vCondition.setText(vehicle.getCondition());
//                    vFuel.setText(vehicle.getFuel());
                    name.setText(vehicle.getName());
                    phone.setText(vehicle.getPhone());
                    img3.setImageURI(Uri.parse(vehicle.getImgUrl()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}


