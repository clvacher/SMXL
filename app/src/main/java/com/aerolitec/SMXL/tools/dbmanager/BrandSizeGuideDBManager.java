package com.aerolitec.SMXL.tools.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.BrandsSizeGuide;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.ui.SMXL;

import java.util.ArrayList;

/**
 * Created by Jerome on 28/04/2015.
 */
public class BrandSizeGuideDBManager extends DBManager{
    private static final String TABLE_NAME = "brand_size_guide";
    public static final String KEY_ID_BRAND_SIZE_GUIDE="id_brand_size_guide";
    public static final String KEY_ID_MARQUE_BRAND_SIZE_GUIDE ="id_brand";
    public static final String KEY_ID_GARMENT_TYPE_SIZE_GUIDE ="id_garment_type";
    public static final String KEY_CATEGORY_SIZE_GUIDE="category";
    public static final String KEY_SEX_SIZE_GUIDE="sex";
    public static final String KEY_COLLAR_SIZE_GUIDE="collar";
    public static final String KEY_CHEST_SIZE_GUIDE="chest";
    public static final String KEY_BUST_SIZE_GUIDE="bust";
    public static final String KEY_SLEEVE_SIZE_GUIDE="sleeve";
    public static final String KEY_WAIST_SIZE_GUIDE="waist";
    public static final String KEY_HEAD_SIZE_GUIDE="head";
    public static final String KEY_HIPS_SIZE_GUIDE="hips";
    public static final String KEY_THIGH_SIZE_GUIDE="thigh";
    public static final String KEY_INSEAM_SIZE_GUIDE="inseam";
    public static final String KEY_FEET_SIZE_GUIDE="feet";
    public static final String KEY_HEIGHT_SIZE_GUIDE="height";
    public static final String KEY_WEIGHT_SIZE_GUIDE="weight";
    public static final String KEY_OTHER_SIZE_GUIDE="other";
    public static final String KEY_SIZE_SMXL_SIZE_GUIDE="size_SMXL";
    public static final String KEY_SIZE_UE_SIZE_GUIDE="size_ue";
    public static final String KEY_SIZE_US_SIZE_GUIDE="size_us";
    public static final String KEY_SIZE_UK_SIZE_GUIDE="size_uk";
    public static final String KEY_SIZE_FR_SIZE_GUIDE="size_fr";
    public static final String KEY_SIZE_SUITE_SIZE_GUIDE="size_suite";


    public static final String CREATE_TABLE_BRAND_SIZE_GUIDE = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_BRAND_SIZE_GUIDE+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+ KEY_ID_MARQUE_BRAND_SIZE_GUIDE +" TEXT" +
            " "+ KEY_ID_GARMENT_TYPE_SIZE_GUIDE +" TEXT" +
            " "+KEY_CATEGORY_SIZE_GUIDE+" TEXT" +
            " "+KEY_SEX_SIZE_GUIDE+" TEXT" +
            " "+KEY_BUST_SIZE_GUIDE+" TEXT" +
            " "+KEY_COLLAR_SIZE_GUIDE+" TEXT" +
            " "+KEY_CHEST_SIZE_GUIDE+" TEXT" +
            " "+KEY_FEET_SIZE_GUIDE+" TEXT" +
            " "+KEY_HEAD_SIZE_GUIDE+" TEXT" +
            " "+KEY_HEIGHT_SIZE_GUIDE+" TEXT" +
            " "+KEY_HIPS_SIZE_GUIDE+" TEXT" +
            " "+KEY_INSEAM_SIZE_GUIDE+" TEXT" +
            " "+KEY_SLEEVE_SIZE_GUIDE+" TEXT" +
            " "+KEY_THIGH_SIZE_GUIDE+" TEXT" +
            " "+KEY_WAIST_SIZE_GUIDE+" TEXT" +
            " "+KEY_WEIGHT_SIZE_GUIDE+" TEXT" +
            " "+KEY_OTHER_SIZE_GUIDE+" TEXT" +
            " "+KEY_SIZE_FR_SIZE_GUIDE+" TEXT" +
            " "+KEY_SIZE_UE_SIZE_GUIDE+" TEXT" +
            " "+KEY_SIZE_UK_SIZE_GUIDE+" TEXT" +
            " "+KEY_SIZE_US_SIZE_GUIDE+" TEXT" +
            " "+KEY_SIZE_SMXL_SIZE_GUIDE+" TEXT" +
            " "+KEY_SIZE_SUITE_SIZE_GUIDE+" TEXT" +
            ");";

    // Constructeur
    public BrandSizeGuideDBManager(Context context)
    {
        super(context);
    }

    public long addBrandSizeGuide(BrandsSizeGuide bsg){
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ID_MARQUE_BRAND_SIZE_GUIDE, bsg.getBrand());
        values.put(KEY_ID_GARMENT_TYPE_SIZE_GUIDE, bsg.getGarmentType());
        values.put(KEY_CATEGORY_SIZE_GUIDE, bsg.getCategory());
        values.put(KEY_SEX_SIZE_GUIDE, bsg.getSex());
        values.put(KEY_BUST_SIZE_GUIDE, bsg.getBust());
        values.put(KEY_COLLAR_SIZE_GUIDE, bsg.getCollar());
        values.put(KEY_CHEST_SIZE_GUIDE, bsg.getChest());
        values.put(KEY_FEET_SIZE_GUIDE, bsg.getFeet());
        values.put(KEY_HEAD_SIZE_GUIDE, bsg.getHead());
        values.put(KEY_HEIGHT_SIZE_GUIDE, bsg.getHeight());
        values.put(KEY_HIPS_SIZE_GUIDE, bsg.getHips());
        values.put(KEY_INSEAM_SIZE_GUIDE, bsg.getInseam());
        values.put(KEY_SLEEVE_SIZE_GUIDE, bsg.getSleeve());
        values.put(KEY_THIGH_SIZE_GUIDE, bsg.getThigh());
        values.put(KEY_WAIST_SIZE_GUIDE, bsg.getWaist());
        values.put(KEY_WEIGHT_SIZE_GUIDE, bsg.getWeight());
        values.put(KEY_OTHER_SIZE_GUIDE, bsg.getOther());
        values.put(KEY_SIZE_FR_SIZE_GUIDE, bsg.getSizeFR());
        values.put(KEY_SIZE_UE_SIZE_GUIDE, bsg.getSizeUE());
        values.put(KEY_SIZE_UK_SIZE_GUIDE, bsg.getSizeUK());
        values.put(KEY_SIZE_US_SIZE_GUIDE, bsg.getSizeUS());
        values.put(KEY_SIZE_SMXL_SIZE_GUIDE, bsg.getSizeSMXL());
        values.put(KEY_SIZE_SUITE_SIZE_GUIDE, bsg.getSizeSuite());


        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateBrandSizeGuide(BrandsSizeGuide bsg) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_ID_MARQUE_BRAND_SIZE_GUIDE, bsg.getBrand());
        values.put(KEY_ID_GARMENT_TYPE_SIZE_GUIDE, bsg.getGarmentType());
        values.put(KEY_CATEGORY_SIZE_GUIDE, bsg.getCategory());
        values.put(KEY_SEX_SIZE_GUIDE, bsg.getSex());
        values.put(KEY_BUST_SIZE_GUIDE, bsg.getBust());
        values.put(KEY_COLLAR_SIZE_GUIDE, bsg.getCollar());
        values.put(KEY_CHEST_SIZE_GUIDE, bsg.getChest());
        values.put(KEY_FEET_SIZE_GUIDE, bsg.getFeet());
        values.put(KEY_HEAD_SIZE_GUIDE, bsg.getHead());
        values.put(KEY_HEIGHT_SIZE_GUIDE, bsg.getHeight());
        values.put(KEY_HIPS_SIZE_GUIDE, bsg.getHips());
        values.put(KEY_INSEAM_SIZE_GUIDE, bsg.getInseam());
        values.put(KEY_SLEEVE_SIZE_GUIDE, bsg.getSleeve());
        values.put(KEY_THIGH_SIZE_GUIDE, bsg.getThigh());
        values.put(KEY_WAIST_SIZE_GUIDE, bsg.getWaist());
        values.put(KEY_WEIGHT_SIZE_GUIDE, bsg.getWeight());
        values.put(KEY_OTHER_SIZE_GUIDE, bsg.getOther());
        values.put(KEY_SIZE_FR_SIZE_GUIDE, bsg.getSizeFR());
        values.put(KEY_SIZE_UE_SIZE_GUIDE, bsg.getSizeUE());
        values.put(KEY_SIZE_UK_SIZE_GUIDE, bsg.getSizeUK());
        values.put(KEY_SIZE_US_SIZE_GUIDE, bsg.getSizeUS());
        values.put(KEY_SIZE_SMXL_SIZE_GUIDE, bsg.getSizeSMXL());
        values.put(KEY_SIZE_SUITE_SIZE_GUIDE, bsg.getSizeSuite());
        String where = KEY_ID_BRAND_SIZE_GUIDE+" = ?";
        String[] whereArgs = {bsg.getId_brand_size_guide()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteBrandSizeGuide(BrandsSizeGuide bsg) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_BRAND_SIZE_GUIDE+" = ?";
        String[] whereArgs = {bsg.getId_brand_size_guide()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public BrandsSizeGuide getBrandSizeGuide(int id) {
        // Retourne l'animal dont l'id est passé en paramètre

        BrandsSizeGuide bsg=new BrandsSizeGuide();

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_BRAND_SIZE_GUIDE+"="+id, null);
        if (c.moveToFirst()) {
            bsg.setId_brand_size_guide(c.getInt(c.getColumnIndex(KEY_ID_BRAND_SIZE_GUIDE)));

            /*
            SMXL.getBrandDBManager().open();
            bsg.setBrand(SMXL.getBrandDBManager().getBrand(c.getInt(c.getColumnIndex(KEY_ID_MARQUE_BRAND_SIZE_GUIDE))));
            SMXL.getBrandDBManager().close();

            SMXL.getGarmentTypeDBManager().open();
            bsg.setGarmentType(SMXL.getGarmentTypeDBManager().getGarmentType(c.getInt(c.getColumnIndex(KEY_ID_GARMENT_TYPE_SIZE_GUIDE))));
            SMXL.getGarmentTypeDBManager().close();
*/
            bsg.setBrand(c.getString(c.getColumnIndex(KEY_ID_MARQUE_BRAND_SIZE_GUIDE)));
            bsg.setGarmentType(c.getString(c.getColumnIndex(KEY_ID_GARMENT_TYPE_SIZE_GUIDE)));

            bsg.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY_SIZE_GUIDE)));
            bsg.setSex(c.getString(c.getColumnIndex(KEY_SEX_SIZE_GUIDE)));

            bsg.setBust(convertToDouble(c.getString(c.getColumnIndex(KEY_BUST_SIZE_GUIDE))));
            bsg.setCollar(convertToDouble(c.getString(c.getColumnIndex(KEY_COLLAR_SIZE_GUIDE))));
            bsg.setChest(convertToDouble(c.getString(c.getColumnIndex(KEY_CHEST_SIZE_GUIDE))));
            bsg.setFeet(convertToDouble(c.getString(c.getColumnIndex(KEY_FEET_SIZE_GUIDE))));
            bsg.setHead(convertToDouble(c.getString(c.getColumnIndex(KEY_HEAD_SIZE_GUIDE))));
            bsg.setHeight(convertToDouble(c.getString(c.getColumnIndex(KEY_HEIGHT_SIZE_GUIDE))));
            bsg.setHips(convertToDouble(c.getString(c.getColumnIndex(KEY_HIPS_SIZE_GUIDE))));
            bsg.setInseam(convertToDouble(c.getString(c.getColumnIndex(KEY_INSEAM_SIZE_GUIDE))));
            bsg.setSleeve(convertToDouble(c.getString(c.getColumnIndex(KEY_SLEEVE_SIZE_GUIDE))));
            bsg.setThigh(convertToDouble(c.getString(c.getColumnIndex(KEY_THIGH_SIZE_GUIDE))));
            bsg.setWaist(convertToDouble(c.getString(c.getColumnIndex(KEY_WAIST_SIZE_GUIDE))));
            bsg.setWeight(convertToDouble(c.getString(c.getColumnIndex(KEY_WEIGHT_SIZE_GUIDE))));
            bsg.setOther(convertToDouble(c.getString(c.getColumnIndex(KEY_OTHER_SIZE_GUIDE))));

            bsg.setSizeFR(c.getString(c.getColumnIndex(KEY_SIZE_FR_SIZE_GUIDE)));
            bsg.setSizeUE(c.getString(c.getColumnIndex(KEY_SIZE_UE_SIZE_GUIDE)));
            bsg.setSizeUK(c.getString(c.getColumnIndex(KEY_SIZE_UK_SIZE_GUIDE)));
            bsg.setSizeUS(c.getString(c.getColumnIndex(KEY_SIZE_US_SIZE_GUIDE)));
            bsg.setSizeSMXL(c.getString(c.getColumnIndex(KEY_SIZE_SMXL_SIZE_GUIDE)));
            bsg.setSizeSuite(c.getString(c.getColumnIndex(KEY_SIZE_SUITE_SIZE_GUIDE)));

            c.close();
        }

        return bsg;
    }

    public Cursor getBrandsSizeGuide() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

//    public ArrayList<BrandsSizeGuide> getAllGarmentsByBrand(Brand brand){

    public ArrayList<BrandsSizeGuide> getAllGarmentsByBrand(Brand brand){
        ArrayList<BrandsSizeGuide> garments = new ArrayList<BrandsSizeGuide>();
        Cursor c;
        //c = db.rawQuery("SELECT * FROM"+TABLE_NAME+" WHERE "+ KEY_ID_MARQUE_BRAND_SIZE_GUIDE +" = "+brand.getId_brand(), null);
        c = db.rawQuery("SELECT * FROM"+TABLE_NAME+" WHERE "+ KEY_ID_MARQUE_BRAND_SIZE_GUIDE +" = "+brand, null);

        boolean eof = c.moveToFirst();
        while (eof) {
            BrandsSizeGuide bsg = new BrandsSizeGuide();

            bsg.setId_brand_size_guide(c.getInt(c.getColumnIndex(KEY_ID_BRAND_SIZE_GUIDE)));

/*
            SMXL.getBrandDBManager().open();
            bsg.setBrand(SMXL.getBrandDBManager().getBrand(c.getInt(c.getColumnIndex(KEY_ID_MARQUE_BRAND_SIZE_GUIDE))));
            SMXL.getBrandDBManager().close();

            SMXL.getGarmentTypeDBManager().open();
            bsg.setGarmentType(SMXL.getGarmentTypeDBManager().getGarmentType(c.getInt(c.getColumnIndex(KEY_ID_GARMENT_TYPE_SIZE_GUIDE))));
            SMXL.getGarmentTypeDBManager().close();
*/
            bsg.setBrand(c.getString(c.getColumnIndex(KEY_ID_MARQUE_BRAND_SIZE_GUIDE)));
            bsg.setGarmentType(c.getString(c.getColumnIndex(KEY_ID_GARMENT_TYPE_SIZE_GUIDE)));

            bsg.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY_SIZE_GUIDE)));
            bsg.setSex(c.getString(c.getColumnIndex(KEY_SEX_SIZE_GUIDE)));

            bsg.setBust(convertToDouble(c.getString(c.getColumnIndex(KEY_BUST_SIZE_GUIDE))));
            bsg.setCollar(convertToDouble(c.getString(c.getColumnIndex(KEY_COLLAR_SIZE_GUIDE))));
            bsg.setChest(convertToDouble(c.getString(c.getColumnIndex(KEY_CHEST_SIZE_GUIDE))));
            bsg.setFeet(convertToDouble(c.getString(c.getColumnIndex(KEY_FEET_SIZE_GUIDE))));
            bsg.setHead(convertToDouble(c.getString(c.getColumnIndex(KEY_HEAD_SIZE_GUIDE))));
            bsg.setHeight(convertToDouble(c.getString(c.getColumnIndex(KEY_HEIGHT_SIZE_GUIDE))));
            bsg.setHips(convertToDouble(c.getString(c.getColumnIndex(KEY_HIPS_SIZE_GUIDE))));
            bsg.setInseam(convertToDouble(c.getString(c.getColumnIndex(KEY_INSEAM_SIZE_GUIDE))));
            bsg.setSleeve(convertToDouble(c.getString(c.getColumnIndex(KEY_SLEEVE_SIZE_GUIDE))));
            bsg.setThigh(convertToDouble(c.getString(c.getColumnIndex(KEY_THIGH_SIZE_GUIDE))));
            bsg.setWaist(convertToDouble(c.getString(c.getColumnIndex(KEY_WAIST_SIZE_GUIDE))));
            bsg.setWeight(convertToDouble(c.getString(c.getColumnIndex(KEY_WEIGHT_SIZE_GUIDE))));
            bsg.setOther(convertToDouble(c.getString(c.getColumnIndex(KEY_OTHER_SIZE_GUIDE))));

            bsg.setSizeFR(c.getString(c.getColumnIndex(KEY_SIZE_FR_SIZE_GUIDE)));
            bsg.setSizeUE(c.getString(c.getColumnIndex(KEY_SIZE_UE_SIZE_GUIDE)));
            bsg.setSizeUK(c.getString(c.getColumnIndex(KEY_SIZE_UK_SIZE_GUIDE)));
            bsg.setSizeUS(c.getString(c.getColumnIndex(KEY_SIZE_US_SIZE_GUIDE)));
            bsg.setSizeSMXL(c.getString(c.getColumnIndex(KEY_SIZE_SMXL_SIZE_GUIDE)));
            bsg.setSizeSuite(c.getString(c.getColumnIndex(KEY_SIZE_SUITE_SIZE_GUIDE)));

            garments.add(bsg);
            eof = c.moveToNext();
        }
        c.close();

        return garments;
    }

    //FIXME
    public ArrayList<BrandsSizeGuide> getAllBrandsByGarment(String garmentType){
        ArrayList<BrandsSizeGuide> brands = new ArrayList<>();
        Cursor c;
        //TODO
        c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_GARMENT_TYPE_SIZE_GUIDE+" = "+ garmentType, null);
        boolean eof = c.moveToFirst();
        while (eof) {
            BrandsSizeGuide bsg = new BrandsSizeGuide();

            bsg.setId_brand_size_guide(c.getInt(c.getColumnIndex(KEY_ID_BRAND_SIZE_GUIDE)));
/*
            SMXL.getBrandDBManager().open();
            bsg.setBrand(SMXL.getBrandDBManager().getBrand(c.getInt(c.getColumnIndex(KEY_ID_MARQUE_BRAND_SIZE_GUIDE))));
            SMXL.getBrandDBManager().close();

            SMXL.getGarmentTypeDBManager().open();
            bsg.setGarmentType(SMXL.getGarmentTypeDBManager().getGarmentType(c.getInt(c.getColumnIndex(KEY_ID_GARMENT_TYPE_SIZE_GUIDE))));
            SMXL.getGarmentTypeDBManager().close();
*/
            bsg.setBrand(c.getString(c.getColumnIndex(KEY_ID_MARQUE_BRAND_SIZE_GUIDE)));
            bsg.setGarmentType(c.getString(c.getColumnIndex(KEY_ID_GARMENT_TYPE_SIZE_GUIDE)));

            bsg.setCategory(c.getString(c.getColumnIndex(KEY_CATEGORY_SIZE_GUIDE)));
            bsg.setSex(c.getString(c.getColumnIndex(KEY_SEX_SIZE_GUIDE)));

            bsg.setBust(convertToDouble(c.getString(c.getColumnIndex(KEY_BUST_SIZE_GUIDE))));
            bsg.setCollar(convertToDouble(c.getString(c.getColumnIndex(KEY_COLLAR_SIZE_GUIDE))));
            bsg.setChest(convertToDouble(c.getString(c.getColumnIndex(KEY_CHEST_SIZE_GUIDE))));
            bsg.setFeet(convertToDouble(c.getString(c.getColumnIndex(KEY_FEET_SIZE_GUIDE))));
            bsg.setHead(convertToDouble(c.getString(c.getColumnIndex(KEY_HEAD_SIZE_GUIDE))));
            bsg.setHeight(convertToDouble(c.getString(c.getColumnIndex(KEY_HEIGHT_SIZE_GUIDE))));
            bsg.setHips(convertToDouble(c.getString(c.getColumnIndex(KEY_HIPS_SIZE_GUIDE))));
            bsg.setInseam(convertToDouble(c.getString(c.getColumnIndex(KEY_INSEAM_SIZE_GUIDE))));
            bsg.setSleeve(convertToDouble(c.getString(c.getColumnIndex(KEY_SLEEVE_SIZE_GUIDE))));
            bsg.setThigh(convertToDouble(c.getString(c.getColumnIndex(KEY_THIGH_SIZE_GUIDE))));
            bsg.setWaist(convertToDouble(c.getString(c.getColumnIndex(KEY_WAIST_SIZE_GUIDE))));
            bsg.setWeight(convertToDouble(c.getString(c.getColumnIndex(KEY_WEIGHT_SIZE_GUIDE))));
            bsg.setOther(convertToDouble(c.getString(c.getColumnIndex(KEY_OTHER_SIZE_GUIDE))));

            bsg.setSizeFR(c.getString(c.getColumnIndex(KEY_SIZE_FR_SIZE_GUIDE)));
            bsg.setSizeUE(c.getString(c.getColumnIndex(KEY_SIZE_UE_SIZE_GUIDE)));
            bsg.setSizeUK(c.getString(c.getColumnIndex(KEY_SIZE_UK_SIZE_GUIDE)));
            bsg.setSizeUS(c.getString(c.getColumnIndex(KEY_SIZE_US_SIZE_GUIDE)));
            bsg.setSizeSMXL(c.getString(c.getColumnIndex(KEY_SIZE_SMXL_SIZE_GUIDE)));
            bsg.setSizeSuite(c.getString(c.getColumnIndex(KEY_SIZE_SUITE_SIZE_GUIDE)));

            brands.add(bsg);
            eof = c.moveToNext();
        }
        c.close();
        return brands;
    }


} // class BrandSizeGuideDBManager
