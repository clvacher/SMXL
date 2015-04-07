package com.aerolitec.SMXL.model;

import java.io.Serializable;

/**
 * Created by kevin on 12/06/14.
 */
public class CategoryGarment implements Serializable {

    private int id;
    private int icon;
    private String name;

    public CategoryGarment(int id, int icon, String name) {
        this.id = id;
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
