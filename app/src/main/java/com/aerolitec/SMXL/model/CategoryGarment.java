package com.aerolitec.SMXL.model;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.aerolitec.SMXL.R;

import java.io.Serializable;


public class CategoryGarment implements Serializable {

    private int id_category_garment;
    private int icon;
    private String category_garment_name;

    public CategoryGarment(){};

    public CategoryGarment(Context context,int id_category_garment, int icon, String category_garment_name) {
        this.id_category_garment = id_category_garment;
        try {
            context.getResources().getResourceEntryName(icon);
        }
        catch(Resources.NotFoundException e) {
            Log.d("CategoryGarment","No matching picture!");
        }
        this.icon = icon;

        this.category_garment_name = category_garment_name;
    }

    public int getId_category_garment() {
        return id_category_garment;
    }

    public void setId_category_garment(int id_category_garment) {
        this.id_category_garment = id_category_garment;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getCategory_garment_name() {
        return category_garment_name;
    }

    public void setCategory_garment_name(String name_category_garment) {
        this.category_garment_name = name_category_garment;
    }
}
