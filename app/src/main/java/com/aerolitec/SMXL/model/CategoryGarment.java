package com.aerolitec.SMXL.model;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.aerolitec.SMXL.R;

import java.io.Serializable;

/**
 * Created by kevin on 12/06/14.
 */
public class CategoryGarment implements Serializable {

    private int id;
    private int icon;
    private String name;

    public CategoryGarment(Context context,int id, int icon, String name) {
        this.id = id;
        try {
            context.getResources().getResourceEntryName(icon);
        }
        catch(Resources.NotFoundException e) {
            Log.d("CategoryGarment","No matching picture!");
        }
        this.icon = icon;

        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
