package com.aerolitec.SMXL.model;

import java.util.ArrayList;

/**
 * Created by stephaneL on 20/03/14.
 */
public class UserClothes extends BaseObjects{

    int clotheid;
    int userid;
    String type;
    String brand;
    String country;
    String size;
    String comment;
    ArrayList<TabSizes> sizes;
    private CategoryGarment category;

    public UserClothes() {}

    public UserClothes(int clotheid, int userid, String type, String brand, String country, String size, String comment, ArrayList<TabSizes> sizes, CategoryGarment category){
        this.clotheid = clotheid;
        this.userid = userid;
        this.type = type;
        this.brand = brand;
        this.country = country;
        this.size = size;
        this.sizes = sizes;
        if (comment.length()>1000)
            this.comment = comment.substring(0,1000);
        else
            this.comment = comment;
        this.category = category;
    }




    public int getClotheid() {
        return clotheid;
    }

    public void setClotheid(int clotheid) {
        this.clotheid = clotheid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
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
        return type + " + " + brand;
    }

    public void setCategory(CategoryGarment category) {
        this.category = category;
    }

    public CategoryGarment getCategory() {
        return category;
    }
}
