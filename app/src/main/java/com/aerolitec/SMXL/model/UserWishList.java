package com.aerolitec.SMXL.model;

import com.aerolitec.SMXL.ui.SMXL;

import java.util.ArrayList;

/**
 * Created by Nelson on 13/08/2015.
 */
public class UserWishList {
    int id_user_wishlist;
    User user;
    String countrySelected;
    String size;
    String picture;
    GarmentType garmentType;
    Brand brand;

    public UserWishList(int id_user_wishlist, User user, String countrySelected,String size ,String picture, GarmentType garmentType, Brand brand) {
        this.id_user_wishlist = id_user_wishlist;
        this.user = user;
        this.countrySelected = countrySelected;
        this.size = size;
        this.picture = picture;
        this.garmentType = garmentType;
        this.brand = brand;
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

    public int getId_user_wishlist() {
        return id_user_wishlist;
    }

    public void setId_user_wishlist(int id_user_wishlist) {
        this.id_user_wishlist = id_user_wishlist;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCountrySelected() {
        return countrySelected;
    }

    public void setCountrySelected(String countrySelected) {
        this.countrySelected = countrySelected;
    }
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
