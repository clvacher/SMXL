package com.aerolitec.SMXL.ui.adapter;

import java.io.Serializable;

/**
 * Created by stephaneL on 30/03/14.
 */
public class TypeGarmentItem implements Serializable{
    //int id;
    String typeGarment;

    public TypeGarmentItem() {}

    public TypeGarmentItem(String typeGarment){
        //this.id = id;
        this.typeGarment = typeGarment;
    }

    public String getTypeGarment() {
        return typeGarment;
    }

    public void setTypeGarment(String typeGarment) {
        this.typeGarment = typeGarment;
    }
}
