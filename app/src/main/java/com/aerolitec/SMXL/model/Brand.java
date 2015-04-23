package com.aerolitec.SMXL.model;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by stephaneL on 20/03/14.
 */
public class Brand implements Serializable {
    int id;
    String brand;

    public Brand() {}

    @Override
    public boolean equals(Object o) {
        if(o instanceof Brand){
            if( ((Brand) o).getBrand().equals(brand)){
                //Log.d("BRAND O IDENTIQUE", "");
                return true;
            }
        }
        return super.equals(o);
    }

    public Brand(int id, String brand){
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
        return "Brand{" +
                "id=" + id +
                ", Brand='" + brand + '\'' +
                '}';
    }
}
