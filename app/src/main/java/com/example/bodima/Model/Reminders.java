package com.example.bodima.Model;

import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.Exclude;

public class Reminders {

    String category;
    String month;
    String day;
    String amount;
    String description;
    String key;

    public Reminders() {
    }

    public Reminders(String category, String month, String day, String amount, String description) {
        this.category = category;
        this.month = month;
        this.day = day;
        this.amount = amount;
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
