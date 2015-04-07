package com.aerolitec.SMXL.model;

/**
 * Created by gautierragueneau on 04/06/2014.
 */
public class ColorPicker {

    public int title;
    //public int iconRes;
    public int _id;
    public int colorSquare;
    //public int counter;
    public boolean isHeader;

    public ColorPicker(int title, int colorSquare,boolean header, int id) {
        this.title = title;
        this.colorSquare=colorSquare;
        this.isHeader=header;
        this._id=id;
        //this.counter=counter;
    }


    public ColorPicker(int title, int colorSquare,int id) {
        this(title,colorSquare,false,id);
    }


}
