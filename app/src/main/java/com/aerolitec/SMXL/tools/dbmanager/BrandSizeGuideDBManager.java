package com.aerolitec.SMXL.tools.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.BrandSizeGuideMeasuresRow;
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
    public static final String KEY_ID_GARMENT_TYPE_SIZE_GUIDE ="id_garment";
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
    public static final String KEY_SIZE_SUIT_SIZE_GUIDE ="size_suit";


    public static final String CREATE_TABLE_BRAND_SIZE_GUIDE = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_BRAND_SIZE_GUIDE+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+ KEY_ID_MARQUE_BRAND_SIZE_GUIDE +" INTEGER" +
            " "+ KEY_ID_GARMENT_TYPE_SIZE_GUIDE +" INTEGER" +
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
            " "+ KEY_SIZE_SUIT_SIZE_GUIDE +" TEXT" +
            " FOREIGN KEY("+KEY_ID_MARQUE_BRAND_SIZE_GUIDE+") REFERENCES "+ BrandDBManager.TABLE_NAME +","+
            " FOREIGN KEY("+KEY_ID_GARMENT_TYPE_SIZE_GUIDE+") REFERENCES "+ GarmentTypeDBManager.TABLE_NAME +
            ");";

    // Constructeur
    public BrandSizeGuideDBManager(Context context)
    {
        super(context);
    }

    public long addBrandSizeGuide(BrandsSizeGuide bsg){
        // Ajout d'un enregistrement dans la table
        open();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_MARQUE_BRAND_SIZE_GUIDE, bsg.getBrand().getId_brand());
        values.put(KEY_ID_GARMENT_TYPE_SIZE_GUIDE, bsg.getGarmentType().getId_garment_type());
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
        values.put(KEY_SIZE_SUIT_SIZE_GUIDE, bsg.getSizeSuite());

        long i = db.insert(TABLE_NAME, null, values);
        close();
        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return i;
    }

    public int updateBrandSizeGuide(BrandsSizeGuide bsg) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête
        open();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_MARQUE_BRAND_SIZE_GUIDE, bsg.getBrand().getId_brand());
        values.put(KEY_ID_GARMENT_TYPE_SIZE_GUIDE, bsg.getGarmentType().getId_garment_type());
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
        values.put(KEY_SIZE_SUIT_SIZE_GUIDE, bsg.getSizeSuite());
        String where = KEY_ID_BRAND_SIZE_GUIDE+" = ?";
        String[] whereArgs = {bsg.getId_brand_size_guide()+""};

        int i = db.update(TABLE_NAME, values, where, whereArgs);
        close();
        return i;
    }

    public int deleteBrandSizeGuide(BrandsSizeGuide bsg) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon
        open();
        String where = KEY_ID_BRAND_SIZE_GUIDE+" = ?";
        String[] whereArgs = {bsg.getId_brand_size_guide()+""};

        int i = db.delete(TABLE_NAME, where, whereArgs);
        close();
        return i;
    }

    public BrandsSizeGuide getBrandSizeGuide(int id) {
        // Retourne l'animal dont l'id est passé en paramètre
        open();
        BrandsSizeGuide bsg=new BrandsSizeGuide();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID_BRAND_SIZE_GUIDE + "=" + id, null);
        if (c.moveToFirst()) {
            bsg.setId_brand_size_guide(c.getInt(c.getColumnIndex(KEY_ID_BRAND_SIZE_GUIDE)));

            bsg.setBrand(SMXL.getBrandDBManager().getBrand(c.getInt(c.getColumnIndex(KEY_ID_MARQUE_BRAND_SIZE_GUIDE))));
            bsg.setGarmentType(SMXL.getGarmentTypeDBManager().getGarmentType(c.getInt(c.getColumnIndex(KEY_ID_GARMENT_TYPE_SIZE_GUIDE))));

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
            bsg.setSizeSuite(c.getString(c.getColumnIndex(KEY_SIZE_SUIT_SIZE_GUIDE)));

            c.close();
        }
        close();
        return bsg;
    }

/*    public Cursor getBrandsSizeGuide() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }*/


    public ArrayList<BrandsSizeGuide> getAllGarmentsByBrand(Brand brand){
        open();
        ArrayList<BrandsSizeGuide> garments = new ArrayList<BrandsSizeGuide>();
        Cursor c;
        c = db.rawQuery("SELECT * FROM"+TABLE_NAME+" WHERE "+ KEY_ID_MARQUE_BRAND_SIZE_GUIDE +" = "+brand.getId_brand(), null);

        boolean eof = c.moveToFirst();
        while (eof) {
            BrandsSizeGuide bsg = new BrandsSizeGuide();

            bsg.setId_brand_size_guide(c.getInt(c.getColumnIndex(KEY_ID_BRAND_SIZE_GUIDE)));

            bsg.setBrand(SMXL.getBrandDBManager().getBrand(c.getInt(c.getColumnIndex(KEY_ID_MARQUE_BRAND_SIZE_GUIDE))));
            bsg.setGarmentType(SMXL.getGarmentTypeDBManager().getGarmentType(c.getInt(c.getColumnIndex(KEY_ID_GARMENT_TYPE_SIZE_GUIDE))));

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
            bsg.setSizeSuite(c.getString(c.getColumnIndex(KEY_SIZE_SUIT_SIZE_GUIDE)));

            garments.add(bsg);
            eof = c.moveToNext();
        }
        c.close();
        close();
        return garments;
    }


    public ArrayList<Brand> getAllBrandsByGarment(GarmentType garmentType){
        open();
        ArrayList<Brand> brands = new ArrayList<>();
        Cursor c;
        c = db.rawQuery("SELECT DISTINCT "+KEY_ID_MARQUE_BRAND_SIZE_GUIDE +
                        " FROM "+TABLE_NAME+
                        " WHERE "+KEY_ID_GARMENT_TYPE_SIZE_GUIDE+" = "+ garmentType.getId_garment_type(), null);
        boolean eof = c.moveToFirst();
        while (eof) {
            Brand brand = SMXL.getBrandDBManager().getBrand(c.getInt(c.getColumnIndex(KEY_ID_MARQUE_BRAND_SIZE_GUIDE)));

            brands.add(brand);
            eof = c.moveToNext();
        }
        c.close();
        close();
        return brands;
    }

//    public ArrayList<Brand> getAllUserBrandsByGarment(GarmentType garmentType,User user){
//        open();
//        ArrayList<Brand> brands = new ArrayList<>();
//        Cursor c;
//        c = db.rawQuery("SELECT DISTINCT "+KEY_ID_MARQUE_BRAND_SIZE_GUIDE +
//                " FROM "+TABLE_NAME+
//                " WHERE "+KEY_ID_GARMENT_TYPE_SIZE_GUIDE+" = "+ garmentType.getId_garment_type(), null);
//        boolean eof = c.moveToFirst();
//        while (eof) {
//            Brand brand = SMXL.getUserBrandDBManager().getUserBrandById(c.getInt(c.getColumnIndex(KEY_ID_MARQUE_BRAND_SIZE_GUIDE)), user);
//
//            brands.add(brand);
//            eof = c.moveToNext();
//        }
//        c.close();
//        close();
//        return brands;
//    }


    // /!\ PEUT retourner null
    public Brand getBrandByIdAndGarment(GarmentType garmentType, int brandId){
        open();
        Brand brand= null;
        Cursor c;
        c = db.rawQuery("SELECT DISTINCT "+KEY_ID_MARQUE_BRAND_SIZE_GUIDE +
                " FROM "+TABLE_NAME+
                " WHERE "+KEY_ID_GARMENT_TYPE_SIZE_GUIDE+" = "+ garmentType.getId_garment_type()+" AND "+KEY_ID_MARQUE_BRAND_SIZE_GUIDE+" = "+brandId, null);
        if(c.moveToFirst()){
            brand = SMXL.getBrandDBManager().getBrand(c.getInt(c.getColumnIndex(KEY_ID_MARQUE_BRAND_SIZE_GUIDE)));
        }
        c.close();
        close();
        return brand;
    }


    public ArrayList<BrandSizeGuideMeasuresRow> getBrandSizeGuideMeasureRowsByBrandAndGarmentType(Brand brand,GarmentType garmentType){
        open();
        ArrayList<BrandSizeGuideMeasuresRow> brandSizeGuideMeasuresRows= new ArrayList<>();
        BrandSizeGuideMeasuresRow brandSizeGuideMeasuresRow;
        Cursor c;
        Log.d("bsgDBManager", garmentType.toString());
        Log.d("bsgDBManager", brand.toString());

        c= db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_MARQUE_BRAND_SIZE_GUIDE+" = "+brand.getId_brand()+" AND "+KEY_ID_GARMENT_TYPE_SIZE_GUIDE+" = "+garmentType.getId_garment_type(),null);
        boolean eof = c.moveToFirst();
        while(eof){
            brandSizeGuideMeasuresRow = new BrandSizeGuideMeasuresRow();

            brandSizeGuideMeasuresRow.setBust(convertToDouble(c.getString(c.getColumnIndex(KEY_BUST_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setCollar(convertToDouble(c.getString(c.getColumnIndex(KEY_COLLAR_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setChest(convertToDouble(c.getString(c.getColumnIndex(KEY_CHEST_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setFeet(convertToDouble(c.getString(c.getColumnIndex(KEY_FEET_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setHeight(convertToDouble(c.getString(c.getColumnIndex(KEY_HEIGHT_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setHips(convertToDouble(c.getString(c.getColumnIndex(KEY_HIPS_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setInseam(convertToDouble(c.getString(c.getColumnIndex(KEY_INSEAM_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setSleeve(convertToDouble(c.getString(c.getColumnIndex(KEY_SLEEVE_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setThigh(convertToDouble(c.getString(c.getColumnIndex(KEY_THIGH_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setWaist(convertToDouble(c.getString(c.getColumnIndex(KEY_WAIST_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setWeight(convertToDouble(c.getString(c.getColumnIndex(KEY_WEIGHT_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setOther(convertToDouble(c.getString(c.getColumnIndex(KEY_OTHER_SIZE_GUIDE))));

            brandSizeGuideMeasuresRow.setSizeFR(c.getString(c.getColumnIndex(KEY_SIZE_FR_SIZE_GUIDE)));
            brandSizeGuideMeasuresRow.setSizeUE(c.getString(c.getColumnIndex(KEY_SIZE_UE_SIZE_GUIDE)));
            brandSizeGuideMeasuresRow.setSizeUK(c.getString(c.getColumnIndex(KEY_SIZE_UK_SIZE_GUIDE)));
            brandSizeGuideMeasuresRow.setSizeUS(c.getString(c.getColumnIndex(KEY_SIZE_US_SIZE_GUIDE)));
            brandSizeGuideMeasuresRow.setSizeSMXL(c.getString(c.getColumnIndex(KEY_SIZE_SMXL_SIZE_GUIDE)));
            brandSizeGuideMeasuresRow.setSizeSuite(c.getString(c.getColumnIndex(KEY_SIZE_SUIT_SIZE_GUIDE)));

            brandSizeGuideMeasuresRows.add(brandSizeGuideMeasuresRow);

            eof = c.moveToNext();
        }

        c.close();
        close();

        return brandSizeGuideMeasuresRows;

    }

    //Get only one row
    public BrandSizeGuideMeasuresRow getBrandSizeGuideMeasureRowByBrandAndGarmentType(Brand brand,GarmentType garmentType){
        open();
        BrandSizeGuideMeasuresRow brandSizeGuideMeasuresRow=null;
        Cursor c;
        Log.d("bsgDBManager", garmentType.toString());
        Log.d("bsgDBManager", brand.toString());

        c= db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_MARQUE_BRAND_SIZE_GUIDE+" = "+brand.getId_brand()+" AND "+KEY_ID_GARMENT_TYPE_SIZE_GUIDE+" = "+garmentType.getId_garment_type()+" LIMIT 1",null);
        if(c.moveToFirst()){
            brandSizeGuideMeasuresRow = new BrandSizeGuideMeasuresRow();

            brandSizeGuideMeasuresRow.setBust(convertToDouble(c.getString(c.getColumnIndex(KEY_BUST_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setCollar(convertToDouble(c.getString(c.getColumnIndex(KEY_COLLAR_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setChest(convertToDouble(c.getString(c.getColumnIndex(KEY_CHEST_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setFeet(convertToDouble(c.getString(c.getColumnIndex(KEY_FEET_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setHeight(convertToDouble(c.getString(c.getColumnIndex(KEY_HEIGHT_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setHips(convertToDouble(c.getString(c.getColumnIndex(KEY_HIPS_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setInseam(convertToDouble(c.getString(c.getColumnIndex(KEY_INSEAM_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setSleeve(convertToDouble(c.getString(c.getColumnIndex(KEY_SLEEVE_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setThigh(convertToDouble(c.getString(c.getColumnIndex(KEY_THIGH_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setWaist(convertToDouble(c.getString(c.getColumnIndex(KEY_WAIST_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setWeight(convertToDouble(c.getString(c.getColumnIndex(KEY_WEIGHT_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setOther(convertToDouble(c.getString(c.getColumnIndex(KEY_OTHER_SIZE_GUIDE))));

            brandSizeGuideMeasuresRow.setSizeFR(c.getString(c.getColumnIndex(KEY_SIZE_FR_SIZE_GUIDE)));
            brandSizeGuideMeasuresRow.setSizeUE(c.getString(c.getColumnIndex(KEY_SIZE_UE_SIZE_GUIDE)));
            brandSizeGuideMeasuresRow.setSizeUK(c.getString(c.getColumnIndex(KEY_SIZE_UK_SIZE_GUIDE)));
            brandSizeGuideMeasuresRow.setSizeUS(c.getString(c.getColumnIndex(KEY_SIZE_US_SIZE_GUIDE)));
            brandSizeGuideMeasuresRow.setSizeSMXL(c.getString(c.getColumnIndex(KEY_SIZE_SMXL_SIZE_GUIDE)));
            brandSizeGuideMeasuresRow.setSizeSuite(c.getString(c.getColumnIndex(KEY_SIZE_SUIT_SIZE_GUIDE)));
        }

        c.close();
        close();

        return brandSizeGuideMeasuresRow;

    }


    // Récupère toutes les tailles entrées dans la bdd pour un GarmentType , une Brand et un country donné
    public ArrayList<String> getSizeListByBrandAndGarmentTypeAndCountry(Brand brand , GarmentType garmentType , String country){
        open();
        ArrayList<String> sizeList = new ArrayList<>();
        String countrySize = "size_"+ country;
        Cursor c;
        c = db.rawQuery("SELECT DISTINCT "+countrySize +
                " FROM "+TABLE_NAME+
                " WHERE "+KEY_ID_GARMENT_TYPE_SIZE_GUIDE+" = "+ garmentType.getId_garment_type()+" AND "+KEY_ID_MARQUE_BRAND_SIZE_GUIDE+" = "+brand.getId_brand(), null);
        boolean eof = c.moveToFirst();
        while(eof) {
            sizeList.add(c.getString(c.getColumnIndex(countrySize)));
            eof = c.moveToNext();
        }
        close();
        return sizeList;
    }

    public ArrayList<BrandSizeGuideMeasuresRow> getBrandSizeGuideMeasureRowsByBrandAndGarmentTypeAndCountryAndSize(Brand brand,GarmentType garmentType,String country , String size ){
        open();
        ArrayList<BrandSizeGuideMeasuresRow> brandSizeGuideMeasuresRows= new ArrayList<>();
        BrandSizeGuideMeasuresRow brandSizeGuideMeasuresRow;
        Cursor c;
        Log.d("bsgDBManager", garmentType.toString());
        Log.d("bsgDBManager", brand.toString());
        String countrySize = "size_"+country;
        c= db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_MARQUE_BRAND_SIZE_GUIDE+" = "+brand.getId_brand()
                +" AND "+KEY_ID_GARMENT_TYPE_SIZE_GUIDE+" = "+garmentType.getId_garment_type() +" AND "+countrySize+" = '"+size+"'",null);
        boolean eof = c.moveToFirst();
        while(eof){
            brandSizeGuideMeasuresRow = new BrandSizeGuideMeasuresRow();

            brandSizeGuideMeasuresRow.setBust(convertToDouble(c.getString(c.getColumnIndex(KEY_BUST_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setCollar(convertToDouble(c.getString(c.getColumnIndex(KEY_COLLAR_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setChest(convertToDouble(c.getString(c.getColumnIndex(KEY_CHEST_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setFeet(convertToDouble(c.getString(c.getColumnIndex(KEY_FEET_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setHeight(convertToDouble(c.getString(c.getColumnIndex(KEY_HEIGHT_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setHips(convertToDouble(c.getString(c.getColumnIndex(KEY_HIPS_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setInseam(convertToDouble(c.getString(c.getColumnIndex(KEY_INSEAM_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setSleeve(convertToDouble(c.getString(c.getColumnIndex(KEY_SLEEVE_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setThigh(convertToDouble(c.getString(c.getColumnIndex(KEY_THIGH_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setWaist(convertToDouble(c.getString(c.getColumnIndex(KEY_WAIST_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setWeight(convertToDouble(c.getString(c.getColumnIndex(KEY_WEIGHT_SIZE_GUIDE))));
            brandSizeGuideMeasuresRow.setOther(convertToDouble(c.getString(c.getColumnIndex(KEY_OTHER_SIZE_GUIDE))));

            brandSizeGuideMeasuresRow.setSizeFR(c.getString(c.getColumnIndex(KEY_SIZE_FR_SIZE_GUIDE)));
            brandSizeGuideMeasuresRow.setSizeUE(c.getString(c.getColumnIndex(KEY_SIZE_UE_SIZE_GUIDE)));
            brandSizeGuideMeasuresRow.setSizeUK(c.getString(c.getColumnIndex(KEY_SIZE_UK_SIZE_GUIDE)));
            brandSizeGuideMeasuresRow.setSizeUS(c.getString(c.getColumnIndex(KEY_SIZE_US_SIZE_GUIDE)));
            brandSizeGuideMeasuresRow.setSizeSMXL(c.getString(c.getColumnIndex(KEY_SIZE_SMXL_SIZE_GUIDE)));
            brandSizeGuideMeasuresRow.setSizeSuite(c.getString(c.getColumnIndex(KEY_SIZE_SUIT_SIZE_GUIDE)));

            brandSizeGuideMeasuresRows.add(brandSizeGuideMeasuresRow);

            eof = c.moveToNext();
        }

        c.close();
        close();

        return brandSizeGuideMeasuresRows;

    }
} // class BrandSizeGuideDBManager
