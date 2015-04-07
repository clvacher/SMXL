package com.aerolitec.SMXL.ui.adapter;

import java.io.Serializable;

/**
 * Created by stephaneL on 30/03/14.
 */
public class CountryItem implements Serializable{
    //int id;
    String countryCode;

    public CountryItem() {}

    public CountryItem(String countryCode){
        //this.id = id;
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
