package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    private Toolbar toolbar;

    private Button Login;
    private Button Register;

    private EditText Email;
    private EditText Pwd;
    private Button Log;

    String Emailin;
    String Pwdin;
    String CurrentUser;

    RadioButton Buyer;
    RadioButton Seller;

    private ProgressDialog mdialog;

    private FirebaseAuth firebaseAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* TOOLBAR */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("Sign In");
        toolbar.setNavigationIcon(R.drawable.arrowback);


        Login = findViewById(R.id.signin);
        Register = findViewById(R.id.signup);

        Buyer = findViewById(R.id.buyer);
        Seller = findViewById(R.id.seller);


        firebaseAuth = FirebaseAuth.getInstance();
        mdialog = new ProgressDialog(this);


        //navigate to previous page
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, WelcomPage.class));
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Login.class);
                startActivity(intent);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });


        Login();
    }


    private void Login() {

        Email = findViewById(R.id.usermail);
        Pwd = findViewById(R.id.userpsw);
        Log = findViewById(R.id.btnLogin);

        Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Emailin = Email.getText().toString().trim();
                Pwdin = Pwd.getText().toString().trim();

                if (TextUtils.isEmpty(Emailin)) {
                    Email.setError("Email Required");
                    return;
                }
                if (TextUtils.isEmpty(Pwdin)) {
                    Pwd.setError("Password Required");
                    return;
                }


                if (Seller.isChecked()) {

                    Loginfunction();
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("User");
                    reference.child(CurrentUser).child("type").setValue("seller");
                    android.util.Log.d("user", "seller");

                }

                if (Buyer.isChecked()) {

                    Loginfunction();

                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("User");
                    reference.child(CurrentUser).child("type").setValue("buyer");
                    //TODO: null pointer
                    android.util.Log.d("user", "buyer");

                }
                if (!(Seller.isChecked()) && (!(Buyer.isChecked()))) {
                    Toast.makeText(Login.this, "select user type", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void Loginfunction() {


        mdialog.setMessage("Processing....");
        mdialog.show();


        firebaseAuth.signInWithEmailAndPassword(Emailin, Pwdin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    mdialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), MyPlaces.class));
                    Toast.makeText(Login.this, "Logged in", Toast.LENGTH_SHORT).show();
                } else {
                    mdialog.dismiss();
                    Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                }


            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            CurrentUser = user.getUid();
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

        }

    }
}

