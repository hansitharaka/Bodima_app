package com.example.bodima.Model;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bodima.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {

    private String Name;
    private String Email;
    private String Password;
    private  String  phone;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private  String type;

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private  String id;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User(){

    }

    public User(String name, String email, String password) {
        Name = name;
        Email = email;
        Password = password;
    }

    public String getName() {

        return Name;
    }

    public void setName(String name) {

        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }



}
