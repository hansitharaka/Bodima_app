package com.example.bodima;

import com.google.firebase.database.Exclude;

public class Vehicle {
    private String Title;
    private String City;
    private String Type;
    private String Condition;
    private String Fuel;
    private String Brand;
    private String Model;
    private String Des;
    private String Amount;
    private String Name;
    private String Phone; //TODO:make phone type string in other models
    private String ImgUrl;
    private String mKey;
    private String uId;

    public Vehicle() {
    }

    public Vehicle(String title, String city, String type, String condition, String fuel, String brand, String model, String des, String amount, String name, String phone, String imgUrl, String mKey, String uId) {
        Title = title;
        City = city;
        Type = type;
        Condition = condition;
        Fuel = fuel;
        Brand = brand;
        Model = model;
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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    public String getFuel() {
        return Fuel;
    }

    public void setFuel(String fuel) {
        Fuel = fuel;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
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
