package com.aerolitec.SMXL.tools.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.aerolitec.SMXL.model.SizeConvert;

/**
 * Created by Jerome on 28/04/2015.
 */
public class SizeConvertDBManager extends DBManager {
    private static final String TABLE_NAME = "size_convert";
    public static final String KEY_ID_SIZE_CONVERT="id_size_convert";
    public static final String KEY_GARMENT_SIZE_CONVERT="garment";
    public static final String KEY_SEX_SIZE_CONVERT="sex";
    public static final String KEY_US_SIZE_CONVERT="valueUS";
    public static final String KEY_UK_SIZE_CONVERT="valueUK";
    public static final String KEY_UE_SIZE_CONVERT="valueUE";
    public static final String KEY_FR_SIZE_CONVERT="valueFR";
    public static final String KEY_ITA_SIZE_CONVERT="valueITA";
    public static final String KEY_JAP_SIZE_CONVERT="valueJAP";
    public static final String KEY_SMXL_SIZE_CONVERT="valueSMXL";


    public static final String CREATE_TABLE_SIZE_CONVERT = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_SIZE_CONVERT+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+KEY_GARMENT_SIZE_CONVERT+" TEXT" +
            " "+KEY_SEX_SIZE_CONVERT+" TEXT" +
            " "+KEY_US_SIZE_CONVERT+" TEXT" +
            " "+KEY_UK_SIZE_CONVERT+" TEXT" +
            " "+KEY_UE_SIZE_CONVERT+" TEXT" +
            " "+KEY_FR_SIZE_CONVERT+" TEXT" +
            " "+KEY_ITA_SIZE_CONVERT+" TEXT" +
            " "+KEY_JAP_SIZE_CONVERT+" TEXT" +
            " "+KEY_SMXL_SIZE_CONVERT+" TEXT" +
            ");";

    // Constructeur
    public SizeConvertDBManager(Context context)
    {
        super(context);
    }


    public long addSizeConvert(SizeConvert sc){
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_GARMENT_SIZE_CONVERT, sc.getGarment());
        values.put(KEY_SEX_SIZE_CONVERT, sc.getSex());
        values.put(KEY_US_SIZE_CONVERT, sc.getValueUS());
        values.put(KEY_UK_SIZE_CONVERT, sc.getValueUK());
        values.put(KEY_UE_SIZE_CONVERT, sc.getValueUE());
        values.put(KEY_FR_SIZE_CONVERT, sc.getValueFR());
        values.put(KEY_ITA_SIZE_CONVERT, sc.getValueITA());
        values.put(KEY_JAP_SIZE_CONVERT, sc.getValueJAP());
        values.put(KEY_SMXL_SIZE_CONVERT, sc.getValueSMXL());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateSizeConvert(SizeConvert sc) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_GARMENT_SIZE_CONVERT, sc.getGarment());
        values.put(KEY_SEX_SIZE_CONVERT, sc.getSex());
        values.put(KEY_US_SIZE_CONVERT, sc.getValueUS());
        values.put(KEY_UK_SIZE_CONVERT, sc.getValueUK());
        values.put(KEY_UE_SIZE_CONVERT, sc.getValueUE());
        values.put(KEY_FR_SIZE_CONVERT, sc.getValueFR());
        values.put(KEY_ITA_SIZE_CONVERT, sc.getValueITA());
        values.put(KEY_JAP_SIZE_CONVERT, sc.getValueJAP());
        values.put(KEY_SMXL_SIZE_CONVERT, sc.getValueSMXL());

        String where = KEY_ID_SIZE_CONVERT+" = ?";
        String[] whereArgs = {sc.getId_size_convert()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteSizeConvert(SizeConvert sc) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_SIZE_CONVERT+" = ?";
        String[] whereArgs = {sc.getId_size_convert()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public SizeConvert getSizeConvert(int id) {
        // Retourne l'animal dont l'id est passé en paramètre

        SizeConvert sc = new SizeConvert();

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_SIZE_CONVERT+"="+id, null);
        if (c.moveToFirst()) {
            sc.setId_size_convert(c.getInt(c.getColumnIndex(KEY_ID_SIZE_CONVERT)));
            sc.setGarment(c.getString(c.getColumnIndex(KEY_GARMENT_SIZE_CONVERT)));
            sc.setSex(c.getString(c.getColumnIndex(KEY_SEX_SIZE_CONVERT)));
            sc.setValueUS(c.getString(c.getColumnIndex(KEY_US_SIZE_CONVERT)));
            sc.setValueUK(c.getString(c.getColumnIndex(KEY_UK_SIZE_CONVERT)));
            sc.setValueUE(c.getString(c.getColumnIndex(KEY_UE_SIZE_CONVERT)));
            sc.setValueFR(c.getString(c.getColumnIndex(KEY_FR_SIZE_CONVERT)));
            sc.setValueITA(c.getString(c.getColumnIndex(KEY_ITA_SIZE_CONVERT)));
            sc.setValueJAP(c.getString(c.getColumnIndex(KEY_JAP_SIZE_CONVERT)));
            sc.setValueSMXL(c.getString(c.getColumnIndex(KEY_SMXL_SIZE_CONVERT)));

            c.close();
        }

        return sc;
    }

    public Cursor getSizeConverts() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

} // class BrandDBManager


