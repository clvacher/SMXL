package com.aerolitec.SMXL.model;

import java.io.Serializable;

/**
 * Created by stephaneL on 20/03/14.
 */
public class BrandsSizeGuide implements Serializable{
    int id;
    String brand;
    String type;
    double dim1;
    double dim2;
    double dim3;
    String size;

    public BrandsSizeGuide(){}

    public BrandsSizeGuide(int id, String brand, String type, int dim1, int dim2, int dim3, String size){
        this.id = id;
        this.brand = brand;
        this.type = type;
        this.dim1 = dim1;
        this.dim2 = dim2;
        this.dim3 = dim3;
        this.size = size;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDim1() {
        return dim1;
    }

    public void setDim1(double dim1) {
        this.dim1 = dim1;
    }

    public double getDim2() {
        return dim2;
    }

    public void setDim2(double dim2) {
        this.dim2 = dim2;
    }

    public double getDim3() {
        return dim3;
    }

    public void setDim3(double dim3) {
        this.dim3 = dim3;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "BrandsSizeGuide{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", type='" + type + '\'' +
                ", dim1=" + dim1 +
                ", dim2=" + dim2 +
                ", dim3=" + dim3 +
                ", size='" + size + '\'' +
                '}';
    }
}
