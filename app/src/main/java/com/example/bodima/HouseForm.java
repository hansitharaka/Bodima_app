package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class HouseForm extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText houseTitle, houseCity, houseSize, houseLand, houseDescription, houseAmount, beds_num, baths_num, name, phone;
    private Button addImg, btnSave;
    private ImageView img1;
    private TextView alert;
    //image array

    private Uri imgUri;
    private LinearLayout imgLayout;
    private ProgressBar progBar;
    private int upload_count = 0;

    //    long maxid=0;
    House house;
    //    private ProgressDialog mdialog;
    FirebaseDatabase rootNode;
    private DatabaseReference mDatabase;
    private StorageReference storageRef;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_form);

        storageRef = FirebaseStorage.getInstance().getReference("AdImages");

        houseTitle = findViewById(R.id.houseTitle);
        houseCity = findViewById(R.id.houseCity);
        houseSize = findViewById(R.id.houseSize);
        houseLand = findViewById(R.id.houseLand);
        houseDescription = findViewById(R.id.houseDescription);
        houseAmount = findViewById(R.id.houseAmount);
        beds_num = findViewById(R.id.beds_num);
        beds_num = findViewById(R.id.beds_num);
        baths_num = findViewById(R.id.baths_num);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);

        imgLayout = (LinearLayout) findViewById(R.id.imagesLayout);
        img1 = (ImageView) findViewById(R.id.img);

        //buttons
        addImg = findViewById(R.id.addImg);
        btnSave = findViewById(R.id.btnSave);
        progBar=findViewById(R.id.progressBar);

        //progressDialog.setMessage("Image Uploading please wait...");

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChoose();
            }
        });

        house = new House();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Houses");


        //save data in firebase on btn click
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddHouse();
            }
        });

    }

    //validation
    public boolean isValid() {
        String hTitle = houseTitle.getText().toString().trim();
        String hCity = houseCity.getText().toString().trim();
        String hAmount = houseAmount.getText().toString().trim();
        String hBeds = beds_num.getText().toString().trim();
        String hBaths = baths_num.getText().toString().trim();
        String hSize = houseSize.getText().toString().trim();
        String hLand = houseLand.getText().toString().trim();
        String hDes = houseDescription.getText().toString().trim();
        String Sname = name.getText().toString().trim();
        String Sphone = phone.getText().toString().trim();

        if (TextUtils.isEmpty(hTitle)) {
            houseTitle.setError("This field cannot be empty");
            houseTitle.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(hCity)) {
            houseCity.setError("This field cannot be empty");
            houseCity.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(hDes)) {
            houseDescription.setError("This field cannot be empty");
            houseDescription.requestFocus();
            return false;

        } else if (hDes.length() < 10) {
            houseDescription.setError("Description should have at least 10 characters");
            houseDescription.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(hAmount)) {
            houseAmount.setError("This field cannot be empty");
            houseAmount.requestFocus();
            return false;

        } else if (Integer.parseInt(hAmount) < 1000) {
            houseAmount.setError("Amount should be at least Rs.1000");
            houseAmount.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(hBeds)) {
            beds_num.setError("This field cannot be empty");
            beds_num.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(hBaths)) {
            baths_num.setError("This field cannot be empty");
            baths_num.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(Sphone)) {
            phone.setError("This field cannot be empty");
            phone.requestFocus();
            return false;

        } else if (Sphone.length() < 10) {
            phone.setError("Phone number should have 10 digits");
            phone.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(hSize)) {
            houseSize.setError("This field cannot be empty");
            houseSize.requestFocus();
            return false;

        } else if (Integer.parseInt(hSize) < 500) {
            houseAmount.setError("Please enter the Size Correctly");
            houseAmount.requestFocus();
            return false;

        }
        else if (TextUtils.isEmpty(hLand)) {
            houseLand.setError("This field cannot be empty");
            houseLand.requestFocus();
            return false;

        } else if (Integer.parseInt(hSize) < 0 ) {
            houseAmount.setError("Please enter a valid LandSize");
            houseAmount.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(Sname)) {
            name.setError("This field cannot be empty");
            name.requestFocus();
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

                                    house.setTitle(houseTitle.getText().toString());
                                    house.setCity(houseCity.getText().toString());
                                    house.setSize(houseSize.getText().toString());
                                    house.setLandSize(houseLand.getText().toString());
                                    house.setDes(houseDescription.getText().toString());
                                    house.setAmount(houseAmount.getText().toString());
                                    house.setBeds(beds_num.getText().toString());
                                    house.setBaths(baths_num.getText().toString());
                                    house.setName(name.getText().toString());
                                    house.setPhone(phone.getText().toString());
                                    house.setImgUrl(String.valueOf(uri));
                                    // mDatabase.push().setValue(house);

                                    mDatabase.push().setValue(house).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                            Toast.makeText(HouseForm.this, "House Data inserted successfully!!", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(HouseForm.this, AllAdvertisements.class));
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

            imgUri=data.getData(); //TODO:not working image display

             imgLayout.setVisibility(View.VISIBLE);
             img1.setImageURI(imgUri);

            Toast.makeText(this, " 1 Image Selected",Toast.LENGTH_SHORT).show();

    }
        else{
                    Toast.makeText(this, "Please Select an image",Toast.LENGTH_SHORT).show();
                }

            }

}

