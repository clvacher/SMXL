package com.aerolitec.SMXL.tools.dbmanager;

import android.content.Context;
import android.database.Cursor;

import com.aerolitec.SMXL.model.CategoryGarment;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.ui.SMXL;

import java.util.ArrayList;


/**
 * Created by Jerome on 28/04/2015.
 */
public class GarmentTypeDBManager extends DBManager {

    public static final String TABLE_NAME = "garment_type";
    public static final String KEY_ID_GARMENT_TYPE="id_garment_type";
    public static final String KEY_ID_CATEGORY_GARMENT_GARMENT_TYPE="id_category_garment";
    public static final String KEY_NAME_GARMENT_TYPE="garment_name";
    public static final String KEY_SEX_GARMENT_TYPE="sex";

    public static final String CREATE_TABLE_CLOTHES_TYPE = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_GARMENT_TYPE+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+KEY_ID_CATEGORY_GARMENT_GARMENT_TYPE+" INTEGER," +
            " "+KEY_NAME_GARMENT_TYPE+" TEXT" +
            " "+KEY_SEX_GARMENT_TYPE+" INTEGER" +
            " FOREIGN KEY("+KEY_ID_CATEGORY_GARMENT_GARMENT_TYPE+") REFERENCES "+CategoryGarmentDBManager.TABLE_NAME+
            ");";

    // Constructeur
    public GarmentTypeDBManager(Context context)
    {
        super(context);
    }

/*
    public long addGarmentType(GarmentType gt){
        // Ajout d'un enregistrement dans la table

        open();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME_GARMENT_TYPE, gt.getType());
        values.put(KEY_SEX_GARMENT_TYPE, gt.getSex());
        values.put(KEY_ID_CATEGORY_GARMENT_GARMENT_TYPE, gt.getCategoryGarment().getId_category_garment());

        long i=db.insert(TABLE_NAME,null,values);
        close();
        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return i;
    }

    public int updateGarmentType(GarmentType gt) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        open();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_GARMENT_TYPE, gt.getType());
        values.put(KEY_SEX_GARMENT_TYPE, gt.getSex());
        values.put(KEY_ID_CATEGORY_GARMENT_GARMENT_TYPE, gt.getCategoryGarment().getId_category_garment());

        String where = KEY_ID_GARMENT_TYPE+" = ?";
        String[] whereArgs = {gt.getId_garment_type()+""};

        int i = db.update(TABLE_NAME, values, where, whereArgs);
        close();
        return i;
    }

    public int deleteGarmentType(GarmentType gt) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        open();
        String where = KEY_ID_GARMENT_TYPE+" = ?";
        String[] whereArgs = {gt.getId_garment_type()+""};

        int i = db.delete(TABLE_NAME, where, whereArgs);
        close();
        return i;
    }
*/
    public GarmentType getGarmentType(int id) {
        // Retourne l'animal dont l'id est passé en paramètre

        open();
        GarmentType gt=new GarmentType();

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_GARMENT_TYPE+"="+id, null);
        if (c.moveToFirst()) {
            gt.setId_garment_type(c.getInt(c.getColumnIndex(KEY_ID_GARMENT_TYPE)));

            SMXL.getCategoryGarmentDBManager().open();
            gt.setCategoryGarment(SMXL.getCategoryGarmentDBManager().getCategoryGarment(c.getInt(c.getColumnIndex(KEY_ID_CATEGORY_GARMENT_GARMENT_TYPE))));
            SMXL.getCategoryGarmentDBManager().close();

            gt.setType(c.getString(c.getColumnIndex(KEY_NAME_GARMENT_TYPE)));
            gt.setSex(c.getString(c.getColumnIndex(KEY_SEX_GARMENT_TYPE)));
            c.close();
        }

        close();
        return gt;
    }

    /*
    public Cursor getGarmentTypes() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }*/

    public ArrayList<GarmentType> getAllGarmentTypes() {
        open();
        ArrayList<GarmentType> garments = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        boolean eof = c.moveToFirst();
        while (eof) {
            GarmentType gt = new GarmentType();
            gt.setId_garment_type(c.getInt(c.getColumnIndex(KEY_ID_GARMENT_TYPE)));

            SMXL.getCategoryGarmentDBManager().open();
            gt.setCategoryGarment(SMXL.getCategoryGarmentDBManager().getCategoryGarment(c.getInt(c.getColumnIndex(KEY_ID_CATEGORY_GARMENT_GARMENT_TYPE))));
            SMXL.getCategoryGarmentDBManager().close();

            gt.setType(c.getString(c.getColumnIndex(KEY_NAME_GARMENT_TYPE)));
            gt.setSex(c.getString(c.getColumnIndex(KEY_SEX_GARMENT_TYPE)));

            garments.add(gt);
            eof = c.moveToNext();
        }
        c.close();
        close();
        return garments;
    }

    public ArrayList<GarmentType> getAllGarmentTypesBySex(int sex) {
        open();
        ArrayList<GarmentType> garments = new ArrayList<>();
        String sexString = "F";
        if(sex==1){
            sexString="H";
        }
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_SEX_GARMENT_TYPE+ " = '"+sexString+"'", null);
        boolean eof = c.moveToFirst();
        while (eof) {
            GarmentType gt = new GarmentType();
            gt.setId_garment_type(c.getInt(c.getColumnIndex(KEY_ID_GARMENT_TYPE)));

            SMXL.getCategoryGarmentDBManager().open();
            gt.setCategoryGarment(SMXL.getCategoryGarmentDBManager().getCategoryGarment(c.getInt(c.getColumnIndex(KEY_ID_CATEGORY_GARMENT_GARMENT_TYPE))));
            SMXL.getCategoryGarmentDBManager().close();

            gt.setType(c.getString(c.getColumnIndex(KEY_NAME_GARMENT_TYPE)));
            gt.setSex(c.getString(c.getColumnIndex(KEY_SEX_GARMENT_TYPE)));

            garments.add(gt);
            eof = c.moveToNext();
        }
        c.close();
        close();
        return garments;
    }

    public ArrayList<GarmentType> getAllGarmentTypeByCategory(CategoryGarment cg, int sex){
        open();
        ArrayList<GarmentType> garments = new ArrayList<>();
        String sexString = "F";
        if(sex==1){
            sexString="H";
        }
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_CATEGORY_GARMENT_GARMENT_TYPE+" = "+cg.getId_category_garment()+" AND "+KEY_SEX_GARMENT_TYPE+" = '"+sexString+"'", null);
        boolean eof = c.moveToFirst();
        while (eof) {
            GarmentType gt = new GarmentType();
            gt.setId_garment_type(c.getInt(c.getColumnIndex(KEY_ID_GARMENT_TYPE)));

            SMXL.getCategoryGarmentDBManager().open();
            gt.setCategoryGarment(SMXL.getCategoryGarmentDBManager().getCategoryGarment(c.getInt(c.getColumnIndex(KEY_ID_CATEGORY_GARMENT_GARMENT_TYPE))));
            SMXL.getCategoryGarmentDBManager().close();

            gt.setType(c.getString(c.getColumnIndex(KEY_NAME_GARMENT_TYPE)));
            gt.setSex(c.getString(c.getColumnIndex(KEY_SEX_GARMENT_TYPE)));

            garments.add(gt);
            eof = c.moveToNext();
        }
        c.close();
        close();
        return garments;
    }

} // class GarmentTypeDBManager

