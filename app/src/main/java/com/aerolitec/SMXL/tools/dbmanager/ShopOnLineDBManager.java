package com.aerolitec.SMXL.tools.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.aerolitec.SMXL.model.ShopOnLine;

import java.util.ArrayList;

/**
 * Created by Jerome on 03/07/2015.
 */
public class ShopOnLineDBManager extends DBManager {

    public static final String TABLE_NAME = "shop_on_line";
    public static final String KEY_ID_SHOP="id_shop_on_line";
    public static final String KEY_NOM_SHOP="shop_on_line_name";
    public static final String KEY_WEBSITE_SHOP="shop_on_line_website";


    public static final String CREATE_TABLE_BRAND = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_SHOP+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+KEY_NOM_SHOP+" TEXT" +
            " "+KEY_WEBSITE_SHOP+" TEXT" +
            ");";

    // Constructeur
    public ShopOnLineDBManager(Context context)
    {
        super(context);
    }


    public int updateShop(ShopOnLine shopOnLine) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête
        open();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM_SHOP, shopOnLine.getShoponline_name());
        values.put(KEY_WEBSITE_SHOP, shopOnLine.getShoponline_website());

        String where = KEY_ID_SHOP+" = ?";
        String[] whereArgs = {shopOnLine.getId_shoponline()+""};

        int i = db.update(TABLE_NAME, values, where, whereArgs);
        close();
        return i;
    }

    public int deleteShop(ShopOnLine shopOnLine) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon
        open();
        String where = KEY_ID_SHOP+" = ?";
        String[] whereArgs = {shopOnLine.getId_shoponline()+""};
        int i = db.delete(TABLE_NAME, where, whereArgs);
        close();
        return i;
    }

    public ShopOnLine getShop(int id) {
        // Retourne l'animal dont l'id est passé en paramètre

        open();
        ShopOnLine b=new ShopOnLine();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID_SHOP + "=" + id, null);
        if (c.moveToFirst()) {
            b.setId_shoponline(c.getInt(c.getColumnIndex(KEY_ID_SHOP)));
            b.setShoponline_name(c.getString(c.getColumnIndex(KEY_NOM_SHOP)));
            b.setShoponline_website(c.getString(c.getColumnIndex(KEY_WEBSITE_SHOP)));
            c.close();
        }
        close();
        return b;
    }

    public ArrayList<ShopOnLine> getAllShops(){
        open();
        ArrayList<ShopOnLine> shops = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_NOM_SHOP, null);
        boolean eof = c.moveToFirst();
        while (eof) {
            ShopOnLine b = new ShopOnLine();
            b.setId_shoponline(c.getInt(c.getColumnIndex(KEY_ID_SHOP)));
            b.setShoponline_name(c.getString(c.getColumnIndex(KEY_NOM_SHOP)));
            b.setShoponline_website(c.getString(c.getColumnIndex(KEY_WEBSITE_SHOP)));
            shops.add(b);
            eof = c.moveToNext();
        }
        c.close();
        close();
        return shops;
    }

} // class ShopOnLineDBManager