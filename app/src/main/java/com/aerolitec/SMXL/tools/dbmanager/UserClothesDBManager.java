package com.aerolitec.SMXL.tools.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.TabSizes;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.model.UserClothes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Jerome on 28/04/2015.
 */
public class UserClothesDBManager extends DBManager {

    public static final String TABLE_NAME = "user_clothes";
    public static final String KEY_ID_USER_CLOTHES="id_user_clothes";
    public static final String KEY_ID_USER_USER_CLOTHES="id_user";
    public static final String KEY_ID_GARMENT_TYPE_USER_CLOTHES="id_garment_type";
    public static final String KEY_ID_BRAND_USER_CLOTHES="id_brand";
    public static final String KEY_COUNTRY_USER_CLOTHES="country";
    public static final String KEY_SIZE_USER_CLOTHES="size";
    public static final String KEY_COMMENT_USER_CLOTHES="comment";
    public static final String KEY_SIZES_USER_CLOTHES="sizes";

    public static final String CREATE_TABLE_USER_CLOTHES = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_USER_CLOTHES+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+KEY_ID_USER_USER_CLOTHES+" INTEGER NOT NULL," +
            " "+KEY_ID_GARMENT_TYPE_USER_CLOTHES+" INTEGER NOT NULL," +
            " "+KEY_ID_BRAND_USER_CLOTHES+" INTEGER NOT NULL," +
            " "+KEY_COUNTRY_USER_CLOTHES+" TEXT" +
            " "+KEY_SIZE_USER_CLOTHES+" TEXT" +
            " "+KEY_COMMENT_USER_CLOTHES+" TEXT" +
            " "+KEY_SIZES_USER_CLOTHES+" TEXT" +
            " FOREIGN KEY("+KEY_ID_USER_USER_CLOTHES+") REFERENCES "+UserDBManager.TABLE_NAME+"," +
            " FOREIGN KEY("+KEY_ID_GARMENT_TYPE_USER_CLOTHES+") REFERENCES "+GarmentTypeDBManager.TABLE_NAME+"," +
            " FOREIGN KEY("+KEY_ID_BRAND_USER_CLOTHES+") REFERENCES"+BrandDBManager.TABLE_NAME+
            ");";

    // Constructeur
    public UserClothesDBManager(Context context)
    {
        super(context);
    }



    public long addUserClothes(UserClothes uc){
        // Ajout d'un enregistrement dans la table

        Gson gson = new Gson();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_USER_CLOTHES, uc.getId_user_clothes());
        values.put(KEY_ID_USER_USER_CLOTHES, uc.getUser().getId_user());
        values.put(KEY_ID_GARMENT_TYPE_USER_CLOTHES, uc.getGarmentType().getId_garment_type());
        values.put(KEY_ID_BRAND_USER_CLOTHES, uc.getBrand().getId_brand());
        values.put(KEY_COUNTRY_USER_CLOTHES, uc.getCountry());
        values.put(KEY_SIZE_USER_CLOTHES, uc.getSize());
        values.put(KEY_COMMENT_USER_CLOTHES, uc.getComment());
        values.put(KEY_SIZES_USER_CLOTHES, gson.toJson(uc.getSizes()));
        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateUserClothes(UserClothes uc) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        Gson gson = new Gson();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_USER_CLOTHES, uc.getId_user_clothes());
        values.put(KEY_ID_USER_USER_CLOTHES, uc.getUser().getId_user());
        values.put(KEY_ID_GARMENT_TYPE_USER_CLOTHES, uc.getGarmentType().getId_garment_type());
        values.put(KEY_ID_BRAND_USER_CLOTHES, uc.getBrand().getId_brand());
        values.put(KEY_COUNTRY_USER_CLOTHES, uc.getCountry());
        values.put(KEY_SIZE_USER_CLOTHES, uc.getSize());
        values.put(KEY_COMMENT_USER_CLOTHES, uc.getComment());
        values.put(KEY_SIZES_USER_CLOTHES, gson.toJson(uc.getSizes()));

        String where = KEY_ID_USER_CLOTHES+" = ?";
        String[] whereArgs = {uc.getId_user_clothes()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteUserClothes(UserClothes uc) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_USER_CLOTHES+" = ?";
        String[] whereArgs = {uc.getId_user_clothes()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public UserClothes getUserClothes(int id) {
        // Retourne l'animal dont l'id est passé en paramètre

        UserClothes uc=new UserClothes();

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_USER_CLOTHES+"="+id, null);
        if (c.moveToFirst()) {

            uc.setId_user_clothes(c.getInt(c.getColumnIndex(KEY_ID_USER_CLOTHES)));

            UserDBManager udbm = new UserDBManager(context);
            udbm.open();
            uc.setUser(udbm.getUser(c.getInt(c.getColumnIndex(KEY_ID_USER_USER_CLOTHES))));
            udbm.close();

            GarmentTypeDBManager gtdbm = new GarmentTypeDBManager(context);
            gtdbm.open();
            uc.setGarmentType(gtdbm.getGarmentType(c.getInt(c.getColumnIndex(KEY_ID_GARMENT_TYPE_USER_CLOTHES))));
            gtdbm.close();

            BrandDBManager bdbm = new BrandDBManager(context);
            bdbm.open();
            uc.setBrand(bdbm.getBrand(c.getInt(c.getColumnIndex(KEY_ID_BRAND_USER_CLOTHES))));
            bdbm.close();

            uc.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY_USER_CLOTHES)));
            uc.setSize(c.getString(c.getColumnIndex(KEY_SIZE_USER_CLOTHES)));
            uc.setComment(c.getString(c.getColumnIndex(KEY_COMMENT_USER_CLOTHES)));

            Gson gson = new Gson();
            uc.setSizes(gson.fromJson(c.getString(c.getColumnIndex(KEY_SIZES_USER_CLOTHES)), new TypeToken<ArrayList<TabSizes>>(){}.getType()));

            c.close();
        }

        return uc;
    }

    public Cursor getUserClothes() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

} // class BrandDBManager


