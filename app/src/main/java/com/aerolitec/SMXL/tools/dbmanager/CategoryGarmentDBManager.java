package com.aerolitec.SMXL.tools.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.CategoryGarment;
import com.aerolitec.SMXL.ui.SMXL;

import java.util.ArrayList;

/**
 * Created by Jerome on 28/04/2015.
 */
public class CategoryGarmentDBManager extends DBManager {

    /*
    1-Tshirt
    2-robe jupe
    3-pantalons
    4-chemise
    5-blouson
    6-chaussure
    7-sweat pull
    8-veste
    9-costume
    10-ss vetement
    11-autre

     */

    public static final String TABLE_NAME = "category_garment";
    public static final String KEY_ID_CATEGORY_GARMENT="id_category_garment";
    public static final String KEY_ICON_CATEGORY_GARMENT="icon";
    public static final String KEY_NAME_CATEGORY_GARMENT="category_garment_name";

    public static final String CREATE_TABLE_BRAND = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_CATEGORY_GARMENT+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+KEY_ICON_CATEGORY_GARMENT+" INTEGER" +
            " "+KEY_NAME_CATEGORY_GARMENT+" TEXT NOT NULL" +
            ");";

    // Constructeur
    public CategoryGarmentDBManager(Context context)
    {
        super(context);
    }



    public long addCategoryGarment(CategoryGarment cg){
        open();
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ICON_CATEGORY_GARMENT, cg.getIcon());
        values.put(KEY_NAME_CATEGORY_GARMENT, cg.getCategory_garment_name());

        long i =db.insert(TABLE_NAME,null,values);
        close();
        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return i;
    }

    public int updateCategoryGarment(CategoryGarment cg) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        open();
        ContentValues values = new ContentValues();
        values.put(KEY_ICON_CATEGORY_GARMENT, cg.getIcon());
        values.put(KEY_NAME_CATEGORY_GARMENT, cg.getCategory_garment_name());

        String where = KEY_ID_CATEGORY_GARMENT+" = ?";
        String[] whereArgs = {cg.getId_category_garment()+""};

        int i =db.update(TABLE_NAME, values, where, whereArgs);
        close();
        return i;
    }

    public int updateCategoryGarment(int id, int icon) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        open();
        ContentValues values = new ContentValues();
        values.put(KEY_ICON_CATEGORY_GARMENT, icon);

        String where = KEY_ID_CATEGORY_GARMENT+" = ?";
        String[] whereArgs = {id+""};

        int i =db.update(TABLE_NAME, values, where, whereArgs);
        close();
        return i;
    }

    public int deleteCategoryGarment(CategoryGarment cg) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        open();
        String where = KEY_ID_CATEGORY_GARMENT+" = ?";
        String[] whereArgs = {cg.getId_category_garment()+""};

        int i = db.delete(TABLE_NAME, where, whereArgs);
        close();
        return i;
    }

    public CategoryGarment getCategoryGarment(int id) {
        // Retourne l'animal dont l'id est passé en paramètre

        open();
        CategoryGarment cg=new CategoryGarment();

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_CATEGORY_GARMENT+"="+id, null);
        if (c.moveToFirst()) {
            cg.setId_category_garment(c.getInt(c.getColumnIndex(KEY_ID_CATEGORY_GARMENT)));
            cg.setIcon(c.getInt(c.getColumnIndex(KEY_ICON_CATEGORY_GARMENT)));
            cg.setCategory_garment_name(c.getString(c.getColumnIndex(KEY_NAME_CATEGORY_GARMENT)));

            c.close();
        }

        close();
        return cg;
    }

    /*
    public Cursor getCategoryGarments() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }*/

    public ArrayList<CategoryGarment> getAllCategoryGarment(){
        open();
        ArrayList<CategoryGarment> categoryGarments = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        boolean eof = c.moveToFirst();
        while (eof) {
            CategoryGarment cg = new CategoryGarment();

            cg.setId_category_garment(c.getInt(c.getColumnIndex(KEY_ID_CATEGORY_GARMENT)));
            cg.setIcon(c.getInt(c.getColumnIndex(KEY_ICON_CATEGORY_GARMENT)));
            cg.setCategory_garment_name(c.getString(c.getColumnIndex(KEY_NAME_CATEGORY_GARMENT)));

            categoryGarments.add(cg);
            eof = c.moveToNext();
        }
        c.close();
        close();
        return categoryGarments;
    }


} // class CategoryGarmentDBManager


