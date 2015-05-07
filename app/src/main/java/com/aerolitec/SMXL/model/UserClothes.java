package com.aerolitec.SMXL.model;

import java.util.ArrayList;

public class UserClothes extends BaseObjects{

    int id_user_clothes;
    User user;
    GarmentType garmentType;
    Brand brand;
    String country;
    String size;
    String comment;
    ArrayList<TabSizes> sizes;

    public UserClothes() {}

    public UserClothes(int id_user_clothes, User user, GarmentType garmentType, Brand brand, String country, String size, String comment, ArrayList<TabSizes> sizes){
        this.id_user_clothes = id_user_clothes;
        this.user = user;
        this.garmentType = garmentType;
        this.brand = brand;
        this.country = country;
        this.size = size;
        this.sizes = sizes;
        if (comment.length()>1000)
            this.comment = comment.substring(0,1000);
        else
            this.comment = comment;
    }



    public int getId_user_clothes() {
        return id_user_clothes;
    }

    public void setId_user_clothes(int id_user_clothes) {
        this.id_user_clothes = id_user_clothes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GarmentType getGarmentType() {
        return garmentType;
    }

    public void setGarmentType(GarmentType garmentType) {
        this.garmentType = garmentType;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ArrayList<TabSizes> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<TabSizes> sizes) {
        this.sizes = sizes;
    }

    @Override
    public String toString() {
        return garmentType + " + " + brand;
    }
}
