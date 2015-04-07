package com.aerolitec.SMXL.model;

import java.io.Serializable;

/**
 * Created by stephaneL on 20/03/14.
 */
public class GarmentType implements Serializable{
    int id;
    String type;

    public GarmentType() {}

    public GarmentType(int id, String type){
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "GarmentType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
