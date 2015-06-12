package com.aerolitec.SMXL.model;

import java.io.Serializable;

/**
 * Created by Jerome on 11/06/2015.
 */
public class PageTuto implements Serializable{

    String title, subtitle;
    int idIcon;


    public PageTuto(int idIcon, String title, String subtitle) {
        this.idIcon = idIcon;
        this.subtitle = subtitle;
        this.title = title;
    }


    public int getIdIcon() {
        return idIcon;
    }

    public void setIdIcon(int idIcon) {
        this.idIcon = idIcon;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
