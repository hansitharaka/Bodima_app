package com.example.bodima;

import com.google.firebase.database.Exclude;

public class Land {
    private String Title;
    private String City;
    private String LandSize;
    private String Des;
    private String Amount;
    private String Name;
    private String Phone;
    private String ImgUrl;
    private String mKey;
    private String uId;

    public Land() {
    }

    public Land(String title, String city, String landSize, String des, String amount, String name, String phone, String imgUrl, String mKey, String uId) {
        Title = title;
        City = city;
        LandSize = landSize;
        Des = des;
        Amount = amount;
        Name = name;
        Phone = phone;
        ImgUrl = imgUrl;
        this.mKey = mKey;
        this.uId = uId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getLandSize() {
        return LandSize;
    }

    public void setLandSize(String landSize) {
        LandSize = landSize;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Exclude
    public String getmKey() {

        return mKey;
    }
    @Exclude
    public void setmKey(String mKey)
    {
        this.mKey = mKey;
    }
}
