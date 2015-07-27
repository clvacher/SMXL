package com.aerolitec.SMXL.tools.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.SizeConvert;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.ui.SMXL;

import java.util.ArrayList;

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
    public static final String KEY_RUS_SIZE_CONVERT="valueRUS";
    public static final String KEY_BRA_SIZE_CONVERT="valueBRA";
    public static final String KEY_JAP_SIZE_CONVERT="valueJAP";
    public static final String KEY_SMXL_SIZE_CONVERT="valueSMXL";
    //TODO Attention ajouté que pour la requête qui utilise l'id_garment
    public static final String KEY_ID_GARMENT="id_garment";


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
            " "+KEY_RUS_SIZE_CONVERT+" TEXT" +
            " "+KEY_BRA_SIZE_CONVERT+" TEXT" +
            " "+KEY_SMXL_SIZE_CONVERT+" TEXT" +
            ");";

    // Constructeur
    public SizeConvertDBManager(Context context)
    {
        super(context);
    }


    public long addSizeConvert(SizeConvert sc){
        // Ajout d'un enregistrement dans la table
        open();

        ContentValues values = new ContentValues();
        values.put(KEY_GARMENT_SIZE_CONVERT, sc.getGarment());
        values.put(KEY_SEX_SIZE_CONVERT, sc.getSex());
        values.put(KEY_US_SIZE_CONVERT, sc.getValueUS());
        values.put(KEY_UK_SIZE_CONVERT, sc.getValueUK());
        values.put(KEY_UE_SIZE_CONVERT, sc.getValueUE());
        values.put(KEY_FR_SIZE_CONVERT, sc.getValueFR());
        values.put(KEY_ITA_SIZE_CONVERT, sc.getValueITA());
        values.put(KEY_JAP_SIZE_CONVERT, sc.getValueJAP());
        values.put(KEY_RUS_SIZE_CONVERT, sc.getValueRUS());
        values.put(KEY_BRA_SIZE_CONVERT, sc.getValueBRA());
        values.put(KEY_SMXL_SIZE_CONVERT, sc.getValueSMXL());

        long i = db.insert(TABLE_NAME,null,values);
        close();
        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return i;
    }

    public int updateSizeConvert(SizeConvert sc) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        open();
        ContentValues values = new ContentValues();
        values.put(KEY_GARMENT_SIZE_CONVERT, sc.getGarment());
        values.put(KEY_SEX_SIZE_CONVERT, sc.getSex());
        values.put(KEY_US_SIZE_CONVERT, sc.getValueUS());
        values.put(KEY_UK_SIZE_CONVERT, sc.getValueUK());
        values.put(KEY_UE_SIZE_CONVERT, sc.getValueUE());
        values.put(KEY_FR_SIZE_CONVERT, sc.getValueFR());
        values.put(KEY_ITA_SIZE_CONVERT, sc.getValueITA());
        values.put(KEY_JAP_SIZE_CONVERT, sc.getValueJAP());
        values.put(KEY_RUS_SIZE_CONVERT, sc.getValueRUS());
        values.put(KEY_BRA_SIZE_CONVERT, sc.getValueBRA());
        values.put(KEY_SMXL_SIZE_CONVERT, sc.getValueSMXL());

        String where = KEY_ID_SIZE_CONVERT+" = ?";
        String[] whereArgs = {sc.getId_size_convert()+""};

        int i = db.update(TABLE_NAME, values, where, whereArgs);
        close();
        return i;
    }

    public int deleteSizeConvert(SizeConvert sc) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        open();
        String where = KEY_ID_SIZE_CONVERT+" = ?";
        String[] whereArgs = {sc.getId_size_convert()+""};
        int i = db.delete(TABLE_NAME, where, whereArgs);
        close();
        return i;
    }

    public SizeConvert getSizeConvert(int id) {
        // Retourne l'animal dont l'id est passé en paramètre
        open();
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
            sc.setValueBRA(c.getString(c.getColumnIndex(KEY_BRA_SIZE_CONVERT)));
            sc.setValueRUS(c.getString(c.getColumnIndex(KEY_RUS_SIZE_CONVERT)));
            sc.setValueSMXL(c.getString(c.getColumnIndex(KEY_SMXL_SIZE_CONVERT)));

            c.close();
        }
        close();
        return sc;
    }

 /*   public Cursor getSizeConverts() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }*/

    public ArrayList<GarmentType> getGarmentsSizeGuideGroupBySexAndGarment(){
        open();
        ArrayList<GarmentType> garments = new ArrayList<GarmentType>();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " GROUP BY " + KEY_SEX_SIZE_CONVERT + "," + KEY_GARMENT_SIZE_CONVERT + " ORDER BY " + KEY_GARMENT_SIZE_CONVERT, null);

        boolean eof = c.moveToFirst();
        while (eof) {

            GarmentType gt = new GarmentType();

            gt.setId_garment_type(c.getInt(c.getColumnIndex(KEY_ID_SIZE_CONVERT)));
            gt.setType(c.getString(c.getColumnIndex(KEY_GARMENT_SIZE_CONVERT)));
            gt.setSex(c.getString(c.getColumnIndex(KEY_SEX_SIZE_CONVERT)));

            garments.add(gt);
            eof = c.moveToNext();
        }
        c.close();
        close();
        return garments;
    }

    // getSizeConvert
    public ArrayList<SizeConvert> getConvertSizesByGarment(GarmentType garment){
        open();
        ArrayList<SizeConvert> sizes = new ArrayList<SizeConvert>();
        Cursor c;
        c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE UPPER("+KEY_GARMENT_SIZE_CONVERT+") LIKE UPPER('"+garment.getType()+"')", null);
        boolean eof = c.moveToFirst();
        while(eof)
        {
            SizeConvert sc = new SizeConvert();
            sc.setId_size_convert(c.getInt(c.getColumnIndex(KEY_ID_SIZE_CONVERT)));
            sc.setGarment(c.getString(c.getColumnIndex(KEY_GARMENT_SIZE_CONVERT)));
            sc.setSex(c.getString(c.getColumnIndex(KEY_SEX_SIZE_CONVERT)));
            sc.setValueUS(c.getString(c.getColumnIndex(KEY_US_SIZE_CONVERT)));
            sc.setValueUK(c.getString(c.getColumnIndex(KEY_UK_SIZE_CONVERT)));
            sc.setValueUE(c.getString(c.getColumnIndex(KEY_UE_SIZE_CONVERT)));
            sc.setValueFR(c.getString(c.getColumnIndex(KEY_FR_SIZE_CONVERT)));
            sc.setValueITA(c.getString(c.getColumnIndex(KEY_ITA_SIZE_CONVERT)));
            sc.setValueJAP(c.getString(c.getColumnIndex(KEY_JAP_SIZE_CONVERT)));
            sc.setValueBRA(c.getString(c.getColumnIndex(KEY_BRA_SIZE_CONVERT)));
            sc.setValueRUS(c.getString(c.getColumnIndex(KEY_RUS_SIZE_CONVERT)));
            sc.setValueSMXL(c.getString(c.getColumnIndex(KEY_SMXL_SIZE_CONVERT)));
            sizes.add(sc);
            eof = c.moveToNext();
        }
        c.close();

        close();
        return sizes;
    }

    public ArrayList<SizeConvert> getConvertSizesByGarmentAndSex(String garment, String sex)
    {
        open();
        ArrayList sizes = new ArrayList();
        Cursor c;
        c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE UPPER("+KEY_GARMENT_SIZE_CONVERT+") LIKE UPPER('"+garment+"') AND UPPER("+KEY_SEX_SIZE_CONVERT+") LIKE UPPER('"+sex+"')", null);

        boolean eof = c.moveToFirst();
        while(eof)
        {
            SizeConvert sc = new SizeConvert();
            sc.setId_size_convert(c.getInt(c.getColumnIndex(KEY_ID_SIZE_CONVERT)));
            sc.setGarment(c.getString(c.getColumnIndex(KEY_GARMENT_SIZE_CONVERT)));
            sc.setSex(c.getString(c.getColumnIndex(KEY_SEX_SIZE_CONVERT)));
            sc.setValueUS(c.getString(c.getColumnIndex(KEY_US_SIZE_CONVERT)));
            sc.setValueUK(c.getString(c.getColumnIndex(KEY_UK_SIZE_CONVERT)));
            sc.setValueUE(c.getString(c.getColumnIndex(KEY_UE_SIZE_CONVERT)));
            sc.setValueFR(c.getString(c.getColumnIndex(KEY_FR_SIZE_CONVERT)));
            sc.setValueITA(c.getString(c.getColumnIndex(KEY_ITA_SIZE_CONVERT)));
            sc.setValueJAP(c.getString(c.getColumnIndex(KEY_JAP_SIZE_CONVERT)));
            sc.setValueBRA(c.getString(c.getColumnIndex(KEY_BRA_SIZE_CONVERT)));
            sc.setValueRUS(c.getString(c.getColumnIndex(KEY_RUS_SIZE_CONVERT)));
            sc.setValueSMXL(c.getString(c.getColumnIndex(KEY_SMXL_SIZE_CONVERT)));
            sizes.add(sc);

            eof = c.moveToNext();
        }
        c.close();

        close();
        return sizes;

    }

    public ArrayList<SizeConvert> getConvertSizesByGarmentTypeAndSize(GarmentType garment,String type,String size)
    {
        open();
        ArrayList sizes = new ArrayList();
        Cursor c;
        c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_GARMENT+"= "+garment.getId_garment_type()+" AND value"+type+" ='"+size+"'", null);

        boolean eof = c.moveToFirst();
        while(eof)
        {
            SizeConvert sc = new SizeConvert();
            sc.setId_size_convert(c.getInt(c.getColumnIndex(KEY_ID_SIZE_CONVERT)));
            sc.setGarment(c.getString(c.getColumnIndex(KEY_GARMENT_SIZE_CONVERT)));
            sc.setSex(c.getString(c.getColumnIndex(KEY_SEX_SIZE_CONVERT)));
            sc.setValueUS(c.getString(c.getColumnIndex(KEY_US_SIZE_CONVERT)));
            sc.setValueUK(c.getString(c.getColumnIndex(KEY_UK_SIZE_CONVERT)));
            sc.setValueUE(c.getString(c.getColumnIndex(KEY_UE_SIZE_CONVERT)));
            sc.setValueFR(c.getString(c.getColumnIndex(KEY_FR_SIZE_CONVERT)));
            sc.setValueITA(c.getString(c.getColumnIndex(KEY_ITA_SIZE_CONVERT)));
            sc.setValueJAP(c.getString(c.getColumnIndex(KEY_JAP_SIZE_CONVERT)));
            sc.setValueBRA(c.getString(c.getColumnIndex(KEY_BRA_SIZE_CONVERT)));
            sc.setValueRUS(c.getString(c.getColumnIndex(KEY_RUS_SIZE_CONVERT)));
            sc.setValueSMXL(c.getString(c.getColumnIndex(KEY_SMXL_SIZE_CONVERT)));
            sizes.add(sc);

            eof = c.moveToNext();
        }
        c.close();

        close();
        return sizes;

    }

} // class BrandDBManager


