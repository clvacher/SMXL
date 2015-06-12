package com.aerolitec.SMXL.model;

import java.io.Serializable;

public class GarmentType implements Serializable{

    int id_garment_type;
    String type;
    String sex;
    CategoryGarment categoryGarment;

    public GarmentType() {}

    public GarmentType(int id_garment_type, String type, String sex, CategoryGarment categoryGarment){
        this.id_garment_type = id_garment_type;
        this.type = type;
        this.sex = sex;
        this.categoryGarment = categoryGarment;
    }

    public int getId_garment_type() {
        return id_garment_type;
    }

    public void setId_garment_type(int id_garment_type) {
        this.id_garment_type = id_garment_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public CategoryGarment getCategoryGarment() {
        return categoryGarment;
    }

    public void setCategoryGarment(CategoryGarment categoryGarment) {
        this.categoryGarment = categoryGarment;
    }

    @Override
    public String toString() {
        return "GarmentType{" +
                "id_garment_type=" + id_garment_type +
                ", type='" + type + '\'' +
                ", sex='" + sex + '\'' +
                ", categoryGarment=" + categoryGarment +
                '}';
    }
}
