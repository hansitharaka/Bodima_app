package com.example.bodima.Model;

public class ExpenseData {

    private String amount;
    private String Type;
    private String date;
    private  String uid;

    public ExpenseData(){

    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType(String s) {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return Type;
    }

    public String getUid(String currentUser) {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
