package com.example.bodima.Model;


import java.util.Date;

public class Place {

    private String title, desc, city, address, phone;
    private Date date;
    private int beds,baths;
    private double amount;

    public Place() {
    }

    public Place(String title, String desc, String city, String address, Date date,
                 int beds, int baths, String phone, double amount) {
        this.title = title;
        this.desc = desc;
        this.city = city;
        this.address = address;
        this.date = date;
        this.beds = beds;
        this.baths = baths;
        this.phone = phone;
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getBaths() {
        return baths;
    }

    public void setBaths(int baths) {
        this.baths = baths;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
