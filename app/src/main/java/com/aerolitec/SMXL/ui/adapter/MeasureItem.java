package com.aerolitec.SMXL.ui.adapter;

import java.io.Serializable;

/**
 * Created by stephaneL on 20/03/14.
 */
public class MeasureItem implements Serializable{
    //int id;
    String typeMeasure;
    Double valueMeasure;

    public MeasureItem() {}

    public MeasureItem(String typeMeasure, double valueMeasure){
//        this.id = id;
        this.typeMeasure = typeMeasure;
        this.valueMeasure = (Math.rint(valueMeasure*100))/100;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getTypeMeasure() {
        return typeMeasure;
    }

    public void setTypeMeasure(String typeMeasure) {
        this.typeMeasure = typeMeasure;
    }

    public double getValueMeasure() {
        return valueMeasure;
    }

    public void setValueMeasure(double valueMeasure) {
        this.valueMeasure = valueMeasure;
    }
}
