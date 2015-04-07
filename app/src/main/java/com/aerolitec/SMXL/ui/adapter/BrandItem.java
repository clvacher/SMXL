package com.aerolitec.SMXL.ui.adapter;

import java.io.Serializable;

/**
 * Created by stephaneL on 30/03/14.
 */
public class BrandItem implements Serializable{
    //int id;
    String brandName;

    public BrandItem() {}

    public BrandItem(String brandName){
        //this.id = id;
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
