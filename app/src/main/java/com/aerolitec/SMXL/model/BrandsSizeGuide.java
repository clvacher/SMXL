package com.aerolitec.SMXL.model;

import java.io.Serializable;


public class BrandsSizeGuide implements Serializable{

    int id_brand_size_guide;
    Brand brand;
    GarmentType garmentType;

    Double bust;
    Double collar;
    Double chest;
    Double feet;
    Double head;
    Double height;
    Double hips;
    Double inseam;
    Double sleeve;
    Double thigh;
    Double waist;
    Double weight;

    Double other;

    String sizeFR;
    String sizeUE;
    String sizeUK;
    String sizeUS;
    String sizeSMXL;
    String sizeSuite;


    public BrandsSizeGuide() {}

    public BrandsSizeGuide(GarmentType paramString1, Brand paramString3, Double paramDouble1, Double paramDouble2, Double paramDouble3, Double paramDouble4, Double paramDouble5, Double paramDouble6, Double paramDouble7, Double paramDouble8, Double paramDouble9, Double paramDouble10, Double paramDouble11, Double paramDouble12, Double paramDouble13, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10)
    {
        this.garmentType = paramString1;
        this.brand = paramString3;
        this.collar = paramDouble1;
        this.chest = paramDouble2;
        this.bust = paramDouble3;
        this.sleeve = paramDouble4;
        this.waist = paramDouble5;
        this.hips = paramDouble6;
        this.inseam = paramDouble7;
        this.feet = paramDouble8;
        this.head = paramDouble9;
        this.height = paramDouble10;
        this.weight = paramDouble11;
        this.thigh = paramDouble12;
        this.other = paramDouble13;
        this.sizeSMXL = paramString5;
        this.sizeUE = paramString6;
        this.sizeUS = paramString7;
        this.sizeUK = paramString8;
        this.sizeFR = paramString9;
        this.sizeSuite = paramString10;
    }

    @Override
    public String toString() {
        return "BrandsSizeGuide{" +
                "id_brand_size_guide=" + id_brand_size_guide +
                ", brand='" + brand + '\'' +
                ", garmentType='" + garmentType + '\'' +
                ", bust=" + bust +
                ", collar=" + collar +
                ", chest=" + chest +
                ", feet=" + feet +
                ", head=" + head +
                ", height=" + height +
                ", hips=" + hips +
                ", inseam=" + inseam +
                ", sleeve=" + sleeve +
                ", thigh=" + thigh +
                ", waist=" + waist +
                ", weight=" + weight +
                ", other=" + other +
                ", sizeFR='" + sizeFR + '\'' +
                ", sizeUE='" + sizeUE + '\'' +
                ", sizeUK='" + sizeUK + '\'' +
                ", sizeUS='" + sizeUS + '\'' +
                ", sizeSMXL='" + sizeSMXL + '\'' +
                ", sizeSuite='" + sizeSuite + '\'' +
                '}';
    }

    public int getId_brand_size_guide() {
        return id_brand_size_guide;
    }

    public void setId_brand_size_guide(int id_brand_size_guide) {
        this.id_brand_size_guide = id_brand_size_guide;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public GarmentType getGarmentType() {
        return garmentType;
    }

    public void setGarmentType(GarmentType garmentType) {
        this.garmentType = garmentType;
    }

    public Double getBust() {
        return bust;
    }

    public void setBust(Double bust) {
        this.bust = bust;
    }

    public Double getCollar() {
        return collar;
    }

    public void setCollar(Double collar) {
        this.collar = collar;
    }

    public Double getChest() {
        return chest;
    }

    public void setChest(Double chest) {
        this.chest = chest;
    }

    public Double getFeet() {
        return feet;
    }

    public void setFeet(Double feet) {
        this.feet = feet;
    }

    public Double getHead() {
        return head;
    }

    public void setHead(Double head) {
        this.head = head;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getHips() {
        return hips;
    }

    public void setHips(Double hips) {
        this.hips = hips;
    }

    public Double getInseam() {
        return inseam;
    }

    public void setInseam(Double inseam) {
        this.inseam = inseam;
    }

    public Double getSleeve() {
        return sleeve;
    }

    public void setSleeve(Double sleeve) {
        this.sleeve = sleeve;
    }

    public Double getThigh() {
        return thigh;
    }

    public void setThigh(Double thigh) {
        this.thigh = thigh;
    }

    public Double getWaist() {
        return waist;
    }

    public void setWaist(Double waist) {
        this.waist = waist;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getOther() {
        return other;
    }

    public void setOther(Double other) {
        this.other = other;
    }

    public String getSizeFR() {
        return sizeFR;
    }

    public void setSizeFR(String sizeFR) {
        this.sizeFR = sizeFR;
    }

    public String getSizeUE() {
        return sizeUE;
    }

    public void setSizeUE(String sizeUE) {
        this.sizeUE = sizeUE;
    }

    public String getSizeUK() {
        return sizeUK;
    }

    public void setSizeUK(String sizeUK) {
        this.sizeUK = sizeUK;
    }

    public String getSizeUS() {
        return sizeUS;
    }

    public void setSizeUS(String sizeUS) {
        this.sizeUS = sizeUS;
    }

    public String getSizeSMXL() {
        return sizeSMXL;
    }

    public void setSizeSMXL(String sizeSMXL) {
        this.sizeSMXL = sizeSMXL;
    }

    public String getSizeSuite() {
        return sizeSuite;
    }

    public void setSizeSuite(String sizeSuite) {
        this.sizeSuite = sizeSuite;
    }
}

