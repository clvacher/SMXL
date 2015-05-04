package com.aerolitec.SMXL.model;

import java.io.Serializable;

/**
 * Created by stephaneL on 20/03/14.
 */
public class Brand implements Serializable {
    int id_brand;
    String brand_name;
    String brand_website;
    String brand_category;

    public Brand() {}

    @Override
    public boolean equals(Object o) {
        if(o instanceof Brand){
            if( ((Brand) o).getBrand_name().equals(brand_name)){
                return true;
            }
        }
        return super.equals(o);
    }

    public Brand(int id_brand, String brand){
        this.id_brand = id_brand;
        this.brand_name = brand;
    }

    public int getId_brand() {
        return id_brand;
    }

    public void setId_brand(int id_brand) {
        this.id_brand = id_brand;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand) {
        this.brand_name = brand;
    }

    public String getBrandWebsite() {
        return brand_website;
    }

    public void setBrandWebsite(String brand_website) {
        this.brand_website = brand_website;
    }

    public String getBrand_category() {
        return brand_category;
    }

    public void setBrand_category(String brand_category) {
        this.brand_category = brand_category;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id_brand=" + id_brand +
                ", brand_name='" + brand_name + '\'' +
                ", brand_website='" + brand_website + '\'' +
                ", brand_category='" + brand_category + '\'' +
                '}';
    }
}
