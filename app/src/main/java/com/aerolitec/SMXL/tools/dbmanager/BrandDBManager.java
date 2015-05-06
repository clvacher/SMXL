package com.aerolitec.SMXL.tools.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.services.SQLiteSMXL;

import java.util.ArrayList;

/**
 * Created by Jerome on 28/04/2015.
 */
public class BrandDBManager extends DBManager{

    public static final String TABLE_NAME = "brand";
    public static final String KEY_ID_BRAND="id_brand";
    public static final String KEY_NOM_BRAND="brand_name";
    public static final String KEY_WEBSITE_BRAND="brand_website";
    public static final String KEY_CATEGORY_BRAND="brand_category";


    public static final String CREATE_TABLE_BRAND = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_BRAND+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+KEY_NOM_BRAND+" TEXT" +
            " "+KEY_WEBSITE_BRAND+" TEXT" +
            " "+KEY_CATEGORY_BRAND+" TEXT" +
            ");";

    // Constructeur
    public BrandDBManager(Context context)
    {
        super(context);
    }



    public long addBrand(Brand brand){
        open();
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_BRAND, brand.getBrand_name());
        values.put(KEY_CATEGORY_BRAND, brand.getBrand_category());


        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        long i = db.insert(TABLE_NAME, null, values);
        close();
        return i;
    }

    public int updateBrand(Brand brand) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête
        open();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM_BRAND, brand.getBrand_name());
        values.put(KEY_CATEGORY_BRAND, brand.getBrand_category());

        String where = KEY_ID_BRAND+" = ?";
        String[] whereArgs = {brand.getId_brand()+""};

        int i = db.update(TABLE_NAME, values, where, whereArgs);
        close();
        return i;
    }

    public int deleteBrand(Brand brand) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon
        open();
        String where = KEY_ID_BRAND+" = ?";
        String[] whereArgs = {brand.getId_brand()+""};
        int i = db.delete(TABLE_NAME, where, whereArgs);
        close();
        return i;
    }

    public Brand getBrand(int id) {
        // Retourne l'animal dont l'id est passé en paramètre

        open();
        Brand b=new Brand();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID_BRAND + "=" + id, null);
        if (c.moveToFirst()) {
            b.setId_brand(c.getInt(c.getColumnIndex(KEY_ID_BRAND)));
            b.setBrand_name(c.getString(c.getColumnIndex(KEY_NOM_BRAND)));
            b.setBrand_category(c.getString(c.getColumnIndex(KEY_CATEGORY_BRAND)));
            c.close();
        }
        close();
        return b;
    }

    /*public Cursor getBrands() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }*/

    public ArrayList<Brand> getAllBrands(){
        open();
        ArrayList<Brand> brands = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_NOM_BRAND, null);
        boolean eof = c.moveToFirst();
        while (eof) {
            Brand b = new Brand();
            b.setId_brand(c.getInt(c.getColumnIndex(KEY_ID_BRAND)));
            b.setBrand_name(c.getString(c.getColumnIndex(KEY_NOM_BRAND)));
            b.setBrand_category(c.getString(c.getColumnIndex(KEY_CATEGORY_BRAND)));
            brands.add(b);
            eof = c.moveToNext();
        }
        c.close();
        close();
        return brands;
    }

    public ArrayList<String> getAllBrandCategory() {
        open();
        ArrayList<String> categories = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT DISTINCT "+KEY_CATEGORY_BRAND+" FROM " + TABLE_NAME + " ORDER BY " + KEY_CATEGORY_BRAND, null);
        boolean eof = c.moveToFirst();
        while (eof) {
            categories.add(c.getString(c.getColumnIndex(KEY_CATEGORY_BRAND)));
            eof = c.moveToNext();
        }
        c.close();
        close();
        return categories;
    }

    public ArrayList<Brand> getBrandsByBrandCategory(String category) {
        open();
        ArrayList<Brand> brands = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+KEY_CATEGORY_BRAND+" = '"+category+"' ORDER BY " + KEY_NOM_BRAND, null);
        boolean eof = c.moveToFirst();
        while (eof) {
            Brand b = new Brand();
            b.setId_brand(c.getInt(c.getColumnIndex(KEY_ID_BRAND)));
            b.setBrand_name(c.getString(c.getColumnIndex(KEY_NOM_BRAND)));
            b.setBrand_category(c.getString(c.getColumnIndex(KEY_CATEGORY_BRAND)));
            brands.add(b);
            eof = c.moveToNext();
        }
        c.close();
        close();
        return brands;
    }
} // class BrandDBManager

