package com.example.bodima.Model;

import com.google.firebase.database.Exclude;

public class Place {

    private String uid, username, title, desc, city, address, phone, date, imgUrl;
    private String beds,baths;
    private String amount;
    private String mKey;

    public Place() {
    }

    public Place(String uid, String username, String title, String desc, String city, String address, String phone, String date, String imgUrl, String beds, String baths, String amount, String mKey) {
        this.uid = uid;
        this.username = username;
        this.title = title;
        this.desc = desc;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.date = date;
        this.imgUrl = imgUrl;
        this.beds = beds;
        this.baths = baths;
        this.amount = amount;
        this.mKey = mKey;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBeds() {
        return beds;
    }

    public void setBeds(String beds) {
        this.beds = beds;
    }

    public String getBaths() {
        return baths;
    }

    public void setBaths(String baths) {
        this.baths = baths;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
