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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

public class LandForm extends AppCompatActivity {
    //variables
    private static final int PICK_IMAGE_REQUEST = 1;
    EditText landTitle, landCity, landSize, landDesc, landAmount, name, phone;
    Button addImg, btnSave;
    private ImageView img2;

    Land land;

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
        setContentView(R.layout.activity_land_form);
        storageRef = FirebaseStorage.getInstance().getReference("AdImages");

        landTitle = findViewById(R.id.title);
        landCity = findViewById(R.id.city);
        landSize = findViewById(R.id.landS);
        landDesc = findViewById(R.id.description);
        landAmount = findViewById(R.id.amount);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);

        imgLayout = (LinearLayout) findViewById(R.id.imagesLayout);
        img2 = (ImageView) findViewById(R.id.img);

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

        land = new Land();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Lands");


        //onclick save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLand();
            }
        });
    }



    public boolean isValid() {
        String lTitle = landTitle.getText().toString().trim();
        String lCity = landCity.getText().toString().trim();
        String lAmount = landAmount.getText().toString().trim();
        String lSize = landSize.getText().toString().trim();
        String lDes = landDesc.getText().toString().trim();
        String Sname = name.getText().toString().trim();
        String Sphone = phone.getText().toString().trim();

        if (TextUtils.isEmpty(lTitle)) {
            landTitle.setError("This field cannot be empty");
            landTitle.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(lCity)) {
            landCity.setError("This field cannot be empty");
            landCity.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(lDes)) {
            landDesc.setError("This field cannot be empty");
            landDesc.requestFocus();
            return false;

        } else if (lDes.length() < 20) {
            landDesc.setError("Description should have at least 10 characters");
            landDesc.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(lAmount)) {
            landAmount.setError("This field cannot be empty");
            landAmount.requestFocus();
            return false;

        } else if (Integer.parseInt(lAmount) < 1000) {
            landAmount.setError("Amount should be at least Rs.1000");
            landAmount.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(Sphone)) {
            phone.setError("This field cannot be empty");
            phone.requestFocus();
            return false;

        } else if (Sphone.length() < 10) {
            phone.setError("Phone number should have 10 digits");
            phone.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(lSize)) {
            landSize.setError("This field cannot be empty");
            landSize.requestFocus();
            return false;

        }
        else if (Integer.parseInt(lSize) <= 0) {
            landSize.setError("Please enter a valid land size");
            landSize.requestFocus();
            return false;

        }
        else if (TextUtils.isEmpty(Sname)) {
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


    private void AddLand(){
        if (isValid()) {

            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(LandForm.this, "Upload in progress", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(LandForm.this, "No images selected", Toast.LENGTH_SHORT).show();
                                    }

                                    land.setTitle(landTitle.getText().toString());
                                    land.setCity(landCity.getText().toString());
                                    land.setLandSize(landSize.getText().toString());
                                    land.setDes(landDesc.getText().toString());
                                    land.setAmount(landAmount.getText().toString());
                                    land.setName(name.getText().toString());
                                    land.setPhone(phone.getText().toString());
                                    land.setImgUrl(String.valueOf(uri));
                                    // mDatabase.push().setValue(house);

                                    mDatabase.push().setValue(land).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                            Toast.makeText(LandForm.this, "Land Data inserted successfully!!", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(LandForm.this, LandAllAds.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LandForm.this, "Error Occured: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            progBar.setProgress((int) progress);
                        }
                    });

                } else {
                    Toast.makeText(LandForm.this, "No images selected", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imgUri=data.getData();

            imgLayout.setVisibility(View.VISIBLE);
            img2.setImageURI(imgUri);

            Toast.makeText(this, " 1 Image Selected",Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(this, "Please Select an image",Toast.LENGTH_SHORT).show();
        }

    }

}

