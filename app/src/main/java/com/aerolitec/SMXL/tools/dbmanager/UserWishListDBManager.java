package com.aerolitec.SMXL.tools.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.model.UserWishList;
import com.aerolitec.SMXL.ui.SMXL;

import java.util.ArrayList;

/**
 * Created by Nelson on 17/08/2015.
 */
public class UserWishListDBManager extends DBManager{
    /*


    String picture;

    */
    public static final String TABLE_NAME = "user_wishlist";
    public static final String KEY_ID_USER_WISHLIST="id_user_wishlist";
    public static final String KEY_ID_USER_USER_WISHLIST="id_user";
    public static final String KEY_ID_GARMENT_TYPE_USER_WISHLIST="id_garment_type";
    public static final String KEY_ID_BRAND_USER_WISHLIST="id_brand";
    public static final String KEY_COUNTRY_USER_WISHLIST="country";
    public static final String KEY_SIZE_USER_WISHLIST="size";
    public static final String KEY_PICTURE_USER_WISHLIST="picture";

    public static final String CREATE_TABLE_USER_WISHLIST = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_USER_WISHLIST+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+KEY_ID_USER_USER_WISHLIST+" INTEGER NOT NULL," +
            " "+KEY_ID_GARMENT_TYPE_USER_WISHLIST+" INTEGER NOT NULL," +
            " "+KEY_ID_BRAND_USER_WISHLIST+" INTEGER NOT NULL," +
            " "+KEY_COUNTRY_USER_WISHLIST+" TEXT," +
            " "+KEY_SIZE_USER_WISHLIST+" TEXT," +
            " "+KEY_PICTURE_USER_WISHLIST+" TEXT," +
            " FOREIGN KEY("+KEY_ID_USER_USER_WISHLIST+") REFERENCES "+UserDBManager.TABLE_NAME+"," +
            " FOREIGN KEY("+KEY_ID_GARMENT_TYPE_USER_WISHLIST+") REFERENCES "+GarmentTypeDBManager.TABLE_NAME+"," +
            " FOREIGN KEY("+KEY_ID_BRAND_USER_WISHLIST+") REFERENCES"+BrandDBManager.TABLE_NAME+
            ");";

    // Constructeur
    public UserWishListDBManager(Context context)
    {
        super(context);

    }



    public long addUserWishList(UserWishList uwl){
        // Ajout d'un enregistrement dans la table

        open();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_USER_USER_WISHLIST, uwl.getUser().getId_user());
        values.put(KEY_ID_GARMENT_TYPE_USER_WISHLIST, uwl.getGarmentType().getId_garment_type());
        values.put(KEY_ID_BRAND_USER_WISHLIST, uwl.getBrand().getId_brand());
        values.put(KEY_COUNTRY_USER_WISHLIST, uwl.getCountrySelected());
        values.put(KEY_SIZE_USER_WISHLIST, uwl.getSize());
        values.put(KEY_PICTURE_USER_WISHLIST, uwl.getPicture());
        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        long i = db.insert(TABLE_NAME,null,values);
        close();
        return i;
    }

    public int updateUserWishList(UserWishList uwl) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        open();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_USER_WISHLIST, uwl.getId_user_wishlist());
        values.put(KEY_ID_USER_USER_WISHLIST, uwl.getUser().getId_user());
        values.put(KEY_ID_GARMENT_TYPE_USER_WISHLIST, uwl.getGarmentType().getId_garment_type());
        values.put(KEY_ID_BRAND_USER_WISHLIST, uwl.getBrand().getId_brand());
        values.put(KEY_COUNTRY_USER_WISHLIST, uwl.getCountrySelected());
        values.put(KEY_SIZE_USER_WISHLIST, uwl.getSize());
        values.put(KEY_PICTURE_USER_WISHLIST, uwl.getPicture());

        String where = KEY_ID_USER_WISHLIST + " = ?";
        String[] whereArgs = {uwl.getId_user_wishlist()+""};

        int i = db.update(TABLE_NAME, values, where, whereArgs);
        close();
        return i;
    }

    public int deleteUserWishList(UserWishList uwl) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        open();
        String where = KEY_ID_USER_WISHLIST + " = ?";
        String[] whereArgs = {uwl.getId_user_wishlist()+""};

        int i = db.delete(TABLE_NAME, where, whereArgs);
        close();
        return i;
    }

    public UserWishList getUserWishList(int id) {

        open();
        UserWishList uwl=new UserWishList();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID_USER_WISHLIST+ "=" + id, null);
        if (c.moveToFirst()) {

            uwl.setId_user_wishlist(c.getInt(c.getColumnIndex(KEY_ID_USER_WISHLIST)));
            uwl.setUser(SMXL.getUserDBManager().getUser(c.getInt(c.getColumnIndex(KEY_ID_USER_USER_WISHLIST))));
            uwl.setGarmentType(SMXL.getGarmentTypeDBManager().getGarmentType(c.getInt(c.getColumnIndex(KEY_ID_GARMENT_TYPE_USER_WISHLIST))));
            uwl.setBrand(SMXL.getBrandDBManager().getBrand(c.getInt(c.getColumnIndex(KEY_ID_BRAND_USER_WISHLIST))));
            uwl.setCountrySelected(c.getString(c.getColumnIndex(KEY_COUNTRY_USER_WISHLIST)));
            uwl.setSize(c.getString(c.getColumnIndex(KEY_SIZE_USER_WISHLIST)));
            uwl.setPicture(c.getString(c.getColumnIndex(KEY_PICTURE_USER_WISHLIST)));

            c.close();
        }

        close();
        return uwl;
    }
    public ArrayList<UserWishList> getAllUserWishList(User user){
        open();
        ArrayList<UserWishList> wishlist = new ArrayList<>();
        Cursor c;
        c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_USER_USER_WISHLIST+" = "+user.getId_user(), null);
        boolean eof = c.moveToFirst();
        while (eof) {
            UserWishList uc = new UserWishList();

            uc.setId_user_wishlist(c.getInt(c.getColumnIndex(KEY_ID_USER_WISHLIST)));

            uc.setUser(user);

            uc.setGarmentType(SMXL.getGarmentTypeDBManager().getGarmentType(c.getInt(c.getColumnIndex(KEY_ID_GARMENT_TYPE_USER_WISHLIST))));

            uc.setBrand(SMXL.getBrandDBManager().getBrand(c.getInt(c.getColumnIndex(KEY_ID_BRAND_USER_WISHLIST))));

            uc.setCountrySelected(c.getString(c.getColumnIndex(KEY_COUNTRY_USER_WISHLIST)));
            uc.setSize(c.getString(c.getColumnIndex(KEY_SIZE_USER_WISHLIST)));
            uc.setPicture(c.getString(c.getColumnIndex(KEY_PICTURE_USER_WISHLIST)));



            wishlist.add(uc);
            eof = c.moveToNext();
        }
        c.close();

        close();
        return wishlist;
    }

    public int getLastId(){
        open();
        Cursor c;
        int result = -1;
        c = db.rawQuery("SELECT seq FROM sqlite_sequence WHERE name='"+TABLE_NAME+"'", null);
        boolean eof = c.moveToFirst();
        if(eof){
            result = c.getInt(0);
        }
        close();
        return result;
    }
}
