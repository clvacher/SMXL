package com.aerolitec.SMXL.model;

import com.github.leonardoxh.fakesearchview.SearchItem;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by Jerome on 03/07/2015.
 */
public class ShopOnLine implements Serializable, Comparable, SearchItem {


    int id_shoponline;
    String shoponline_name;
    String shoponline_website;

    public ShopOnLine(){}

    public ShopOnLine(String shoponline_name, String shoponline_website, int id_shoponline) {
        this.shoponline_name = shoponline_name;
        this.shoponline_website = shoponline_website;
        this.id_shoponline = id_shoponline;
    }


    @Override
    public int compareTo(Object another) {
        return getShoponline_name().compareTo(((ShopOnLine)another).getShoponline_name());
    }

    @Override
    public boolean match(CharSequence charSequence) {
        return shoponline_name.toLowerCase(Locale.FRANCE).startsWith(charSequence.toString().toLowerCase(Locale.FRANCE));
    }


    @Override
    public String toString() {
        return "ShopOnLine{" +
                "id_shoponline=" + id_shoponline +
                ", shoponline_name='" + shoponline_name + '\'' +
                ", shoponline_website='" + shoponline_website + '\'' +
                '}';
    }

    public int getId_shoponline() {
        return id_shoponline;
    }

    public void setId_shoponline(int id_shoponline) {
        this.id_shoponline = id_shoponline;
    }

    public String getShoponline_name() {
        return shoponline_name;
    }

    public void setShoponline_name(String shoponline_name) {
        this.shoponline_name = shoponline_name;
    }

    public String getShoponline_website() {
        return shoponline_website;
    }

    public void setShoponline_website(String shoponline_website) {
        this.shoponline_website = shoponline_website;
    }
}
