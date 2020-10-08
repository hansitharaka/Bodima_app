package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class LandForm extends AppCompatActivity {
    //variables
    private Toolbar toolbar;

    private static final int PICK_IMAGE_REQUEST = 1;
    EditText Title, City, LandSize, Description, Amount, Name, Phone;
    Button addImg, btnSave;
    private ImageView img2;

    //Land land;

    private Uri imgUri;
    private LinearLayout imgLayout;
    private ProgressBar progBar;
    private int upload_count = 0;
    private String uId;
    //key variable

    private String key;

    private DatabaseReference mDatabase;
    private StorageReference storageRef;
    private StorageTask uploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_form);

        /* TOOLBAR */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("Post Land Ad");

        storageRef = FirebaseStorage.getInstance().getReference("AdImages");

        Title = findViewById(R.id.title);
        City = findViewById(R.id.city);
        LandSize = findViewById(R.id.landS);
        Description = findViewById(R.id.description);
        Amount = findViewById(R.id.amount);
        Name = findViewById(R.id.name);
        Phone = findViewById(R.id.phone);

        imgLayout = (LinearLayout) findViewById(R.id.imagesLayout);
        img2 = (ImageView) findViewById(R.id.img);

        //buttons
        addImg = findViewById(R.id.addImg);
        btnSave = findViewById(R.id.btnSave);
        progBar=findViewById(R.id.progressBar);

        //get key
        key = getIntent().getStringExtra("key");

        //land = new Land();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Advertisements").child("Lands");
        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //if not null change to update
        if (key != null) {
            GetDataFromFirebase(key);
            btnSave.setText("Update");

            AddLand();
        }

        //onclick save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLand();
            }
        });

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChoose();
            }
        });
    }




    public boolean isValid() {
        String lTitle = Title.getText().toString().trim();
        String lCity = City.getText().toString().trim();
        String lAmount = Amount.getText().toString().trim();
        String lSize = LandSize.getText().toString().trim();
        String lDes = Description.getText().toString().trim();
        String Sname = Name.getText().toString().trim();
        String Sphone = Phone.getText().toString().trim();

        if (TextUtils.isEmpty(lTitle)) {
            Title.setError("This field cannot be empty");
            Title.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(lCity)) {
            City.setError("This field cannot be empty");
            City.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(lDes)) {
            Description.setError("This field cannot be empty");
            Description.requestFocus();
            return false;

        } else if (lDes.length() < 20) {
            Description.setError("Description should have at least 10 characters");
            Description.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(lAmount)) {
            Amount.setError("This field cannot be empty");
            Amount.requestFocus();
            return false;

        } else if (Integer.parseInt(lAmount) < 1000) {
            Amount.setError("Amount should be at least Rs.1000");
            Amount.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(Sphone)) {
            Phone.setError("This field cannot be empty");
            Phone.requestFocus();
            return false;

        } else if (Sphone.length() < 10) {
            Phone.setError("Phone number should have 10 digits");
            Phone.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(lSize)) {
            LandSize.setError("This field cannot be empty");
            LandSize.requestFocus();
            return false;

        }
        else if (Integer.parseInt(lSize) <= 0) {
            LandSize.setError("Please enter a valid land size");
            LandSize.requestFocus();
            return false;

        }
        else if (TextUtils.isEmpty(Sname)) {
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

                                    Land land = new Land();
                                    land.setTitle(Title.getText().toString());
                                    land.setCity(City.getText().toString());
                                    land.setLandSize(LandSize.getText().toString());
                                    land.setDes(Description.getText().toString());
                                    land.setAmount(Amount.getText().toString());
                                    land.setName(Name.getText().toString());
                                    land.setPhone(Phone.getText().toString());
                                    land.setImgUrl(String.valueOf(uri));
                                    land.setuId(uId);
                                    // mDatabase.push().setValue(house);

                                    if (key != null) {
                                        mDatabase.child(key).setValue(land).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(LandForm.this, "Land Data has been updated!!", Toast.LENGTH_LONG).show();

                                                startActivity(new Intent(LandForm.this, LandAllAds.class));
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(LandForm.this, "Error Occured: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else if (key == null) {
                                        mDatabase.push().setValue(land).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(LandForm.this, "Land Data inserted successfully!!", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(LandForm.this, LandAllAds.class));
                                            }
                                        });
                                    }
                                }
                            });
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
//get data from firebase when updating
    private void GetDataFromFirebase(String mKey) {
        mDatabase.child(mKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Land land =snapshot.getValue(Land.class);
                if(land != null) {
                    Title.setText(land.getTitle());
                    City.setText(land.getCity());
                    Amount.setText(land.getAmount());
                    LandSize.setText(land.getLandSize());
                    Description.setText(land.getDes());
                    Name.setText(land.getName());
                    Phone.setText(land.getPhone());
                    img2.setImageURI(Uri.parse(land.getImgUrl()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

