package com.aerolitec.SMXL.tools.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jerome on 28/04/2015.
 */
public class UserBrandDBManager extends DBManager {
    public static final String TABLE_NAME = "user_brand";
    public static final String KEY_ID_USER="id_user";
    public static final String KEY_ID_BRAND="id_brand";


    public static final String CREATE_TABLE_USER_BRAND = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_USER+" INTEGER NOT NULL," +
            " "+KEY_ID_BRAND+" INTEGER NOT NULL," +
            " PRIMARY KEY("+KEY_ID_USER+","+KEY_ID_BRAND+"),"+
            " FOREIGN KEY("+KEY_ID_USER+") REFERENCES "+BrandDBManager.TABLE_NAME+"," +
            " FOREIGN KEY("+KEY_ID_BRAND+") REFERENCES "+ UserDBManager.TABLE_NAME +
            ");";

    // Constructeur
    public UserBrandDBManager(Context context)
    {
        super(context);
    }



    public long addUserBrand(User user, Brand brand){
        // Ajout d'un enregistrement dans la table
        open();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_USER, user.getId_user());
        values.put(KEY_ID_BRAND, brand.getId_brand());

        long i = db.insert(TABLE_NAME, null, values);

        close();
        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return i;
    }

    public int updateUserBrand(User user, Brand brand) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête
        open();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_USER, user.getId_user());
        values.put(KEY_ID_BRAND, brand.getId_brand());

        String where = KEY_ID_USER+" = ? AND "+KEY_ID_BRAND+" = ?";
        String[] whereArgs = {user.getId_user()+"", brand.getId_brand()+""};

        int i = db.update(TABLE_NAME, values, where, whereArgs);
        close();
        return i;
    }

    public int deleteUserBrand(User user, Brand brand) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        open();
        String where = KEY_ID_BRAND+" = ? AND "+KEY_ID_USER+" = ?";
        String[] whereArgs = {brand.getId_brand()+"", user.getId_user()+""};

        int i = db.delete(TABLE_NAME, where, whereArgs);
        close();
        return i;
    }

    public int deleteUserBrand(User user) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        open();
        String where = KEY_ID_USER+" = ?";
        String[] whereArgs = {user.getId_user()+""};

        int i = db.delete(TABLE_NAME, where, whereArgs);
        close();
        return i;
    }

    public int deleteUserBrand(Brand brand) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        open();
        String where = KEY_ID_BRAND+" = ?";
        String[] whereArgs = {brand.getId_brand()+""};

        int i =db.delete(TABLE_NAME, where, whereArgs);

        close();
        return i;
    }

/*
    public Cursor getUserBrands() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }
*/



    public boolean alreadyExistUserBrand(User user, Brand b) {
        open();
        boolean found = false;
            Cursor c;
            c = db.rawQuery("SELECT * FROM "+TABLE_NAME+
                    " WHERE "+KEY_ID_USER+"="+user.getId_user()+
                    " AND "+KEY_ID_BRAND+"="+b.getId_brand(), null);
            boolean eof = c.moveToFirst();
            found = eof;
            c.close();

        close();
        return found;
    }

    public ArrayList<Brand> getAllUserBrands(User user){
        open();
        ArrayList<Brand> brands = new ArrayList<>();
        //Log.d("getAllUserBrands User",user.toString());
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_USER+" = '"+user.getId_user()+"'", null);
        boolean eof = c.moveToFirst();
        while (eof) {
            Brand b = SMXL.getBrandDBManager().getBrand(c.getInt(c.getColumnIndex(KEY_ID_BRAND)));
            brands.add(b);
            eof = c.moveToNext();
        }
        c.close();
        close();
        //Log.d("getAllUserBrands", brands.toString());


        Collections.sort(brands);
        return brands;
    }
} // class UserBrandDBManager


