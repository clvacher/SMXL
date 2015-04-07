package com.aerolitec.SMXL.model;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by stephaneL on 12/06/2014.
 */
public class TabSizes implements Serializable {
    String pays;
    String valeur;

    public TabSizes() {
    }

    public TabSizes(String pays, String valeur) {
        this.pays = pays;
        this.valeur = valeur;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
