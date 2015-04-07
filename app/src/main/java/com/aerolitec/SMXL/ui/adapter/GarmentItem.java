package com.aerolitec.SMXL.ui.adapter;

import java.io.Serializable;

/**
 * Created by stephaneL on 20/03/14.
 */
public class GarmentItem implements Serializable{
    int id;
    String typeGarment;
    String brand;
    String country;
    String comment;
    String size;

    public GarmentItem() {}

    public GarmentItem(int id, String typeGarment, String brand, String country, String comment, String size){
        this.id = id;
        this.typeGarment = typeGarment;
        this.brand = brand;
        this.country = country;
        this.comment = comment;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeGarment() {
        return typeGarment;
    }

    public void setTypeGarment(String typeGarment) {
        this.typeGarment = typeGarment;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
