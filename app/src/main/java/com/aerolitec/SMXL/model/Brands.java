package com.aerolitec.SMXL.model;

import java.io.Serializable;

/**
 * Created by stephaneL on 20/03/14.
 */
public class Brands implements Serializable {
    int id;
    String brand;

    public Brands() {}

    public Brands(int id, String brand){
        this.id = id;
        this.brand = brand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Brands{" +
                "id=" + id +
                ", Brand='" + brand + '\'' +
                '}';
    }
}
