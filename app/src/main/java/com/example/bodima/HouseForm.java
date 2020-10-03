package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bodima.Model.House;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import java.util.ArrayList;
import java.util.List;

public class HouseForm extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText Title, City, HouseSize, LandSize, Description, Amount, BedsNo, BathsNo, Name, Phone;
    private Button addImg, btnSave;
    private ImageView img1;
    private FirebaseUser currentUser;

    //image array

    private Uri imgUri;
    private LinearLayout imgLayout;
    private ProgressBar progBar;
    private int upload_count = 0;
    //key variable
    private String key;

    List<House> houseArrayList = new ArrayList<>();

    FirebaseDatabase rootNode;
    private DatabaseReference mDatabase;
    private StorageReference storageRef;
    private StorageTask uploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_form);

        storageRef = FirebaseStorage.getInstance().getReference("AdImages");

        Title = findViewById(R.id.houseTitle);
        City = findViewById(R.id.houseCity);
        HouseSize = findViewById(R.id.houseSize);
        LandSize = findViewById(R.id.houseLand);
        Description = findViewById(R.id.houseDescription);
        Amount = findViewById(R.id.houseAmount);
        BedsNo = findViewById(R.id.beds_num);
        BathsNo = findViewById(R.id.baths_num);
        Name = findViewById(R.id.name);
        Phone = findViewById(R.id.phone);

        imgLayout = (LinearLayout) findViewById(R.id.imagesLayout);
        img1 = (ImageView) findViewById(R.id.img);

        //buttons
        addImg = findViewById(R.id.addImg);
        btnSave = findViewById(R.id.btnSave);
        progBar = findViewById(R.id.progressBar);

        //get key
        key = getIntent().getStringExtra("key");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Houses");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

    //if not null change to update
        if (key != null) {
            GetDataFromFirebase(key);
            btnSave.setText("Update");

            AddHouse();
        }
        //save data in firebase on btn click
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddHouse();
            }
        });

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChoose();
            }
        });

    }


    //validation
    public boolean isValid() {
        String hTitle = Title.getText().toString().trim();
        String hCity = City.getText().toString().trim();
        String hAmount = Amount.getText().toString().trim();
        String hBeds = BedsNo.getText().toString().trim();
        String hBaths = BathsNo.getText().toString().trim();
        String hSize = HouseSize.getText().toString().trim();
        String hLand = LandSize.getText().toString().trim();
        String hDes = Description.getText().toString().trim();
        String Sname = Name.getText().toString().trim();
        String Sphone = Phone.getText().toString().trim();

        if (TextUtils.isEmpty(hTitle)) {
            Title.setError("This field cannot be empty");
            Title.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(hCity)) {
            City.setError("This field cannot be empty");
            City.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(hDes)) {
            Description.setError("This field cannot be empty");
            Description.requestFocus();
            return false;

        } else if (hDes.length() < 10) {
            Description.setError("Description should have at least 10 characters");
            Description.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(hAmount)) {
            Amount.setError("This field cannot be empty");
            Amount.requestFocus();
            return false;

        } else if (Integer.parseInt(hAmount) < 1000) {
            Amount.setError("Amount should be at least Rs.1000");
            Amount.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(hBeds)) {
            BedsNo.setError("This field cannot be empty");
            BedsNo.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(hBaths)) {
            BathsNo.setError("This field cannot be empty");
            BathsNo.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(Sphone)) {
            Phone.setError("This field cannot be empty");
            Phone.requestFocus();
            return false;

        } else if (Sphone.length() < 10) {
            Phone.setError("Phone number should have 10 digits");
            Phone.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(hSize)) {
            HouseSize.setError("This field cannot be empty");
            HouseSize.requestFocus();
            return false;

        } else if (Integer.parseInt(hAmount) < 500) {
            Amount.setError("Please enter the Size Correctly");
            Amount.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(hLand)) {
            LandSize.setError("This field cannot be empty");
            LandSize.requestFocus();
            return false;

        } else if (Integer.parseInt(hLand) < 0) {
            LandSize.setError("Please enter a valid LandSize");
            LandSize.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(Sname)) {
            Name.setError("This field cannot be empty");
            Name.requestFocus();
            return false;
        } else {

            return true;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    //image upload
    private void imageChoose() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    //get image extension
    private String getExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    //insert data
    private void AddHouse() {

        if (isValid()) {

            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(HouseForm.this, "Upload in progress", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(HouseForm.this, "No images selected", Toast.LENGTH_SHORT).show();
                                    }

                                    House house = new House();
                                    house.setTitle(Title.getText().toString());
                                    house.setCity(City.getText().toString());
                                    house.setSize(HouseSize.getText().toString());
                                    house.setLandSize(LandSize.getText().toString());
                                    house.setDes(Description.getText().toString());
                                    house.setAmount(Amount.getText().toString());
                                    house.setBeds(BedsNo.getText().toString());
                                    house.setBaths(BathsNo.getText().toString());
                                    house.setName(Name.getText().toString());
                                    house.setPhone(Phone.getText().toString());
                                    house.setImgUrl(String.valueOf(uri));
                                    // mDatabase.push().setValue(house);

                                    if (key != null) {
                                        mDatabase.push().child(key).setValue(house).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(HouseForm.this, "House Data has been updated!!", Toast.LENGTH_LONG).show();

                                                startActivity(new Intent(HouseForm.this, AllAdvertisements.class));
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(HouseForm.this, "Error Occured: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else if (key == null) {
                                        mDatabase.push().setValue(house).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(HouseForm.this, "House Data inserted successfully!!", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(HouseForm.this, AllAdvertisements.class));
                                            }
                                        });
                                    }
                                }
                            });
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(HouseForm.this, "Error Occured: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            progBar.setProgress((int) progress);
                        }
                    });

                } else {
                    Toast.makeText(HouseForm.this, "No images selected", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imgUri = data.getData(); //TODO:not working image display

            imgLayout.setVisibility(View.VISIBLE);
            img1.setImageURI(imgUri);

            Toast.makeText(this, " 1 Image Selected", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Please Select an image", Toast.LENGTH_SHORT).show();
        }

    }


    private void GetDataFromFirebase(String mKey) {

        mDatabase.child(mKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                House house = snapshot.getValue(House.class);

                if (house != null) {
                    Title.setText(house.getTitle());
                    City.setText(house.getCity());
                    HouseSize.setText(house.getSize());
                    LandSize.setText(house.getLandSize());
                    Description.setText(house.getDes());
                    Amount.setText(house.getAmount());
                    BedsNo.setText(house.getBeds());
                    BathsNo.setText(house.getBaths());
                    Name.setText(house.getName());
                    Phone.setText(house.getPhone());
                    img1.setImageURI(Uri.parse(house.getImgUrl()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

