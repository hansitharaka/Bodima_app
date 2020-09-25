package com.example.bodima;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.bodima.R.id.signin;
import static com.example.bodima.R.id.signup;

public class Register extends AppCompatActivity {

    private Button Login;
    private Button Register;

    private EditText Email;
    private EditText Pwd;
    private Button Reg;

    String Emailin;
    String Pwdin;

    private ProgressDialog mdialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();


        Email = findViewById(R.id.usermail);
        Pwd = findViewById(R.id.userpsw);
        Reg = findViewById(R.id.btnReg);

        Login=findViewById(signin);
        Register=findViewById(signup);


        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                 Emailin = Email.getText().toString();
                 Pwdin = Pwd.getText().toString();


                if (Emailin.isEmpty()) {
                    Email.setError("Enter email");
                    Email.requestFocus();
                } else if (Pwdin.isEmpty()) {
                    Pwd.setError("enter pwd");
                    Pwd.requestFocus();
                } else if (Emailin.isEmpty() && Pwdin.isEmpty()) {
                    Toast.makeText(Register.this, "fields empty", Toast.LENGTH_SHORT).show();

                } else if (!(Emailin.isEmpty() && Pwdin.isEmpty())) {

                    firebaseAuth.createUserWithEmailAndPassword(Emailin, Pwdin)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(Register.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {

                                        Toast.makeText(Register.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();

                                    } else {

                                        startActivity(new Intent(Register.this, MyPlaces.class));
                                        finish();

                                    }
                                }
                            });
                } else {
                    Toast.makeText(Register.this, "Error", Toast.LENGTH_SHORT).show();
                }


            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);



            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Register.class);
                startActivity(intent);



            }
        });







    }
}