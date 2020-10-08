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
import android.widget.TextView;
import android.widget.Toast;

import com.example.bodima.Model.Place;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AddPlaceForm extends AppCompatActivity {

    //variables
    private Toolbar toolbar;

    private TextView username, date;
    private EditText title, desc, amount, nBeds, nBaths, phone, city, address;
    private Button addImg, btnSave;
    private ImageView img1;
    private ProgressBar progBar;
    private LinearLayout imgLayout;
    private String pUid, pUsername, pDate, pTitle, pDesc, pAmount, pBaths, pBeds, pPhone, pCity, pAddress;
    private Uri mImgUri;
    private int upload_count = 0;
    private String key;

    List<Place> placeArrayList = new ArrayList<>();

    private static final int PICK_IMAGE_REQUEST = 1;

    DatabaseReference mReff;
    StorageReference storageRef;
    FirebaseUser currentUser;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place_form);

        /* TOOLBAR */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("Add a Place");

        //Database
        mReff = FirebaseDatabase.getInstance().getReference().child("Places");
        storageRef = FirebaseStorage.getInstance().getReference("Images");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //initialize
        username = (TextView) findViewById(R.id.username);
        date = (TextView) findViewById(R.id.date);

        title = (EditText) findViewById(R.id.title);
        desc = (EditText) findViewById(R.id.description);
        amount = (EditText) findViewById(R.id.amount);
        nBaths = (EditText) findViewById(R.id.baths_num);
        nBeds = (EditText) findViewById(R.id.beds_num);
        phone = (EditText) findViewById(R.id.phone);
        city = (EditText) findViewById(R.id.city);
        address = (EditText) findViewById(R.id.address);
        progBar = (ProgressBar) findViewById(R.id.progressBar);

        imgLayout = (LinearLayout) findViewById(R.id.imagesLayout);
        img1 = (ImageView) findViewById(R.id.imgV1);

        addImg = (Button) findViewById(R.id.addImg);
        btnSave = (Button) findViewById(R.id.btnSave);


        //Loading current user username and current date to the form
        pUsername = "Thomas Jeff";
        username.setText(pUsername);
        //TODO: change the dummy pUsername; it should be received by the user profile

        date.setText(DateFormat.getDateInstance().format(new Date()));


        key = getIntent().getStringExtra("key");

        if (key != null) {
            GetDataFromFirebase(key);
            btnSave.setText("Update");

            AddPlace();
        }

        //add images button
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChoose();
            }
        });

        //submit
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPlace();
            }
        });


    }
  
    private boolean isValid() {
        pUid = "Ghgsc52s1f1S";
        pDate = DateFormat.getDateInstance().format(new Date());
        pTitle = title.getText().toString().trim();
        pDesc = desc.getText().toString().trim();
        pAmount = amount.getText().toString().trim();
        pBaths = nBaths.getText().toString().trim();
        pBeds = nBeds.getText().toString().trim();
        pPhone = phone.getText().toString().trim();
        pCity = city.getText().toString().trim();
        pAddress = address.getText().toString().trim();


        //validate
        if (TextUtils.isEmpty(pTitle)) {
            title.setError("This field cannot be empty");
            title.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(pDesc)) {
            desc.setError("This field cannot be empty");
            desc.requestFocus();
            return false;

        } else if (pDesc.length() < 10) {
            desc.setError("Description should have at least 10 characters");
            desc.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(pAmount)) {
            amount.setError("This field cannot be empty");
            amount.requestFocus();
            return false;

        } else if (Integer.parseInt(pAmount) < 1000) {
            amount.setError("Amount should be at least Rs.1000");
            amount.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(pBeds)) {
            nBeds.setError("This field cannot be empty");
            nBeds.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(pBaths)) {
            nBaths.setError("This field cannot be empty");
            nBaths.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(pPhone)) {
            phone.setError("This field cannot be empty");
            phone.requestFocus();
            return false;

        } else if (pPhone.length() < 10) {
            phone.setError("Phone number should have 10 digits");
            phone.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(pCity)) {
            city.setError("This field cannot be empty");
            city.requestFocus();
            return false;

        } else if (TextUtils.isEmpty(pAddress)) {
            address.setError("This field cannot be empty");
            address.requestFocus();
            return false;

        } else {
            return true;
        }
    }


    //img upload method
    private void FileChoose() {
        // choose files
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");                                      //only see images
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImgUri = data.getData();

            //load the image to the view
            imgLayout.setVisibility(View.VISIBLE);
            img1.setImageURI(mImgUri);

        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    //Insert data method
    private void AddPlace() {

        if (isValid()) {
            //calling images upload
            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(AddPlaceForm.this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {

                if (mImgUri != null) {
                    //giving unique file name and extension to save
                    final StorageReference sReff = storageRef.child(System.currentTimeMillis() + upload_count + "." + getExtension(mImgUri));

                    sReff.putFile(mImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            sReff.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    if(uri == null) {
                                        Toast.makeText(AddPlaceForm.this, "No images selected", Toast.LENGTH_SHORT).show();
                                    }

                                    //Sending all data to firebase
                                    HashMap<String, Object> myPlaces = new HashMap<String, Object>();
                                    myPlaces.put("uid", pUid);
                                    myPlaces.put("username", pUsername);
                                    myPlaces.put("title", pTitle);
                                    myPlaces.put("desc", pDesc);
                                    myPlaces.put("city", pCity);
                                    myPlaces.put("address", pAddress);
                                    myPlaces.put("date", pDate);
                                    myPlaces.put("beds", pBeds);
                                    myPlaces.put("baths", pBaths);
                                    myPlaces.put("phone", pPhone);
                                    myPlaces.put("amount", pAmount);
                                    myPlaces.put("imgUrl", String.valueOf(uri));

                                    if (key != null) {
                                        mReff.child(key).setValue(myPlaces).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(AddPlaceForm.this, "Data has been updated", Toast.LENGTH_SHORT).show();
                                                //go to home page
                                                startActivity(new Intent(AddPlaceForm.this, MyPlaces.class));
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(AddPlaceForm.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else if (key == null) {
                                        mReff.push().setValue(myPlaces).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(AddPlaceForm.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                                                //go to home page
                                                startActivity(new Intent(AddPlaceForm.this, MyPlaces.class));
                                            }
                                        });
                                    }

                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddPlaceForm.this, "Error Occured: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            //update progress bar
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            progBar.setProgress((int) progress);
                        }
                    });

                } else {
                    Toast.makeText(AddPlaceForm.this, "No images selected", Toast.LENGTH_SHORT).show();
                }

            }
            //TODO: user uid or phone number must be used instead of username as child(pUsername)


        }}

  private void GetDataFromFirebase(String mKey) {

        mReff.child(mKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Place place = snapshot.getValue(Place.class);
                username.setText(place.getUsername());
                date.setText(place.getDate());
                title.setText(place.getTitle());
                desc.setText(place.getDesc());
                amount.setText(place.getAmount());
                nBaths.setText(place.getBaths());
                nBeds.setText(place.getBeds());
                phone.setText(place.getPhone());
                city.setText(place.getCity());
                address.setText(place.getAddress());
                img1.setImageURI(Uri.parse(place.getImgUrl()));
            }

          @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}