package com.aerolitec.SMXL.tools.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.MenuInflater;

import com.aerolitec.SMXL.model.Brands;
import com.aerolitec.SMXL.model.BrandsSizeGuide;
import com.aerolitec.SMXL.model.CategoryGarment;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.SizeConvert;
import com.aerolitec.SMXL.model.TabSizes;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.model.UserClothes;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by stephaneL on 20/03/14.
 */
public class SizeGuideDataBase extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "SIZEGUIDE_DB";

    public SizeGuideDataBase(Context context){
        super(context, DATABASE_NAME, null, 1);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM user_clothes", null);
            if(c.getColumnCount() < 9) {
                db.execSQL("DROP TABLE IF EXISTS user_clothes");
                String statement = "CREATE TABLE IF NOT EXISTS user_clothes ( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "userid INTEGER, " +
                        "type VARCHAR(32), " +
                        "brand VARCHAR(32), " +
                        "country VARCHAR(8), " +
                        "size VARCHAR(8)," +
                        "comment VARCHAR(1000), " +
                        "sizes VARCHAR(1000), " +
                        "category VARCHAR(1000)); ";
                db.execSQL(statement);
            }
            c.close();
        }
        catch (SQLiteException e) {
        }
        db.close();

    }

    public void onCreate(SQLiteDatabase db) {
        Cursor c = null;
        String statement = "";
        if (db == null) {
            db = this.getWritableDatabase();
        }
        if (db == null) {
            Log.e(Constants.TAG,"CAN'T OPEN OR CREATE DATA BASE");
        }


        //db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS brands");
        db.execSQL("DROP TABLE IF EXISTS clothe_type");
        db.execSQL("DROP TABLE IF EXISTS brand_size_guide");


        // Creation user table
        try {
            statement = "CREATE TABLE IF NOT EXISTS user (userid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nickname VARCHAR(32), " +
                    "firstname VARCHAR(32), " +
                    "lastname VARCHAR(32), " +
                    "birthday VARCHAR(32), " +
                    "sexe VARCHAR(1), " +
                    "avatar VARCHAR(128), " +
                    "description VARCHAR(128), " +
                    "size REAL, " +
                    "weight REAL, " +
                    "chest REAL, " +
                    "collar REAL, " +
                    "bust REAL, " +
                    "waist REAL, " +
                    "hips REAL, " +
                    "sleeve REAL, " +
                    "inseam REAL, " +
                    "feet REAL, "+
                    "unitL INTEGER, "+
                    "unitW INTEGER, "+
                    "pointure REAL);";
            db.execSQL(statement);

            statement = "CREATE TABLE IF NOT EXISTS user_clothes ( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "userid INTEGER, " +
                    "type VARCHAR(32), " +
                    "brand VARCHAR(32), " +
                    "country VARCHAR(8), " +
                    "size VARCHAR(8)," +
                    "comment VARCHAR(1000), " +
                    "sizes VARCHAR(1000), " +
                    "category VARCHAR(1000)); ";
            db.execSQL(statement);

            statement = "CREATE TABLE IF NOT EXISTS clothe_type ( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "type VARCHAR(32)," +
                    "sexe VARCHAR(8)); ";
            db.execSQL(statement);

            statement = "CREATE TABLE IF NOT EXISTS brands ( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "brand VARCHAR(32)); ";
            db.execSQL(statement);

            statement = "CREATE TABLE IF NOT EXISTS size_convert ( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "type VARCHAR(32), " +
                    "valueUS VARCHAR(8), " +
                    "valueUK VARCHAR(8), " +
                    "valueUE VARCHAR(8), " +
                    "valueFR VARCHAR(8), " +
                    "valueITA VARCHAR(8), " +
                    "valueJAP VARCHAR(8), " +
                    "valueAUS VARCHAR(8)); ";
            db.execSQL(statement);

            statement = "CREATE TABLE IF NOT EXISTS brand_size_guide ( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "brand VARCHAR(32), " +
                    "type VARCHAR(32), " +
                    "dim1 REAL, " +
                    "dim2 REAL, " +
                    "dim3 REAL, " +
                    "size VARCHAR(8)); ";
            db.execSQL(statement);
        }
        catch (Exception e){
            Log.d(Constants.TAG,"Erreur Sql : "+e.getMessage());
        }


        // Update table ClotheType if empty
        c = db.rawQuery("select * from clothe_type",null);
        if (c.getCount() < 2){
            updateTableClotheType(db);
        }
        c.close();

        // Update table brands if empty
        c = db.rawQuery("select * from brands",null);
        if (c.getCount() < 2){
            updateTableBrands(db);
        }
        c.close();

        // Update table size_convert if empty
        c = db.rawQuery("select * from size_convert",null);
        if (c.getCount() < 2){
            updateTableSizeConvert(db);
        }
        c.close();

        // Update table brand_size_guide if empty
        c = db.rawQuery("select * from brand_size_guide",null);
        if (c.getCount() < 2){
            updateTableBrandSizeGuide(db);
        }
        c.close();

/**        try {
            if (db.isOpen())
                db.close();
        }
        catch (Exception e){
            Log.e(Constants.TAG,"PB CLOSE DATABASE");
        }
 /**/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {

    }

    // Methods to initialize the content of the reference tables
    //
    private void updateTableClotheType(SQLiteDatabase db){
        // "NOMENCLATURE VETEMENTS" + SEXE/AGE CONCERNE : Femme, Homme, Girl, Boy, Child, babY, All (F,H,G,B,C,Y,A)
        createRecordClotheType(db,"BLOUSON","F");
        createRecordClotheType(db,"BLOUSON","H");
        createRecordClotheType(db,"BLOUSON","G");
        createRecordClotheType(db,"BLOUSON","B");
        createRecordClotheType(db,"BLOUSON","C");
        createRecordClotheType(db,"BLOUSON","Y");
        createRecordClotheType(db,"CHAPEAU","A");
        createRecordClotheType(db,"CHAUSSURE","H");
        createRecordClotheType(db,"CHAUSSURE","F");
        createRecordClotheType(db,"CHAUSSURE","G");
        createRecordClotheType(db,"CHAUSSURE","B");
        createRecordClotheType(db,"CHAUSSURE","C");
        createRecordClotheType(db,"CHAUSSURE","Y");
        createRecordClotheType(db,"CHEMISE","H");
        createRecordClotheType(db,"CHEMISE","F");
        createRecordClotheType(db,"CHEMISE","G");
        createRecordClotheType(db,"CHEMISE","B");
        createRecordClotheType(db,"CHEMISE","C");
        createRecordClotheType(db,"CHEMISE","Y");
        createRecordClotheType(db,"CHEMISIER","F");
        createRecordClotheType(db,"CHEMISIER","G");
        createRecordClotheType(db,"CHEMISIER","C");
        createRecordClotheType(db,"CHEMISIER","Y");
        createRecordClotheType(db,"COSTUME","H");
        createRecordClotheType(db,"COSTUME","B");
        createRecordClotheType(db,"JEANS","H");
        createRecordClotheType(db,"JEANS","F");
        createRecordClotheType(db,"JEANS","G");
        createRecordClotheType(db,"JEANS","B");
        createRecordClotheType(db,"JEANS","C");
        createRecordClotheType(db,"JEANS","Y");
        createRecordClotheType(db,"JUPE","F");
        createRecordClotheType(db,"JUPE","G");
        createRecordClotheType(db,"MANTEAU","H");
        createRecordClotheType(db,"MANTEAU","F");
        createRecordClotheType(db,"MANTEAU","G");
        createRecordClotheType(db,"MANTEAU","B");
        createRecordClotheType(db,"MANTEAU","C");
        createRecordClotheType(db,"MANTEAU","Y");
        createRecordClotheType(db,"PANTALON","H");
        createRecordClotheType(db,"PANTALON","F");
        createRecordClotheType(db,"PANTALON","G");
        createRecordClotheType(db,"PANTALON","B");
        createRecordClotheType(db,"PANTALON","C");
        createRecordClotheType(db,"PANTALON","Y");
        createRecordClotheType(db,"PULL","H");
        createRecordClotheType(db,"PULL","F");
        createRecordClotheType(db,"PULL","G");
        createRecordClotheType(db,"PULL","B");
        createRecordClotheType(db,"PULL","C");
        createRecordClotheType(db,"PULL","Y");
        createRecordClotheType(db,"ROBE","F");
        createRecordClotheType(db,"ROBE","G");
        createRecordClotheType(db,"SHORT","H");
        createRecordClotheType(db,"SHORT","F");
        createRecordClotheType(db,"SHORT","B");
        createRecordClotheType(db,"SHORT","G");
        createRecordClotheType(db,"SHORT","C");
        createRecordClotheType(db,"SLIP","H");
        createRecordClotheType(db,"SLIP","F");
        createRecordClotheType(db,"SLIP","G");
        createRecordClotheType(db,"SLIP","B");
        createRecordClotheType(db,"SLIP","Y");
        createRecordClotheType(db,"SOUTIEN-GORGE","F");
        createRecordClotheType(db,"SOUTIEN-GORGE","G");
        createRecordClotheType(db,"SWEATER","H");
        createRecordClotheType(db,"SWEATER","F");
        createRecordClotheType(db,"SWEATER","G");
        createRecordClotheType(db,"SWEATER","B");
        createRecordClotheType(db,"SWEATER","C");
        createRecordClotheType(db,"SWEATER","Y");
        createRecordClotheType(db,"TSHIRT","H");
        createRecordClotheType(db,"TSHIRT","F");
        createRecordClotheType(db,"TSHIRT","G");
        createRecordClotheType(db,"TSHIRT","B");
        createRecordClotheType(db,"TSHIRT","C");
        createRecordClotheType(db,"TSHIRT","Y");
        createRecordClotheType(db,"VESTE","H");
        createRecordClotheType(db,"VESTE","F");
        createRecordClotheType(db,"VESTE","G");
        createRecordClotheType(db,"VESTE","B");
        createRecordClotheType(db,"VESTE","C");
        createRecordClotheType(db,"VESTE","Y");
        createRecordClotheType(db,"VESTE","Y");
        createRecordClotheType(db,"MAILLOTS DE BAIN","F");

    }

    private void updateTableBrands(SQLiteDatabase db){
        // Definir toutes les marques utilisees dans l'application, quel que soit le type de vetement
        createRecordBrands(db, "DEFAULT");
        createRecordBrands(db, "ASOS");
        createRecordBrands(db, "KIABI");
        createRecordBrands(db, "ADIDAS");
        createRecordBrands(db, "NIKE");
        createRecordBrands(db, "H&M");
        createRecordBrands(db, "ZARA");
        createRecordBrands(db, "DESIGUAL");
        createRecordBrands(db, "MANGO");
        createRecordBrands(db, "KOOKAI");
        createRecordBrands(db, "KOOPLES");
        createRecordBrands(db, "PROMOD");
        createRecordBrands(db, "SANDRO");
        createRecordBrands(db, "DEVRED");
        createRecordBrands(db, "CELIO");
    }

    private void updateTableSizeConvert(SQLiteDatabase db){
                            // id, Type,   US, UK,  EU,  FR, ITA, JAP, LET
        // TABLE DE CONVERSION DES TAILLES PAR TYPE DE VETEMENTS: Conversion generique internationale
        // Voir
        // www.guidedestailles.com
        // http://convertisseur.kingconv.com/taille-vetement

        createRecordSizeConvert(db,"ROBE","2","4","32","32","36","5","XXS");
        createRecordSizeConvert(db,"ROBE","4","6","34","34","38","7","XS");
        createRecordSizeConvert(db,"ROBE","6","8","36","36","40","9","S");
        createRecordSizeConvert(db,"ROBE","8","10","38","38","42","11","M");
        createRecordSizeConvert(db,"ROBE","10","12","40","40","44","13","L");
        createRecordSizeConvert(db,"ROBE","12","14","42","42","46","15","XL");
        createRecordSizeConvert(db,"ROBE","14","16","44","44","48","17","XXL");
        createRecordSizeConvert(db,"ROBE","16","18","46","46","50","19","XXXL");
        createRecordSizeConvert(db,"ROBE","18","20","48","48","52","21","XXXL");
        createRecordSizeConvert(db,"ROBE","20","22","50","50","54","23","XXXL");
        createRecordSizeConvert(db,"ROBE","22","24","52","52","56","25","XXXL");
        createRecordSizeConvert(db,"ROBE","24","26","54","54","58","27","XXXL");

        createRecordSizeConvert(db,"JUPE","2","4","32","32","36","5","XXS");
        createRecordSizeConvert(db,"JUPE","4","6","34","34","38","7","XS");
        createRecordSizeConvert(db,"JUPE","6","8","36","36","40","9","S");
        createRecordSizeConvert(db,"JUPE","8","10","38","38","42","11","M");
        createRecordSizeConvert(db,"JUPE","10","12","40","40","44","13","L");
        createRecordSizeConvert(db,"JUPE","12","14","42","42","46","15","X");
        createRecordSizeConvert(db,"JUPE","14","16","44","44","48","17","XL");
        createRecordSizeConvert(db,"JUPE","16","18","46","46","50","19","XXL");
        createRecordSizeConvert(db,"JUPE","18","20","48","48","52","21","XXXL");
        createRecordSizeConvert(db,"JUPE","20","22","50","50","54","23","XXXL");
        createRecordSizeConvert(db,"JUPE","22","24","52","52","56","25","XXXL");
        createRecordSizeConvert(db,"JUPE","24","26","54","54","58","27","XXXL");

        createRecordSizeConvert(db,"COSTUME","30","30","40","","","","XXS");
        createRecordSizeConvert(db,"COSTUME","32","32","42","","","","XS");
        createRecordSizeConvert(db,"COSTUME","34","34","44","","","S","S");
        createRecordSizeConvert(db,"COSTUME","36","36","46","","","M","M");
        createRecordSizeConvert(db,"COSTUME","38","38","48","","","M","M");
        createRecordSizeConvert(db,"COSTUME","40","40","50","","","L","L");
        createRecordSizeConvert(db,"COSTUME","42","42","52","","","L","L");
        createRecordSizeConvert(db,"COSTUME","44","44","54","","","LL","XL");
        createRecordSizeConvert(db,"COSTUME","46","46","56","","","LL","XL");
        createRecordSizeConvert(db,"COSTUME","48","48","58","","","","XXL");
        createRecordSizeConvert(db,"COSTUME","50","50","60","","","","XXL");
        createRecordSizeConvert(db,"COSTUME","52","52","62","","","","XXL");

        createRecordSizeConvert(db,"CHEMISIER","13","13","34","","","34","XXS");
        createRecordSizeConvert(db,"CHEMISIER","13.5","13.5","35","","","35","XS");
        createRecordSizeConvert(db,"CHEMISIER","14","14","36","","","36","S");
        createRecordSizeConvert(db,"CHEMISIER","14.5","14.5","37","","","37","M");
        createRecordSizeConvert(db,"CHEMISIER","15","15","38","","","38","L");
        createRecordSizeConvert(db,"CHEMISIER","15.5","15.5","39","","","39","X");
        createRecordSizeConvert(db,"CHEMISIER","15.75","15.75","40","","","40","XL");
        createRecordSizeConvert(db,"CHEMISIER","16","16","41","","","41","XXL");
        createRecordSizeConvert(db,"CHEMISIER","16.5","16.5","42","","","42","XXXL");
        createRecordSizeConvert(db,"CHEMISIER","17","17","43","","","43","XXXL");
        createRecordSizeConvert(db,"CHEMISIER","17.5","17.5","44","","","44","XXXL");

        createRecordSizeConvert(db,"CHEMISE","13","13","34","","","34","XXS");
        createRecordSizeConvert(db,"CHEMISE","13.5","13.5","35","","","35","XXS");
        createRecordSizeConvert(db,"CHEMISE","14","14","36","","","36","XS");
        createRecordSizeConvert(db,"CHEMISE","14.5","14.5","37","","","37","XS");
        createRecordSizeConvert(db,"CHEMISE","15","15","38","","","38","S");
        createRecordSizeConvert(db,"CHEMISE","15.5","15.5","39","","","39","S");
        createRecordSizeConvert(db,"CHEMISE","15.75","15.75","40","","","40","M");
        createRecordSizeConvert(db,"CHEMISE","16","16","41","","","41","M");
        createRecordSizeConvert(db,"CHEMISE","16.5","16.5","42","","","42","M");
        createRecordSizeConvert(db,"CHEMISE","17","17","43","","","43","L");
        createRecordSizeConvert(db,"CHEMISE","17.5","17.5","44","","","44","XL");
        createRecordSizeConvert(db,"CHEMISE","18","17","45","","","45","XL");
        createRecordSizeConvert(db,"CHEMISE","18.5","17.5","46","","","46","XXL");
        createRecordSizeConvert(db,"CHEMISE","19","18","47","","","47","XXL");
        createRecordSizeConvert(db,"CHEMISE","19.5","18.5","48","","","48","XXXL");

        createRecordSizeConvert(db,"TSHIRT","1","24","36","","","","XS");
        createRecordSizeConvert(db,"TSHIRT","3","26","38","","","","S");
        createRecordSizeConvert(db,"TSHIRT","4","27","39","","","","S");
        createRecordSizeConvert(db,"TSHIRT","5","28","40","","","","M");
        createRecordSizeConvert(db,"TSHIRT","6","30","42","","","","L");
        createRecordSizeConvert(db,"TSHIRT","7","31","43","","","","L");
        createRecordSizeConvert(db,"TSHIRT","9","33","45","","","","XL");
        createRecordSizeConvert(db,"TSHIRT","10","34","46","","","","XXL");

        createRecordSizeConvert(db,"SWEATER","1","26","36","","","","XS");
        createRecordSizeConvert(db,"SWEATER","3","28","38","","","","S");
        createRecordSizeConvert(db,"SWEATER","4","29","39","","","","S");
        createRecordSizeConvert(db,"SWEATER","5","30","40","","","","M");
        createRecordSizeConvert(db,"SWEATER","6","32","42","","","","L");
        createRecordSizeConvert(db,"SWEATER","7","33","43","","","","L");
        createRecordSizeConvert(db,"SWEATER","9","35","45","","","","XL");
        createRecordSizeConvert(db,"SWEATER","10","36","46","","","","XXL");

        createRecordSizeConvert(db,"SHIRT","1","14","36","","","","XS");
        createRecordSizeConvert(db,"SHIRT","3","15","38","","","","S");
        createRecordSizeConvert(db,"SHIRT","4","15","39","","","","S");
        createRecordSizeConvert(db,"SHIRT","5","16","40","","","","M");
        createRecordSizeConvert(db,"SHIRT","6","17","42","","","","L");
        createRecordSizeConvert(db,"SHIRT","7","17","43","","","","L");
        createRecordSizeConvert(db,"SHIRT","9","18","45","","","","XL");
        createRecordSizeConvert(db,"SHIRT","10","19","46","","","","XXL");

        createRecordSizeConvert(db,"PULL","1","16","36","","","","XS");
        createRecordSizeConvert(db,"PULL","3","18","38","","","","S");
        createRecordSizeConvert(db,"PULL","4","19","39","","","","S");
        createRecordSizeConvert(db,"PULL","5","20","40","","","","M");
        createRecordSizeConvert(db,"PULL","6","22","42","","","","L");
        createRecordSizeConvert(db,"PULL","7","23","43","","","","L");
        createRecordSizeConvert(db,"PULL","9","25","45","","","","XL");
        createRecordSizeConvert(db,"PULL","10","26","46","","","","XXL");

        // pantalons
        createRecordSizeConvert(db,"PANTALON","22","22","32","","","","XXXS");
        createRecordSizeConvert(db,"PANTALON","24","24","34","","","","XXS");
        createRecordSizeConvert(db,"PANTALON","26","26","36","","","","XS");
        createRecordSizeConvert(db,"PANTALON","28","28","38","","","","S");
        createRecordSizeConvert(db,"PANTALON","30","30","40","","","","M");
        createRecordSizeConvert(db,"PANTALON","32","32","42","","","","L");
        createRecordSizeConvert(db,"PANTALON","34","34","44","","","","XL");
        createRecordSizeConvert(db,"PANTALON","36","36","46","","","","XXL");
        createRecordSizeConvert(db,"PANTALON","38","38","48","","","","XXXL");
        createRecordSizeConvert(db,"PANTALON","40","40","50","","","","XXXXL");

        // JEANS
        createRecordSizeConvert(db,"JEANS","22","22","32","","","","XXXS");
        createRecordSizeConvert(db,"JEANS","24","24","34","","","","XXS");
        createRecordSizeConvert(db,"JEANS","26","26","36","","","","XS");
        createRecordSizeConvert(db,"JEANS","28","28","38","","","","S");
        createRecordSizeConvert(db,"JEANS","30","30","40","","","","M");
        createRecordSizeConvert(db,"JEANS","32","32","42","","","","L");
        createRecordSizeConvert(db,"JEANS","34","34","44","","","","XL");
        createRecordSizeConvert(db,"JEANS","36","36","46","","","","XXL");
        createRecordSizeConvert(db,"JEANS","38","38","48","","","","XXXL");
        createRecordSizeConvert(db,"JEANS","40","40","50","","","","XXXXL");

        createRecordSizeConvert(db,"CHAUSSURE-F","5","2.5","35","35","","21","");
        createRecordSizeConvert(db,"CHAUSSURE-F","5.5","3","35.5","35.5","","21.5","");
        createRecordSizeConvert(db,"CHAUSSURE-F","6","3.5","36","36","","22.5","");
        createRecordSizeConvert(db,"CHAUSSURE-F","6.5","4","37","37","","23","");
        createRecordSizeConvert(db,"CHAUSSURE-F","7","4.5","37.5","37.5","","23.5","");
        createRecordSizeConvert(db,"CHAUSSURE-F","7.5","5","38","38","","24","");
        createRecordSizeConvert(db,"CHAUSSURE-F","8","5.5","38.5","38.5","","24.5","");
        createRecordSizeConvert(db,"CHAUSSURE-F","8.5","6","39","39","","25","");
        createRecordSizeConvert(db,"CHAUSSURE-F","9","6.5","40","40","","25.5","");
        createRecordSizeConvert(db,"CHAUSSURE-F","9.5","7","40.5","40.5","","26","");
        createRecordSizeConvert(db,"CHAUSSURE-F","10","7.5","41","41","","26.5","");
        createRecordSizeConvert(db,"CHAUSSURE-F","10.5","8","42","42","","27","");
        createRecordSizeConvert(db,"CHAUSSURE-F","11","8.5","42.5","42.5","","27.5","");

        createRecordSizeConvert(db,"CHAUSSURE-H","5","4.5","37","37","","23","");
        createRecordSizeConvert(db,"CHAUSSURE-H","5.5","5","37.5","37.5","","23.5","");
        createRecordSizeConvert(db,"CHAUSSURE-H","6","5.5","38","38","","24","");
        createRecordSizeConvert(db,"CHAUSSURE-H","6.5","5.5","38.5","38.5","","24.5","");
        createRecordSizeConvert(db,"CHAUSSURE-H","7","6","39","39","","25","");
        createRecordSizeConvert(db,"CHAUSSURE-H","7.5","6.5","40","40","","25.5","");
        createRecordSizeConvert(db,"CHAUSSURE-H","8","7","7","40.5","40.5","26","");
        createRecordSizeConvert(db,"CHAUSSURE-H","8.5","7.5","41","41","","26.5","");
        createRecordSizeConvert(db,"CHAUSSURE-H","9","8","42","42","","27","");
        createRecordSizeConvert(db,"CHAUSSURE-H","9.5","8.5","42.5","42.5","","27.5","");
        createRecordSizeConvert(db,"CHAUSSURE-H","10","9","43","43","","28","");
        createRecordSizeConvert(db,"CHAUSSURE-H","10.5","9.5","44","44","","28.5","");
        createRecordSizeConvert(db,"CHAUSSURE-H","11","10","44.5","44.5","","29","");
        createRecordSizeConvert(db,"CHAUSSURE-H","11.5","10.5","45","45","","29.5","");
        createRecordSizeConvert(db,"CHAUSSURE-H","12","11","46","46","","30","");
        createRecordSizeConvert(db,"CHAUSSURE-H","12.5","11.5","46.5","46.5","","30.5","");
        createRecordSizeConvert(db,"CHAUSSURE-H","13","12","47","47","","31","");
        createRecordSizeConvert(db,"CHAUSSURE-H","13.5","12.5","48","48","","31.5","");
        createRecordSizeConvert(db,"CHAUSSURE-H","14","13","48.5","48.5","","32","");
        createRecordSizeConvert(db,"CHAUSSURE-H","14.5","13.5","49","49","","32.5","");
        createRecordSizeConvert(db,"CHAUSSURE-H","15.5","14.5","51","51","","33.5","");
        createRecordSizeConvert(db,"CHAUSSURE-H","15.5","15.5","52","52","","34.5","");

        createRecordSizeConvert(db,"SLIP","0","0",	"34",	"","","","XXS");
        createRecordSizeConvert(db,"SLIP","1","1",	"36",	"","","","XS");
        createRecordSizeConvert(db,"SLIP","1.5","1.5",	"38",	"","","","S");
        createRecordSizeConvert(db,"SLIP","2","2",	"40",	"","","","M");
        createRecordSizeConvert(db,"SLIP","2.5","2.5",	"42",	"","","","L");
        createRecordSizeConvert(db,"SLIP","3","3",	"44",	"","","","XL");
        createRecordSizeConvert(db,"SLIP","4","4",	"48",	"","","","XXL");
        createRecordSizeConvert(db,"SLIP","5","5",	"52",	"","","","XXXL");
        createRecordSizeConvert(db,"SLIP","6","6",	"56",	"","","","XXXXL");

        createRecordSizeConvert(db,"VESTE","36","36",	"46",	"","","","XXS");
        createRecordSizeConvert(db,"VESTE","38","38",	"48",	"","","","XS");
        createRecordSizeConvert(db,"VESTE","40","40",	"50",	"","","","S");
        createRecordSizeConvert(db,"VESTE","42","42",	"52",	"","","","M");
        createRecordSizeConvert(db,"VESTE","43","44",	"53",	"","","","L");
        createRecordSizeConvert(db,"VESTE","44","44",	"54",	"","","","XL");
        createRecordSizeConvert(db,"VESTE","45","45",	"55",	"","","","XXL");
        createRecordSizeConvert(db,"VESTE","46","46",	"56",	"","","","XXXL");

        createRecordSizeConvert(db,"MANTEAU","36","36",	"46",	"","","","XXS");
        createRecordSizeConvert(db,"MANTEAU","38","38",	"48",	"","","","XS");
        createRecordSizeConvert(db,"MANTEAU","40","40",	"50",	"","","","S");
        createRecordSizeConvert(db,"MANTEAU","42","42",	"52",	"","","","M");
        createRecordSizeConvert(db,"MANTEAU","43","43",	"53",	"","","","L");
        createRecordSizeConvert(db,"MANTEAU","44","44",	"54",	"","","","XL");
        createRecordSizeConvert(db,"MANTEAU","45","45",	"55",	"","","","XXL");
        createRecordSizeConvert(db,"MANTEAU","46","46",	"56",	"","","","XXXL");

        createRecordSizeConvert(db,"BLOUSON","36","36",	"46",	"","","","XXS");
        createRecordSizeConvert(db,"BLOUSON","38","38",	"48",	"","","","XS");
        createRecordSizeConvert(db,"BLOUSON","40","40",	"50",	"","","","S");
        createRecordSizeConvert(db,"BLOUSON","42","42",	"52",	"","","","M");
        createRecordSizeConvert(db,"BLOUSON","43","43",	"53",	"","","","L");
        createRecordSizeConvert(db,"BLOUSON","44","44",	"54",	"","","","XL");
        createRecordSizeConvert(db,"BLOUSON","45","45",	"55",	"","","","XXL");
        createRecordSizeConvert(db,"BLOUSON","46","46",	"56",	"","","","XXXL");


    }

    private void updateTableBrandSizeGuide(SQLiteDatabase db){
        //              Brand  Clothe Bust(cm) Waist(cm) Hips(cm) = UKSize
        // CREATION DES ENREGISTREMENTS PAR MARQUE/VETEMENTS/DIM1/DIM2/DIM3/TAILLE UK
        /*createRecordBrandSizeGuide(db,"DEFAULT","ROBE",0,0,0,"0");
        createRecordBrandSizeGuide(db,"DEFAULT","ROBE",74,56,81,"2");
        createRecordBrandSizeGuide(db,"DEFAULT","ROBE",76,58,84,"4");
        createRecordBrandSizeGuide(db,"DEFAULT","ROBE",79,61,86,"6");
        createRecordBrandSizeGuide(db,"DEFAULT","ROBE",81,63,89,"8");
        createRecordBrandSizeGuide(db,"DEFAULT","ROBE",86,68,94,"10");
        createRecordBrandSizeGuide(db,"DEFAULT","ROBE",91,73,99,"12");
        createRecordBrandSizeGuide(db,"DEFAULT","ROBE",96,78,104,"14");
        createRecordBrandSizeGuide(db,"DEFAULT","ROBE",101,83,109,"16");
        createRecordBrandSizeGuide(db,"DEFAULT","ROBE",109,91,116,"18");
        createRecordBrandSizeGuide(db,"DEFAULT","ROBE",116,98,124,"20");
        createRecordBrandSizeGuide(db,"DEFAULT","ROBE",122,104,130,"22");
        createRecordBrandSizeGuide(db,"DEFAULT","ROBE",128,110,136,"24");
        createRecordBrandSizeGuide(db,"DEFAULT","ROBE",134,116,142,"26");
        createRecordBrandSizeGuide(db, "DEFAULT", "ROBE", 140, 122, 148, "28");*/

        // ROBES
        createRecordBrandSizeGuide(db,"ASOS","ROBE",0,0,0,"0");
        createRecordBrandSizeGuide(db, "ASOS","ROBE",78.5,60.5,86,"6");
        createRecordBrandSizeGuide(db, "ASOS","ROBE",81,63,88.5,"8");
        createRecordBrandSizeGuide(db, "ASOS","ROBE",86,68,93.5,"10");
        createRecordBrandSizeGuide(db, "ASOS","ROBE",91,73,98.5,"12");
        createRecordBrandSizeGuide(db, "ASOS","ROBE",96,78,103.5,"14");
        createRecordBrandSizeGuide(db, "ASOS","ROBE",101,83,108.5,"16");
        createRecordBrandSizeGuide(db, "ASOS","ROBE",108.5,90.5,116,"18");
        createRecordBrandSizeGuide(db, "ASOS","ROBE",116,98,123.5,"20");
        createRecordBrandSizeGuide(db, "ASOS","ROBE",122,104,129.5,"22");
        createRecordBrandSizeGuide(db, "ASOS","ROBE",128,110,135.5,"24");
        createRecordBrandSizeGuide(db, "ASOS","ROBE",134,116,141.5,"26");


        createRecordBrandSizeGuide(db, "ADIDAS","ROBE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "ADIDAS","ROBE",76,	60,	84,	"4");
        createRecordBrandSizeGuide(db, "ADIDAS","ROBE",78,	62,	86,	"6");
        createRecordBrandSizeGuide(db, "ADIDAS","ROBE",82,	64,	88,	"8");
        createRecordBrandSizeGuide(db, "ADIDAS","ROBE",86,	68,	92,	"10");
        createRecordBrandSizeGuide(db, "ADIDAS","ROBE",90,	72,	96,	"12");
        createRecordBrandSizeGuide(db, "ADIDAS","ROBE",94,	76,	100,	"14");
        createRecordBrandSizeGuide(db, "ADIDAS","ROBE",98,	80,	104	, "16");
        createRecordBrandSizeGuide(db, "ADIDAS","ROBE",102,	84,	108,	"18");
        createRecordBrandSizeGuide(db, "ADIDAS","ROBE",106,	88,	112,	"20");
        createRecordBrandSizeGuide(db, "ADIDAS","ROBE",110,	92,	116,	"22");

        createRecordBrandSizeGuide(db, "NIKE","ROBE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "NIKE","ROBE",76,	60,	84,	"4");
        createRecordBrandSizeGuide(db, "NIKE","ROBE",78,	62,	86,	"6");
        createRecordBrandSizeGuide(db, "NIKE","ROBE",82,	64,	88,	"8");
        createRecordBrandSizeGuide(db, "NIKE","ROBE",86,	68,	92,	"10");
        createRecordBrandSizeGuide(db, "NIKE","ROBE",90,	72,	96,	"12");
        createRecordBrandSizeGuide(db, "NIKE","ROBE",94,	76,	100,	"14");
        createRecordBrandSizeGuide(db, "NIKE","ROBE",98,	80,	104,	"16");
        createRecordBrandSizeGuide(db, "NIKE","ROBE",102,	84,	108,	"18");
        createRecordBrandSizeGuide(db, "NIKE","ROBE",106,	88,	112,	"20");
        createRecordBrandSizeGuide(db, "NIKE","ROBE",110,	92,	116,	"22");
        createRecordBrandSizeGuide(db, "NIKE","ROBE",117,	97,	121,	"24");
        createRecordBrandSizeGuide(db, "NIKE","ROBE",123,	103,	127,	"26");

        createRecordBrandSizeGuide(db, "KIABI","ROBE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "KIABI","ROBE",76,	60,	84,	"4");
        createRecordBrandSizeGuide(db, "KIABI","ROBE",78,	62,	86,	"6");
        createRecordBrandSizeGuide(db, "KIABI","ROBE",82,	64,	88,	"8");
        createRecordBrandSizeGuide(db, "KIABI","ROBE",86,	68,	92,	"10");
        createRecordBrandSizeGuide(db, "KIABI","ROBE",90,	72,	96,	"12");
        createRecordBrandSizeGuide(db, "KIABI","ROBE",94,	76,	100,	"14");
        createRecordBrandSizeGuide(db, "KIABI","ROBE",98,	80,	104,	"16");
        createRecordBrandSizeGuide(db, "KIABI","ROBE",102,	84,	108,	"18");
        createRecordBrandSizeGuide(db, "KIABI","ROBE",106,	88,	112,	"20");
        createRecordBrandSizeGuide(db, "KIABI","ROBE",110,	92,	116,	"22");
        createRecordBrandSizeGuide(db, "KIABI","ROBE",117,	97,	121,	"24");
        createRecordBrandSizeGuide(db, "KIABI","ROBE",123,	103,	127,	"26");
        createRecordBrandSizeGuide(db, "KIABI","ROBE",129,	109,	133	,"28");
        createRecordBrandSizeGuide(db, "KIABI","ROBE",135,	115,	139,	"30");
        createRecordBrandSizeGuide(db, "KIABI","ROBE",141,	121,	145,	"32");

        createRecordBrandSizeGuide(db, "H&M","ROBE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "H&M","ROBE",76,	60,	84,	"4");
        createRecordBrandSizeGuide(db, "H&M","ROBE",80,	64,	88,	"6");
        createRecordBrandSizeGuide(db, "H&M","ROBE",84,	68,	92,	"8");
        createRecordBrandSizeGuide(db, "H&M","ROBE",88,	72,	96,	"10");
        createRecordBrandSizeGuide(db, "H&M","ROBE",92,	76,	100,	"12");
        createRecordBrandSizeGuide(db, "H&M","ROBE",96,	80,	104,	"14");
        createRecordBrandSizeGuide(db, "H&M","ROBE",100,	84,	108,	"16");
        createRecordBrandSizeGuide(db, "H&M","ROBE",104	,88,	112,	"18");
        createRecordBrandSizeGuide(db, "H&M","ROBE",110	,94,	117,	"20");
        createRecordBrandSizeGuide(db, "H&M","ROBE",116,	100,	122,	"22");
        createRecordBrandSizeGuide(db, "H&M","ROBE",122,	106,	127,	"24");
        createRecordBrandSizeGuide(db, "H&M","ROBE",128,	112,	132,	"26");
        createRecordBrandSizeGuide(db, "H&M","ROBE",134,	118,	137,	"28");
        createRecordBrandSizeGuide(db, "H&M","ROBE",140,	124,	142,	"30");

        createRecordBrandSizeGuide(db, "ZARA","ROBE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "ZARA","ROBE",71,	48,	75,	"0");
        createRecordBrandSizeGuide(db, "ZARA","ROBE",73,	50,	78,	"2");
        createRecordBrandSizeGuide(db, "ZARA","ROBE",76,	53,	80,	"4");
        createRecordBrandSizeGuide(db, "ZARA","ROBE",79,	56,	83,	"6");
        createRecordBrandSizeGuide(db, "ZARA","ROBE",83,	61,	87,	"8");
        createRecordBrandSizeGuide(db, "ZARA","ROBE",87,	66,	92,	"10");
        createRecordBrandSizeGuide(db, "ZARA","ROBE",95,	71,	97,	"12");
        createRecordBrandSizeGuide(db, "ZARA","ROBE",100,	76,	102,	"14");
        createRecordBrandSizeGuide(db, "ZARA","ROBE",105,	85,	107,	"16");
        createRecordBrandSizeGuide(db, "ZARA","ROBE",110,	91,	112,	"18");
        createRecordBrandSizeGuide(db, "ZARA","ROBE",115,	98,	118,	"20");
        createRecordBrandSizeGuide(db, "ZARA","ROBE",122,	105,	125,	"22");
        createRecordBrandSizeGuide(db, "ZARA","ROBE",128,	110	,131,	"24");
        createRecordBrandSizeGuide(db, "ZARA","ROBE",134,	125,	137,	"26");

        createRecordBrandSizeGuide(db, "DESIGUAL","ROBE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "DESIGUAL","ROBE",82,	62,	88,	"6");
        createRecordBrandSizeGuide(db, "DESIGUAL","ROBE",86,	66,	92,	"8");
        createRecordBrandSizeGuide(db, "DESIGUAL","ROBE",90,	70,	96,	"10");
        createRecordBrandSizeGuide(db, "DESIGUAL","ROBE",96,	76,	102,	"12");
        createRecordBrandSizeGuide(db, "DESIGUAL","ROBE",102,	82,	108,	"14");
        createRecordBrandSizeGuide(db, "DESIGUAL","ROBE",108,	88,	114,	"16");

        createRecordBrandSizeGuide(db, "MANGO","ROBE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "MANGO","ROBE",80,	60,	88,	"4");
        createRecordBrandSizeGuide(db, "MANGO","ROBE",82,	62,	90,	"6");
        createRecordBrandSizeGuide(db, "MANGO","ROBE",86,	66,	94,	"8");
        createRecordBrandSizeGuide(db, "MANGO","ROBE",92,	72,	100,	"10");
        createRecordBrandSizeGuide(db, "MANGO","ROBE",98,	78,	106,	"12");
        createRecordBrandSizeGuide(db, "MANGO","ROBE",104,	85,	112,	"14");
        createRecordBrandSizeGuide(db, "MANGO","ROBE",108,	90,	116,	"16");

        createRecordBrandSizeGuide(db, "KOOKAI","ROBE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "KOOKAI","ROBE",76,	58,	84,	"4");
        createRecordBrandSizeGuide(db, "KOOKAI","ROBE",80,	62,	88,	"6");
        createRecordBrandSizeGuide(db, "KOOKAI","ROBE",84,	66,	92,	"8");
        createRecordBrandSizeGuide(db, "KOOKAI","ROBE",88,	70,	96,	"10");
        createRecordBrandSizeGuide(db, "KOOKAI","ROBE",92,	74,	100,	"12");
        createRecordBrandSizeGuide(db, "KOOKAI","ROBE",96,	78,	104,	"14");
        createRecordBrandSizeGuide(db, "KOOKAI","ROBE",100,	82,	108,	"16");

        createRecordBrandSizeGuide(db, "KOOPLES","ROBE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "KOOPLES","ROBE",80,	62,	86,	"6");
        createRecordBrandSizeGuide(db, "KOOPLES","ROBE",84,	66,	90,	"8");
        createRecordBrandSizeGuide(db, "KOOPLES","ROBE",88,	70,	94,	"10");
        createRecordBrandSizeGuide(db, "KOOPLES","ROBE",92,	74,	98,	"12");
        createRecordBrandSizeGuide(db, "KOOPLES","ROBE",96,	78,	102,	"14");

        createRecordBrandSizeGuide(db, "SANDRO","ROBE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "SANDRO","ROBE",79,	59,	85,	"4");
        createRecordBrandSizeGuide(db, "SANDRO","ROBE",83,	63,	89,	"6");
        createRecordBrandSizeGuide(db, "SANDRO","ROBE",87,	67,	93,	"8");
        createRecordBrandSizeGuide(db, "SANDRO","ROBE",91,	71,	97,	"10");
        createRecordBrandSizeGuide(db, "SANDRO","ROBE",95,	75,	101,	"12");
        createRecordBrandSizeGuide(db, "SANDRO","ROBE",99,	79,	105,	"14");
        createRecordBrandSizeGuide(db, "SANDRO","ROBE",104,	84,	110,	"16");
        createRecordBrandSizeGuide(db, "SANDRO","ROBE",110,	90,	116,	"18");

        // JUPES

        createRecordBrandSizeGuide(db, "ASOS","JUPE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "ASOS","JUPE",0,60.5,	86,	"6");
        createRecordBrandSizeGuide(db, "ASOS","JUPE",0,	63,	88.5,	"8");
        createRecordBrandSizeGuide(db, "ASOS","JUPE",0,		68,	93.5,	"10");
        createRecordBrandSizeGuide(db, "ASOS","JUPE",0,		73,	98.5,	"12");
        createRecordBrandSizeGuide(db, "ASOS","JUPE",0,		78,	103.5,	"14");
        createRecordBrandSizeGuide(db, "ASOS","JUPE",0,		83,	108.5,	"16");
        createRecordBrandSizeGuide(db, "ASOS","JUPE",0,		90.5,	116,	"18");
        createRecordBrandSizeGuide(db, "ASOS","JUPE",0,		98,	123.5,	"20");
        createRecordBrandSizeGuide(db, "ASOS","JUPE",0,		104,	129.5,	"22");
        createRecordBrandSizeGuide(db, "ASOS","JUPE",0,		110,	135.5,	"24");
        createRecordBrandSizeGuide(db, "ASOS","JUPE",0,		116,	141.5,	"26");

        createRecordBrandSizeGuide(db, "KIABI","JUPE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"KIABI","JUPE",0,	60,	84,	"4");
        createRecordBrandSizeGuide(db,"KIABI","JUPE",0,	62,	86,	"6");
        createRecordBrandSizeGuide(db,"KIABI","JUPE",0,	64,	88,	"8");
        createRecordBrandSizeGuide(db,"KIABI","JUPE",0,	68,	92,	"10");
        createRecordBrandSizeGuide(db,"KIABI","JUPE",0,	72,	96,	"12");
        createRecordBrandSizeGuide(db,"KIABI","JUPE",0,	76,	100,	"14");
        createRecordBrandSizeGuide(db,"KIABI","JUPE",0,	80,	104,	"16");
        createRecordBrandSizeGuide(db,"KIABI","JUPE",0,	84,	108,	"18");
        createRecordBrandSizeGuide(db,"KIABI","JUPE",0,	88,	112,	"20");
        createRecordBrandSizeGuide(db,"KIABI","JUPE",0,	92,	116,	"22");
        createRecordBrandSizeGuide(db,"KIABI","JUPE",0,	97,	121,	"24");
        createRecordBrandSizeGuide(db,"KIABI","JUPE",0,	103,	127,	"26");
        createRecordBrandSizeGuide(db,"KIABI","JUPE",0,	109,	133,	"28");
        createRecordBrandSizeGuide(db,"KIABI","JUPE",0,	115,	139,	"30");
        createRecordBrandSizeGuide(db,"KIABI","JUPE",0,	121,	145,	"32");

        createRecordBrandSizeGuide(db, "ADIDAS","JUPE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"ADIDAS","JUPE",0,	60,	84,	"4");
        createRecordBrandSizeGuide(db,"ADIDAS","JUPE",0,	62,	86,	"6");
        createRecordBrandSizeGuide(db,"ADIDAS","JUPE",0,	64,	88,	"8");
        createRecordBrandSizeGuide(db,"ADIDAS","JUPE",0,	68,	92,	"10");
        createRecordBrandSizeGuide(db,"ADIDAS","JUPE",0,	72,	96,	"12");
        createRecordBrandSizeGuide(db,"ADIDAS","JUPE",0,	76,	100,	"14");
        createRecordBrandSizeGuide(db,"ADIDAS","JUPE",0,	80,	104,"16");
        createRecordBrandSizeGuide(db,"ADIDAS","JUPE",0,	84,	108,	"18");
        createRecordBrandSizeGuide(db,"ADIDAS","JUPE",0,	88,	112,	"20");
        createRecordBrandSizeGuide(db,"ADIDAS","JUPE",0,	92,	116,	"22");

        createRecordBrandSizeGuide(db, "NIKE","JUPE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"NIKE","JUPE",0,	60,	84,	"4");
        createRecordBrandSizeGuide(db,"NIKE","JUPE",0,  62,	86,	"6");
        createRecordBrandSizeGuide(db,"NIKE","JUPE",0,	64,	88,	"8");
        createRecordBrandSizeGuide(db,"NIKE","JUPE",0,	68,	92,	"10");
        createRecordBrandSizeGuide(db,"NIKE","JUPE",0,	72,	96,	"12");
        createRecordBrandSizeGuide(db,"NIKE","JUPE",0,	76,	100,	"14");
        createRecordBrandSizeGuide(db,"NIKE","JUPE",0,	80,	104,	"16");
        createRecordBrandSizeGuide(db,"NIKE","JUPE",0,	84,	108,	"18");
        createRecordBrandSizeGuide(db,"NIKE","JUPE",0,	88,	112,	"20");
        createRecordBrandSizeGuide(db,"NIKE","JUPE",0,	92,	116,	"22");
        createRecordBrandSizeGuide(db,"NIKE","JUPE",0,	97,	121,	"24");
        createRecordBrandSizeGuide(db,"NIKE","JUPE",0,103,	127,	"26");

        createRecordBrandSizeGuide(db, "H&M","JUPE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"H&M","JUPE",0,	56,	81,	"2");
        createRecordBrandSizeGuide(db,"H&M","JUPE",0,	58,	84,	"4");
        createRecordBrandSizeGuide(db,"H&M","JUPE",0,	61,	86,	"6");
        createRecordBrandSizeGuide(db,"H&M","JUPE",0,	63,	89,	"8");
        createRecordBrandSizeGuide(db,"H&M","JUPE",0,	68,	94,	"10");
        createRecordBrandSizeGuide(db,"H&M","JUPE",0,	73,	99,	"12");
        createRecordBrandSizeGuide(db,"H&M","JUPE",0,	78,	104,	"14");
        createRecordBrandSizeGuide(db,"H&M","JUPE",0,	83,	109,	"16");
        createRecordBrandSizeGuide(db,"H&M","JUPE",0,	91,	116,	"18");
        createRecordBrandSizeGuide(db,"H&M","JUPE",0,	98,	124,	"20");
        createRecordBrandSizeGuide(db,"H&M","JUPE",0,	104,	130,	"22");
        createRecordBrandSizeGuide(db,"H&M","JUPE",0,	110,	136,	"24");
        createRecordBrandSizeGuide(db,"H&M","JUPE",0,	116,	142,	"26");
        createRecordBrandSizeGuide(db,"H&M","JUPE",0,	122,	148,	"28");

        createRecordBrandSizeGuide(db, "ZARA","JUPE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"ZARA","JUPE",0,	48,	75,	"2");
        createRecordBrandSizeGuide(db,"ZARA","JUPE",0,	50,	78,	"4");
        createRecordBrandSizeGuide(db,"ZARA","JUPE",0,	53,	80,	"6");
        createRecordBrandSizeGuide(db,"ZARA","JUPE",0,	56,	83,	"8");
        createRecordBrandSizeGuide(db,"ZARA","JUPE",0,	61,	87,	"10");
        createRecordBrandSizeGuide(db,"ZARA","JUPE",0,	66,	92,	"12");
        createRecordBrandSizeGuide(db,"ZARA","JUPE",0,	71,	97,	"14");
        createRecordBrandSizeGuide(db,"ZARA","JUPE",0,	76,	102,	"16");
        createRecordBrandSizeGuide(db,"ZARA","JUPE",0,	85,	107,	"18");
        createRecordBrandSizeGuide(db,"ZARA","JUPE",0,	91,	112,	"20");
        createRecordBrandSizeGuide(db,"ZARA","JUPE",0,	98,	118,	"22");
        createRecordBrandSizeGuide(db,"ZARA","JUPE",0,	105,	125,	"24");
        createRecordBrandSizeGuide(db,"ZARA","JUPE",0,	110,	131,	"26");
        createRecordBrandSizeGuide(db,"ZARA","JUPE",0,	125,	137,	"28");

        createRecordBrandSizeGuide(db, "DESIGUAL","JUPE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"DESIGUAL","JUPE",0,	62,	88,	"6");
        createRecordBrandSizeGuide(db,"DESIGUAL","JUPE",0,	66,	92,	"8");
        createRecordBrandSizeGuide(db,"DESIGUAL","JUPE",0,	70,	96,	"10");
        createRecordBrandSizeGuide(db,"DESIGUAL","JUPE",0,	76,	102,	"12");
        createRecordBrandSizeGuide(db,"DESIGUAL","JUPE",0,	82,	108,	"14");
        createRecordBrandSizeGuide(db,"DESIGUAL","JUPE",0,	88,	114,	"16");

        createRecordBrandSizeGuide(db, "MANGO","JUPE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"MANGO","JUPE",0,	60,	88,	"4");
        createRecordBrandSizeGuide(db,"MANGO","JUPE",0,62,	90,	"6");
        createRecordBrandSizeGuide(db,"MANGO","JUPE",0,	66,	94,	"8");
        createRecordBrandSizeGuide(db,"MANGO","JUPE",0,	72,	100,	"10");
        createRecordBrandSizeGuide(db,"MANGO","JUPE",0,	78,	106,	"12");
        createRecordBrandSizeGuide(db,"MANGO","JUPE",0,	85,	112,	"14");
        createRecordBrandSizeGuide(db,"MANGO","JUPE",0,	90,	116,	"16");


        createRecordBrandSizeGuide(db, "KOOKAI","JUPE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"KOOKAI","JUPE",0,	58,	84,	"4");
        createRecordBrandSizeGuide(db,"KOOKAI","JUPE",0,	62,	88,	"6");
        createRecordBrandSizeGuide(db,"KOOKAI","JUPE",0,	66,	92,	"8");
        createRecordBrandSizeGuide(db,"KOOKAI","JUPE",0,	70,	96,	"10");
        createRecordBrandSizeGuide(db,"KOOKAI","JUPE",0,	74,	100,	"12");
        createRecordBrandSizeGuide(db,"KOOKAI","JUPE",0,	78,	104,	"14");
        createRecordBrandSizeGuide(db,"KOOKAI","JUPE",0,	82,	108,	"16");

        createRecordBrandSizeGuide(db, "KOOPLES","JUPE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"KOOPLES","JUPE",0,	62,	86,	"6");
        createRecordBrandSizeGuide(db,"KOOPLES","JUPE",0,	66,	90,	"8");
        createRecordBrandSizeGuide(db,"KOOPLES","JUPE",0,	70,	94,	"10");
        createRecordBrandSizeGuide(db,"KOOPLES","JUPE",0,	74,	98,	"12");
        createRecordBrandSizeGuide(db,"KOOPLES","JUPE",0,	78,	102,	"14");

        createRecordBrandSizeGuide(db, "PROMOD","JUPE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"PROMOD","JUPE",0,	59,	85,	"4");
        createRecordBrandSizeGuide(db,"PROMOD","JUPE",0,	63,	89,	"6");
        createRecordBrandSizeGuide(db,"PROMOD","JUPE",0,	67,	93,	"8");
        createRecordBrandSizeGuide(db,"PROMOD","JUPE",0,	71,	97,	"10");
        createRecordBrandSizeGuide(db,"PROMOD","JUPE",0,	75,	101,	"12");
        createRecordBrandSizeGuide(db,"PROMOD","JUPE",0,	79,	105,	"14");
        createRecordBrandSizeGuide(db,"PROMOD","JUPE",0,	84,	110,	"16");
        createRecordBrandSizeGuide(db,"PROMOD","JUPE",0,	90,	116,	"18");

        createRecordBrandSizeGuide(db, "SANDRO","JUPE",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"SANDRO","JUPE",0,	62,	90,	"6");
        createRecordBrandSizeGuide(db,"SANDRO","JUPE",0,	66,	94,	"8");
        createRecordBrandSizeGuide(db,"SANDRO","JUPE",0,	70,	98,	"10");
        createRecordBrandSizeGuide(db,"SANDRO","JUPE",0,	76,	104,	"12");
        createRecordBrandSizeGuide(db,"SANDRO","JUPE",0,	82,	110,	"14");

        // T-SHIRT (Dimension = Tour de poitrine)
        createRecordBrandSizeGuide(db,"ASOS", "TSHIRT-H",0,0,	0,"0");
        createRecordBrandSizeGuide(db,"ASOS", "TSHIRT-H",0,0,	81,"24");
        createRecordBrandSizeGuide(db,"ASOS", "TSHIRT-H",0,0,	86,"26");
        createRecordBrandSizeGuide(db,"ASOS", "TSHIRT-H",0,0,	91,"28");
        createRecordBrandSizeGuide(db,"ASOS", "TSHIRT-H",0,0,	96,"30");
        createRecordBrandSizeGuide(db,"ASOS", "TSHIRT-H",0,0,	101,"32");
        createRecordBrandSizeGuide(db,"ASOS", "TSHIRT-H",0,0,	106,"34");


        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	0, 0,0,		"0");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	78,0,0,		"28");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	82,	0,0,	"30");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	86,	0,0,	"32");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	90,	0,0,	"34");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	94,	0,0,	"36");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	98,	0,0,	"38");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	102,0,0,		"40");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	106,0,0,		"42");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	110,0,0,		"44");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	114,0,0,		"46");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	118,0,0,		"48");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	122,0,0,		"50");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	126,0,0,		"52");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	130,0,0,		"54");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	134,0,0,		"56");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	138,0,0,		"58");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	142,0,0,		"60");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	146,0,0,		"62");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	150,0,0,		"64");
        createRecordBrandSizeGuide(db, "KIABI",	"TSHIRT-H",	154,0,0,		"68");

        createRecordBrandSizeGuide(db, "H&M","TSHIRT-F",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "H&M","TSHIRT-F",76,	60,	84,	"26");
        createRecordBrandSizeGuide(db, "H&M","TSHIRT-F",78,	62,	86,	"28");
        createRecordBrandSizeGuide(db, "H&M","TSHIRT-F",82,	64,	88,	"30");
        createRecordBrandSizeGuide(db, "H&M","TSHIRT-F",86,	68,	92,	"32");
        createRecordBrandSizeGuide(db, "H&M","TSHIRT-F",90,	72,	96,	"34");
        createRecordBrandSizeGuide(db, "H&M","TSHIRT-F",94,	76,	100,	"36");
        createRecordBrandSizeGuide(db, "H&M","TSHIRT-F",98,	80,	104,	"38");
        createRecordBrandSizeGuide(db, "H&M","TSHIRT-F",102,	84,	108,	"40");
        createRecordBrandSizeGuide(db, "H&M","TSHIRT-F",106,	88,	112,	"42");
        createRecordBrandSizeGuide(db, "H&M","TSHIRT-F",110,	92,	116,	"44");
        createRecordBrandSizeGuide(db, "H&M","TSHIRT-F",117,	97,	121,	"46");
        createRecordBrandSizeGuide(db, "H&M","TSHIRT-F",123,	103,	127,	"48");

        createRecordBrandSizeGuide(db, "H&M", "TSHIRT-H", 	0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "H&M", "TSHIRT-H", 	82,	72,	0,	"30");
        createRecordBrandSizeGuide(db, "H&M",	"TSHIRT-H", 	88,	76,	0,	"32");
        createRecordBrandSizeGuide(db, "H&M",	"TSHIRT-H", 	92,	80,	0,	"34");
        createRecordBrandSizeGuide(db, "H&M",	"TSHIRT-H", 	96,	84,	0,	"36");
        createRecordBrandSizeGuide(db, "H&M",	"TSHIRT-H", 	100,	88, 0,		"38");
        createRecordBrandSizeGuide(db, "H&M",	"TSHIRT-H", 	104,	92,	0,	"40");
        createRecordBrandSizeGuide(db, "H&M",	"TSHIRT-H", 	108,	96,	0,	"42");
        createRecordBrandSizeGuide(db, "H&M",	"TSHIRT-H", 	112,	100,	0,	"44");
        createRecordBrandSizeGuide(db, "H&M",	"TSHIRT-H", 	116,	104, 0,	"46");
        createRecordBrandSizeGuide(db, "H&M",	"TSHIRT-H", 	120,	108,	0,	"48");
        createRecordBrandSizeGuide(db, "H&M",	"TSHIRT-H", 	124,    112,	0,	"50");

        createRecordBrandSizeGuide(db, "KOOKAI",	"TSHIRT-F",	0,	0,	0,		"0");
        createRecordBrandSizeGuide(db, "KOOKAI",	"TSHIRT-F",	80,	62,	88,		"22");
        createRecordBrandSizeGuide(db, "KOOKAI",	"TSHIRT-F",	84,	66,	92,		"24");
        createRecordBrandSizeGuide(db, "KOOKAI",	"TSHIRT-F",	88,	70,	96,		"26");
        createRecordBrandSizeGuide(db, "KOOKAI",	"TSHIRT-F",	92,	74,	100,	"28");
        createRecordBrandSizeGuide(db, "KOOKAI",	"TSHIRT-F",	96,	78,	104,	"30");
        createRecordBrandSizeGuide(db, "KOOKAI",	"TSHIRT-F",	100,82,	108,	"32");

        createRecordBrandSizeGuide(db, "ZARA",	"TSHIRT-F",	0,	0,	0,      "0");
        createRecordBrandSizeGuide(db, "ZARA",	"TSHIRT-F",	80,	58,	86,     "20");
        createRecordBrandSizeGuide(db, "ZARA",	"TSHIRT-F",	82,	64,	90,		"22");
        createRecordBrandSizeGuide(db, "ZARA",	"TSHIRT-F",	86,	66,	94,		"24");
        createRecordBrandSizeGuide(db, "ZARA",	"TSHIRT-F",	90,	70,	98,		"26");
        createRecordBrandSizeGuide(db, "ZARA",	"TSHIRT-F",	94,	74,	102,	"28");
        createRecordBrandSizeGuide(db, "ZARA",	"TSHIRT-F",	98,	78,	106,	"30");
        createRecordBrandSizeGuide(db, "ZARA",	"TSHIRT-F",	102,82,	110,	"32");
        createRecordBrandSizeGuide(db, "ZARA",	"TSHIRT-F",	106,86,	114,	"34");

        createRecordBrandSizeGuide(db, "ADIDAS",	"TSHIRT-H",	0,	0,	0,		"0");
        createRecordBrandSizeGuide(db, "ADIDAS",	"TSHIRT-H",	82,	71,	82,		"28");
        createRecordBrandSizeGuide(db, "ADIDAS",	"TSHIRT-H",	87,	75,	86,		"30");
        createRecordBrandSizeGuide(db, "ADIDAS",	"TSHIRT-H",	94,	82,	93,		"32");
        createRecordBrandSizeGuide(db, "ADIDAS",	"TSHIRT-H",	102,90,	101,	"34");
        createRecordBrandSizeGuide(db, "ADIDAS",	"TSHIRT-H",	111,99,	110,	"36");
        createRecordBrandSizeGuide(db, "ADIDAS",	"TSHIRT-H",	121,109,119,	"38");
        createRecordBrandSizeGuide(db, "ADIDAS",	"TSHIRT-H",	132,121,128,	"40");
        createRecordBrandSizeGuide(db, "ADIDAS",	"TSHIRT-H",	144,134,138,	"42");

        createRecordBrandSizeGuide(db, "ADIDAS",	"TSHIRT-F",	0,	0,	0,		"0");
        createRecordBrandSizeGuide(db, "ADIDAS",	"TSHIRT-F",	73,	57,	82,		"28");
        createRecordBrandSizeGuide(db, "ADIDAS",	"TSHIRT-F",	77,	61,	86,		"30");
        createRecordBrandSizeGuide(db, "ADIDAS",	"TSHIRT-F",	83,	67,	92,		"32");
        createRecordBrandSizeGuide(db, "ADIDAS",	"TSHIRT-F",	89,	73,	98,		"34");
        createRecordBrandSizeGuide(db, "ADIDAS",	"TSHIRT-F",	95,	79,	104,	"36");
        createRecordBrandSizeGuide(db, "ADIDAS",	"TSHIRT-F",	102,86,	111,	"38");
        createRecordBrandSizeGuide(db, "ADIDAS",	"TSHIRT-F",	110,94,	118,	"40");

        createRecordBrandSizeGuide(db, "MANGO",	"TSHIRT-F",	0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "MANGO",	"TSHIRT-F",	80,	60,	0,	"20");
        createRecordBrandSizeGuide(db, "MANGO",	"TSHIRT-F",	82,	62,	0,	"22");
        createRecordBrandSizeGuide(db, "MANGO",	"TSHIRT-F",	86,	66,	0,	"24");
        createRecordBrandSizeGuide(db, "MANGO",	"TSHIRT-F",	90,	70,	0,	"26");
        createRecordBrandSizeGuide(db, "MANGO",	"TSHIRT-F",	94,	74,	0,	"28");
        createRecordBrandSizeGuide(db, "MANGO",	"TSHIRT-F",	98,	78,	0,	"30");
        createRecordBrandSizeGuide(db, "MANGO",	"TSHIRT-F",	102,83,	0,	"32");
        createRecordBrandSizeGuide(db, "MANGO",	"TSHIRT-F",	106,88,	0,	"34");
        createRecordBrandSizeGuide(db, "MANGO",	"TSHIRT-F",	111,93,	0,	"36");

        createRecordBrandSizeGuide(db,"DESIGUAL",	"TSHIRT-H",	0,0,0,	"0");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"TSHIRT-H",	86,0,0,	"26");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"TSHIRT-H",	90,0,0,	"28");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"TSHIRT-H",	94,0,0,	"30");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"TSHIRT-H",	98,0,0,	"32");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"TSHIRT-H",	102,0,0,	"34");

        // SWEAT SHIRT (Dimension = Tour de poitrine)
        createRecordBrandSizeGuide(db,"ASOS","SWEATER-F",0,0,0,"0");
        createRecordBrandSizeGuide(db,"ASOS","SWEATER-F",0,0,81,"26");
        createRecordBrandSizeGuide(db,"ASOS","SWEATER-F",0,0,86,"28");
        createRecordBrandSizeGuide(db,"ASOS","SWEATER-F",0,0,91,"30");
        createRecordBrandSizeGuide(db,"ASOS","SWEATER-F",0,0,96,"32");
        createRecordBrandSizeGuide(db,"ASOS","SWEATER-F",0,0,101,"33");
        createRecordBrandSizeGuide(db,"ASOS","SWEATER-F",0,0,106,"34");
        createRecordBrandSizeGuide(db,"ASOS","SWEATER-F",0,0,111,"36");

        createRecordBrandSizeGuide(db,"DESIGUAL",	"SWEATER-F",	0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"SWEATER-F",	0,	0,	86,	"28");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"SWEATER-F",	0,	0,	90,	"30");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"SWEATER-F",	0,	0,	94,	"32");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"SWEATER-F",	0,	0,	98,	"34");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"SWEATER-F",	0,	0,	102,	"36");

        createRecordBrandSizeGuide(db, "KIABI","SWEATER-F",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "KIABI","SWEATER-F",76,	60,	84,	"24");
        createRecordBrandSizeGuide(db, "KIABI","SWEATER-F",78,	62,	86,	"26");
        createRecordBrandSizeGuide(db, "KIABI","SWEATER-F",82,	64,	88,	"28");
        createRecordBrandSizeGuide(db, "KIABI","SWEATER-F",86,	68,	92,	"30");
        createRecordBrandSizeGuide(db, "KIABI","SWEATER-F",90,	72,	96,	"32");
        createRecordBrandSizeGuide(db, "KIABI","SWEATER-F",94,	76,	100,	"34");
        createRecordBrandSizeGuide(db, "KIABI","SWEATER-F",98,	80,	104,	"36");
        createRecordBrandSizeGuide(db, "KIABI","SWEATER-F",102,	84,	108,	"38");
        createRecordBrandSizeGuide(db, "KIABI","SWEATER-F",106,	88,	112,	"40");
        createRecordBrandSizeGuide(db, "KIABI","SWEATER-F",110,	92,	116,	"42");
        createRecordBrandSizeGuide(db, "KIABI","SWEATER-F",117,	97,	121,	"44");
        createRecordBrandSizeGuide(db, "KIABI","SWEATER-F",123,	103,	127,	"46");
        createRecordBrandSizeGuide(db, "KIABI","SWEATER-F",129,	109,	133	,"48");
        createRecordBrandSizeGuide(db, "KIABI","SWEATER-F",135,	115,	139,	"50");
        createRecordBrandSizeGuide(db, "KIABI","SWEATER-F",141,	121,	145,	"52");

        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	0, 0,0,		"0");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	78,0,0,		"28");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	82,	0,0,	"30");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	86,	0,0,	"32");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	90,	0,0,	"34");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	94,	0,0,	"36");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	98,	0,0,	"38");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	102,0,0,		"40");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	106,0,0,		"42");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	110,0,0,		"44");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	114,0,0,		"46");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	118,0,0,		"48");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	122,0,0,		"50");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	126,0,0,		"52");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	130,0,0,		"54");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	134,0,0,		"56");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	138,0,0,		"58");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	142,0,0,		"60");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	146,0,0,		"62");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	150,0,0,		"64");
        createRecordBrandSizeGuide(db, "KIABI",	"SWEATER-H",	154,0,0,		"68");

        createRecordBrandSizeGuide(db, "KOOKAI",	"SWEATER-F",	0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "KOOKAI",	"SWEATER-F",	80,	62,	88,	"22");
        createRecordBrandSizeGuide(db, "KOOKAI",	"SWEATER-F",    84,	66,	92,	"24");
        createRecordBrandSizeGuide(db, "KOOKAI",	"SWEATER-F",	88,	70,	96,	"26");
        createRecordBrandSizeGuide(db, "KOOKAI",	"SWEATER-F",	92,	74,	100,"28");
        createRecordBrandSizeGuide(db, "KOOKAI",	"SWEATER-F",	96,	78,	104,"30");
        createRecordBrandSizeGuide(db, "KOOKAI",	"SWEATER-F",	100,82,	108,"32");

        createRecordBrandSizeGuide(db, "ZARA",	"SWEATER-F",	0,	0,	0,      "0");
        createRecordBrandSizeGuide(db, "ZARA",	"SWEATER-F",	80,	58,	86,     "20");
        createRecordBrandSizeGuide(db, "ZARA",	"SWEATER-F",	82,	64,	90,		"22");
        createRecordBrandSizeGuide(db, "ZARA",	"SWEATER-F",	86,	66,	94,		"24");
        createRecordBrandSizeGuide(db, "ZARA",	"SWEATER-F",	90,	70,	98,		"26");
        createRecordBrandSizeGuide(db, "ZARA",	"SWEATER-F",	94,	74,	102,	"28");
        createRecordBrandSizeGuide(db, "ZARA",	"SWEATER-F",	98,	78,	106,	"30");
        createRecordBrandSizeGuide(db, "ZARA",	"SWEATER-F",	102,82,	110,	"32");
        createRecordBrandSizeGuide(db, "ZARA",	"SWEATER-F",	106,86,	114,	"34");

        createRecordBrandSizeGuide(db, "ADIDAS",	"SWEATER-H",	0,	0,	0,		"0");
        createRecordBrandSizeGuide(db, "ADIDAS",	"SWEATER-H",	82,	71,	82,		"28");
        createRecordBrandSizeGuide(db, "ADIDAS",	"SWEATER-H",	87,	75,	86,		"30");
        createRecordBrandSizeGuide(db, "ADIDAS",	"SWEATER-H",	94,	82,	93,		"32");
        createRecordBrandSizeGuide(db, "ADIDAS",	"SWEATER-H",	102,90,	101,	"34");
        createRecordBrandSizeGuide(db, "ADIDAS",	"SWEATER-H",	111,99,	110,	"36");
        createRecordBrandSizeGuide(db, "ADIDAS",	"SWEATER-H",	121,109,119,	"38");
        createRecordBrandSizeGuide(db, "ADIDAS",	"SWEATER-H",	132,121,128,	"40");
        createRecordBrandSizeGuide(db, "ADIDAS",	"SWEATER-H",	144,134,138,	"42");

        createRecordBrandSizeGuide(db, "ADIDAS",	"SWEATER-F",	0,	0,	0,		"0");
        createRecordBrandSizeGuide(db, "ADIDAS",	"SWEATER-F",	73,	57,	82,		"28");
        createRecordBrandSizeGuide(db, "ADIDAS",	"SWEATER-F",	77,	61,	86,		"30");
        createRecordBrandSizeGuide(db, "ADIDAS",	"SWEATER-F",	83,	67,	92,		"32");
        createRecordBrandSizeGuide(db, "ADIDAS",	"SWEATER-F",	89,	73,	98,		"34");
        createRecordBrandSizeGuide(db, "ADIDAS",	"SWEATER-F",	95,	79,	104,	"36");
        createRecordBrandSizeGuide(db, "ADIDAS",	"SWEATER-F",	102,86,	111,	"38");
        createRecordBrandSizeGuide(db, "ADIDAS",	"SWEATER-F",	110,94,	118,	"40");

        createRecordBrandSizeGuide(db, "MANGO",	"SWEATER-F",	0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "MANGO",	"SWEATER-F",	80,	60,	0,	"20");
        createRecordBrandSizeGuide(db, "MANGO",	"SWEATER-F",	82,	62,	0,	"22");
        createRecordBrandSizeGuide(db, "MANGO",	"SWEATER-F",	86,	66,	0,	"24");
        createRecordBrandSizeGuide(db, "MANGO",	"SWEATER-F",	90,	70,	0,	"26");
        createRecordBrandSizeGuide(db, "MANGO",	"SWEATER-F",	94,	74,	0,	"28");
        createRecordBrandSizeGuide(db, "MANGO",	"SWEATER-F",	98,	78,	0,	"30");
        createRecordBrandSizeGuide(db, "MANGO",	"SWEATER-F",	102,83,	0,	"32");
        createRecordBrandSizeGuide(db, "MANGO",	"SWEATER-F",	106,88,	0,	"34");
        createRecordBrandSizeGuide(db, "MANGO",	"SWEATER-F",	111,93,	0,	"36");

        createRecordBrandSizeGuide(db, "H&M","SWEATER-F",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "H&M","SWEATER-F",76,	60,	84,	"26");
        createRecordBrandSizeGuide(db, "H&M","SWEATER-F",78,	62,	86,	"28");
        createRecordBrandSizeGuide(db, "H&M","SWEATER-F",82,	64,	88,	"30");
        createRecordBrandSizeGuide(db, "H&M","SWEATER-F",86,	68,	92,	"32");
        createRecordBrandSizeGuide(db, "H&M","SWEATER-F",90,	72,	96,	"34");
        createRecordBrandSizeGuide(db, "H&M","SWEATER-F",94,	76,	100,	"36");
        createRecordBrandSizeGuide(db, "H&M","SWEATER-F",98,	80,	104,	"38");
        createRecordBrandSizeGuide(db, "H&M","SWEATER-F",102,	84,	108,	"40");
        createRecordBrandSizeGuide(db, "H&M","SWEATER-F",106,	88,	112,	"42");
        createRecordBrandSizeGuide(db, "H&M","SWEATER-F",110,	92,	116,	"44");
        createRecordBrandSizeGuide(db, "H&M","SWEATER-F",117,	97,	121,	"46");
        createRecordBrandSizeGuide(db, "H&M","SWEATER-F",123,	103,	127,	"48");

        createRecordBrandSizeGuide(db, "H&M", "SWEATER-H", 	0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "H&M", "SWEATER-H", 	82,	72,	0,	"30");
        createRecordBrandSizeGuide(db, "H&M",	"SWEATER-H", 	88,	76,	0,	"32");
        createRecordBrandSizeGuide(db, "H&M",	"SWEATER-H", 	92,	80,	0,	"34");
        createRecordBrandSizeGuide(db, "H&M",	"SWEATER-H", 	96,	84,	0,	"36");
        createRecordBrandSizeGuide(db, "H&M",	"SWEATER-H", 	100,	88, 0,		"38");
        createRecordBrandSizeGuide(db, "H&M",	"SWEATER-H", 	104,	92,	0,	"40");
        createRecordBrandSizeGuide(db, "H&M",	"SWEATER-H", 	108,	96,	0,	"42");
        createRecordBrandSizeGuide(db, "H&M",	"SWEATER-H", 	112,	100,	0,	"44");
        createRecordBrandSizeGuide(db, "H&M",	"SWEATER-H", 	116,	104, 0,	"46");
        createRecordBrandSizeGuide(db, "H&M",	"SWEATER-H", 	120,	108,	0,	"48");
        createRecordBrandSizeGuide(db, "H&M",	"SWEATER-H", 	124,    112,	0,	"50");


        // CHEMISE (Dimension = Tour de cou ou Tour de poitrine)
        createRecordBrandSizeGuide(db,"ASOS","CHEMISE",0,	0,0,"0");
        createRecordBrandSizeGuide(db,"ASOS","CHEMISE",36,	81,0,"14");
        createRecordBrandSizeGuide(db,"ASOS","CHEMISE",37,	86,0,"15");
        createRecordBrandSizeGuide(db,"ASOS","CHEMISE",38,	91,0,"16");
        createRecordBrandSizeGuide(db,"ASOS","CHEMISE",41,	96,0,"17");
        createRecordBrandSizeGuide(db,"ASOS","CHEMISE",43,	101,0,"17");
        createRecordBrandSizeGuide(db,"ASOS","CHEMISE",44,	106,0,"18");
        createRecordBrandSizeGuide(db,"ASOS","CHEMISE",46,	111,0,"19");

        createRecordBrandSizeGuide(db,"H&M","CHEMISE",	0,	0,	0,		"0");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",	35,	80,	68,		"35");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",	36,	84,	72,		"36");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",	37,	88,	76,		"37");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",	38,	92,	80,		"38");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",	39,	96,	84,		"39");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",	40,	100,88,	    "40");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",	41,	104,92,	    "41");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",	42,	108,96,	    "42");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",	43,	112,100,    "43");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",	44,	116,104,    "44");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",	45,	120,108,    "45");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",	46,	124,112,    "46");

        createRecordBrandSizeGuide(db,"KIABI","CHEMISE-H",	0,	0,	0,		"0");
        createRecordBrandSizeGuide(db,"KIABI","CHEMISE-H",	35,	0,	0,		"35");
        createRecordBrandSizeGuide(db,"KIABI","CHEMISE-H",	36,	0,	0,		"36");
        createRecordBrandSizeGuide(db,"KIABI","CHEMISE-H",	37,	0,	0,		"37");
        createRecordBrandSizeGuide(db,"KIABI","CHEMISE-H",	38,	0,	0,		"38");
        createRecordBrandSizeGuide(db,"KIABI","CHEMISE-H",	39,	0,	0,		"39");
        createRecordBrandSizeGuide(db,"KIABI","CHEMISE-H",	40,	0,	0,		"40");
        createRecordBrandSizeGuide(db,"KIABI","CHEMISE-H",	41,	0,	0,		"41");
        createRecordBrandSizeGuide(db,"KIABI","CHEMISE-H",	42,	0,	0,		"42");
        createRecordBrandSizeGuide(db,"KIABI","CHEMISE-H",	43,	0,	0,		"43");
        createRecordBrandSizeGuide(db,"KIABI","CHEMISE-H",	44,	0,	0,		"44");
        createRecordBrandSizeGuide(db,"KIABI","CHEMISE-H",	45,	0,	0,		"45");
        createRecordBrandSizeGuide(db,"KIABI","CHEMISE-H",	46,	0,	0,		"46");
        createRecordBrandSizeGuide(db,"KIABI","CHEMISE-H",	47,	0,	0,		"47");
        createRecordBrandSizeGuide(db,"KIABI","CHEMISE-H",	48,	0,	0,		"48");

        createRecordBrandSizeGuide(db,"DEVRED","CHEMISE-H",	0,	0,	0,		"0");
        createRecordBrandSizeGuide(db,"DEVRED","CHEMISE-H",	35,	0,	0,		"35");
        createRecordBrandSizeGuide(db,"DEVRED","CHEMISE-H",	36,	0,	0,		"36");
        createRecordBrandSizeGuide(db,"DEVRED","CHEMISE-H",	37,	0,	0,		"37");
        createRecordBrandSizeGuide(db,"DEVRED","CHEMISE-H",	38,	0,	0,		"38");
        createRecordBrandSizeGuide(db,"DEVRED","CHEMISE-H",	39,	0,	0,		"39");
        createRecordBrandSizeGuide(db,"DEVRED","CHEMISE-H",	40,	0,	0,		"40");
        createRecordBrandSizeGuide(db,"DEVRED","CHEMISE-H",	41,	0,	0,		"41");
        createRecordBrandSizeGuide(db,"DEVRED","CHEMISE-H",	42,	0,	0,		"42");
        createRecordBrandSizeGuide(db,"DEVRED","CHEMISE-H",	43,	0,	0,		"43");
        createRecordBrandSizeGuide(db,"DEVRED","CHEMISE-H",	44,	0,	0,		"44");
        createRecordBrandSizeGuide(db,"DEVRED","CHEMISE-H",	45,	0,	0,		"45");
        createRecordBrandSizeGuide(db,"DEVRED","CHEMISE-H",	46,	0,	0,		"46");
        createRecordBrandSizeGuide(db,"DEVRED","CHEMISE-H",	47,	0,	0,		"47");
        createRecordBrandSizeGuide(db,"DEVRED","CHEMISE-H",	48,	0,	0,		"48");

        createRecordBrandSizeGuide(db,"CELIO","CHEMISE-H",	0,	0,	0,		"0");
        createRecordBrandSizeGuide(db,"CELIO","CHEMISE-H",	35,	0,	0,		"35");
        createRecordBrandSizeGuide(db,"CELIO","CHEMISE-H",	36,	0,	0,		"36");
        createRecordBrandSizeGuide(db,"CELIO","CHEMISE-H",	37,	0,	0,		"37");
        createRecordBrandSizeGuide(db,"CELIO","CHEMISE-H",	38,	0,	0,		"38");
        createRecordBrandSizeGuide(db,"CELIO","CHEMISE-H",	39,	0,	0,		"39");
        createRecordBrandSizeGuide(db,"CELIO","CHEMISE-H",	40,	0,	0,		"40");
        createRecordBrandSizeGuide(db,"CELIO","CHEMISE-H",	41,	0,	0,		"41");
        createRecordBrandSizeGuide(db,"CELIO","CHEMISE-H",	42,	0,	0,		"42");
        createRecordBrandSizeGuide(db,"CELIO","CHEMISE-H",	43,	0,	0,		"43");
        createRecordBrandSizeGuide(db,"CELIO","CHEMISE-H",	44,	0,	0,		"44");
        createRecordBrandSizeGuide(db,"CELIO","CHEMISE-H",	45,	0,	0,		"45");
        createRecordBrandSizeGuide(db,"CELIO","CHEMISE-H",	46,	0,	0,		"46");
        createRecordBrandSizeGuide(db,"CELIO","CHEMISE-H",	47,	0,	0,		"47");
        createRecordBrandSizeGuide(db,"CELIO","CHEMISE-H",	48,	0,	0,		"48");

        // CHEMISIERS (OU CHEMISES FEMME)

        createRecordBrandSizeGuide(db,"DESIGUAL",	"CHEMISIER",	0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"CHEMISIER",	84,	64,	90,	"14");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"CHEMISIER",	86,	66,	92,	"15");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"CHEMISIER",	90,	70,	96,	"16");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"CHEMISIER",	94,	74,	100,	"17");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"CHEMISIER",	100,80,	106,	"18");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"CHEMISIER",	106,86,	112,	"19");

        createRecordBrandSizeGuide(db, "KIABI","CHEMISIER",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "KIABI","CHEMISIER",76,	60,	84,	"4");
        createRecordBrandSizeGuide(db, "KIABI","CHEMISIER",78,	62,	86,	"6");
        createRecordBrandSizeGuide(db, "KIABI","CHEMISIER",82,	64,	88,	"8");
        createRecordBrandSizeGuide(db, "KIABI","CHEMISIER",86,	68,	92,	"10");
        createRecordBrandSizeGuide(db, "KIABI","CHEMISIER",90,	72,	96,	"12");
        createRecordBrandSizeGuide(db, "KIABI","CHEMISIER",94,	76,	100,	"14");
        createRecordBrandSizeGuide(db, "KIABI","CHEMISIER",98,	80,	104,	"16");
        createRecordBrandSizeGuide(db, "KIABI","CHEMISIER",102,	84,	108,	"18");
        createRecordBrandSizeGuide(db, "KIABI","CHEMISIER",106,	88,	112,	"20");
        createRecordBrandSizeGuide(db, "KIABI","CHEMISIER",110,	92,	116,	"22");
        createRecordBrandSizeGuide(db, "KIABI","CHEMISIER",117,	97,	121,	"24");
        createRecordBrandSizeGuide(db, "KIABI","CHEMISIER",123,	103,	127,	"26");
        createRecordBrandSizeGuide(db, "KIABI","CHEMISIER",129,	109,	133	,"28");
        createRecordBrandSizeGuide(db, "KIABI","CHEMISIER",135,	115,	139,	"30");
        createRecordBrandSizeGuide(db, "KIABI","CHEMISIER",141,	121,	145,	"32");

        createRecordBrandSizeGuide(db, "H&M","CHEMISIER",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "H&M","CHEMISIER",76,	60,	84,	"4");
        createRecordBrandSizeGuide(db, "H&M","CHEMISIER",80,	64,	88,	"6");
        createRecordBrandSizeGuide(db, "H&M","CHEMISIER",84,	68,	92,	"8");
        createRecordBrandSizeGuide(db, "H&M","CHEMISIER",88,	72,	96,	"10");
        createRecordBrandSizeGuide(db, "H&M","CHEMISIER",92,	76,	100,	"12");
        createRecordBrandSizeGuide(db, "H&M","CHEMISIER",96,	80,	104,	"14");
        createRecordBrandSizeGuide(db, "H&M","CHEMISIER",100,	84,	108,	"16");
        createRecordBrandSizeGuide(db, "H&M","CHEMISIER",104	,88,	112,	"18");
        createRecordBrandSizeGuide(db, "H&M","CHEMISIER",110	,94,	117,	"20");
        createRecordBrandSizeGuide(db, "H&M","CHEMISIER",116,	100,	122,	"22");
        createRecordBrandSizeGuide(db, "H&M","CHEMISIER",122,	106,	127,	"24");
        createRecordBrandSizeGuide(db, "H&M","CHEMISIER",128,	112,	132,	"26");
        createRecordBrandSizeGuide(db, "H&M","CHEMISIER",134,	118,	137,	"28");
        createRecordBrandSizeGuide(db, "H&M","CHEMISIER",140,	124,	142,	"30");

        createRecordBrandSizeGuide(db, "ZARA","CHEMISIER",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "ZARA","CHEMISIER",71,	48,	75,	"0");
        createRecordBrandSizeGuide(db, "ZARA","CHEMISIER",73,	50,	78,	"2");
        createRecordBrandSizeGuide(db, "ZARA","CHEMISIER",76,	53,	80,	"4");
        createRecordBrandSizeGuide(db, "ZARA","CHEMISIER",79,	56,	83,	"6");
        createRecordBrandSizeGuide(db, "ZARA","CHEMISIER",83,	61,	87,	"8");
        createRecordBrandSizeGuide(db, "ZARA","CHEMISIER",87,	66,	92,	"10");
        createRecordBrandSizeGuide(db, "ZARA","CHEMISIER",95,	71,	97,	"12");
        createRecordBrandSizeGuide(db, "ZARA","CHEMISIER",100,	76,	102,	"14");
        createRecordBrandSizeGuide(db, "ZARA","CHEMISIER",105,	85,	107,	"16");
        createRecordBrandSizeGuide(db, "ZARA","CHEMISIER",110,	91,	112,	"18");
        createRecordBrandSizeGuide(db, "ZARA","CHEMISIER",115,	98,	118,	"20");
        createRecordBrandSizeGuide(db, "ZARA","CHEMISIER",122,	105,	125,	"22");
        createRecordBrandSizeGuide(db, "ZARA","CHEMISIER",128,	110	,131,	"24");
        createRecordBrandSizeGuide(db, "ZARA","CHEMISIER",134,	125,	137,	"26");

        createRecordBrandSizeGuide(db, "MANGO",	"CHEMISIER",	0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "MANGO",	"CHEMISIER",	80,	60,	0,	"20");
        createRecordBrandSizeGuide(db, "MANGO",	"CHEMISIER",	82,	62,	0,	"22");
        createRecordBrandSizeGuide(db, "MANGO",	"CHEMISIER",	86,	66,	0,	"24");
        createRecordBrandSizeGuide(db, "MANGO",	"CHEMISIER",	90,	70,	0,	"26");
        createRecordBrandSizeGuide(db, "MANGO",	"CHEMISIER",	94,	74,	0,	"28");
        createRecordBrandSizeGuide(db, "MANGO",	"CHEMISIER",	98,	78,	0,	"30");
        createRecordBrandSizeGuide(db, "MANGO",	"CHEMISIER",	102,83,	0,	"32");
        createRecordBrandSizeGuide(db, "MANGO",	"CHEMISIER",	106,88,	0,	"34");
        createRecordBrandSizeGuide(db, "MANGO",	"CHEMISIER",	111,93,	0,	"36");

        // PULL (Dimension = Tour de poitrine)
        createRecordBrandSizeGuide(db,"ASOS","PULL-H",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"ASOS","PULL-H",0,0,	81,	"16");
        createRecordBrandSizeGuide(db,"ASOS","PULL-H",0,0,	86,	"18");
        createRecordBrandSizeGuide(db,"ASOS","PULL-H",0,0,	91,	"20");
        createRecordBrandSizeGuide(db,"ASOS","PULL-H",0,0,	96,	"22");
        createRecordBrandSizeGuide(db,"ASOS","PULL-H",0,0,	101,	"24");
        createRecordBrandSizeGuide(db,"ASOS","PULL-H",0,0,	106	,"24");
        createRecordBrandSizeGuide(db,"ASOS","PULL-H",0,0,	111,	"26");

        createRecordBrandSizeGuide(db,"DESIGUAL",	"PULL-F",	0,	0,	0,	"18");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"PULL-F",	0,	0,	86,	"18");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"PULL-F",	0,	0,	90,	"20");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"PULL-F",	0,	0,	94,	"22");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"PULL-F",	0,	0,	98,	"24");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"PULL-F",	0,	0,	102,"26");

        createRecordBrandSizeGuide(db, "KIABI","PULL-F",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "KIABI","PULL-F",76,	60,	84,	"24");
        createRecordBrandSizeGuide(db, "KIABI","PULL-F",78,	62,	86,	"26");
        createRecordBrandSizeGuide(db, "KIABI","PULL-F",82,	64,	88,	"28");
        createRecordBrandSizeGuide(db, "KIABI","PULL-F",86,	68,	92,	"30");
        createRecordBrandSizeGuide(db, "KIABI","PULL-F",90,	72,	96,	"32");
        createRecordBrandSizeGuide(db, "KIABI","PULL-F",94,	76,	100,	"34");
        createRecordBrandSizeGuide(db, "KIABI","PULL-F",98,	80,	104,	"36");
        createRecordBrandSizeGuide(db, "KIABI","PULL-F",102,	84,	108,	"38");
        createRecordBrandSizeGuide(db, "KIABI","PULL-F",106,	88,	112,	"40");
        createRecordBrandSizeGuide(db, "KIABI","PULL-F",110,	92,	116,	"42");
        createRecordBrandSizeGuide(db, "KIABI","PULL-F",117,	97,	121,	"44");
        createRecordBrandSizeGuide(db, "KIABI","PULL-F",123,	103,	127,	"46");
        createRecordBrandSizeGuide(db, "KIABI","PULL-F",129,	109,	133	,"48");
        createRecordBrandSizeGuide(db, "KIABI","PULL-F",135,	115,	139,	"50");
        createRecordBrandSizeGuide(db, "KIABI","PULL-F",141,	121,	145,	"52");

        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	0, 0,0,		"0");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	78,0,0,		"28");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	82,	0,0,	"30");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	86,	0,0,	"32");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	90,	0,0,	"34");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	94,	0,0,	"36");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	98,	0,0,	"38");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	102,0,0,		"40");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	106,0,0,		"42");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	110,0,0,		"44");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	114,0,0,		"46");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	118,0,0,		"48");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	122,0,0,		"50");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	126,0,0,		"52");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	130,0,0,		"54");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	134,0,0,		"56");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	138,0,0,		"58");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	142,0,0,		"60");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	146,0,0,		"62");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	150,0,0,		"64");
        createRecordBrandSizeGuide(db, "KIABI",	"PULL-H",	154,0,0,		"68");

        createRecordBrandSizeGuide(db, "KOOKAI",	"PULL-F",	0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "KOOKAI",	"PULL-F",	80,	62,	88,	"22");
        createRecordBrandSizeGuide(db, "KOOKAI",	"PULL-F",    84,	66,	92,	"24");
        createRecordBrandSizeGuide(db, "KOOKAI",	"PULL-F",	88,	70,	96,	"26");
        createRecordBrandSizeGuide(db, "KOOKAI",	"PULL-F",	92,	74,	100,"28");
        createRecordBrandSizeGuide(db, "KOOKAI",	"PULL-F",	96,	78,	104,"30");
        createRecordBrandSizeGuide(db, "KOOKAI",	"PULL-F",	100,82,	108,"32");

        createRecordBrandSizeGuide(db, "ZARA",	"PULL-F",	0,	0,	0,      "0");
        createRecordBrandSizeGuide(db, "ZARA",	"PULL-F",	80,	58,	86,     "20");
        createRecordBrandSizeGuide(db, "ZARA",	"PULL-F",	82,	64,	90,		"22");
        createRecordBrandSizeGuide(db, "ZARA",	"PULL-F",	86,	66,	94,		"24");
        createRecordBrandSizeGuide(db, "ZARA",	"PULL-F",	90,	70,	98,		"26");
        createRecordBrandSizeGuide(db, "ZARA",	"PULL-F",	94,	74,	102,	"28");
        createRecordBrandSizeGuide(db, "ZARA",	"PULL-F",	98,	78,	106,	"30");
        createRecordBrandSizeGuide(db, "ZARA",	"PULL-F",	102,82,	110,	"32");
        createRecordBrandSizeGuide(db, "ZARA",	"PULL-F",	106,86,	114,	"34");

        createRecordBrandSizeGuide(db, "ADIDAS",	"PULL-H",	0,	0,	0,		"0");
        createRecordBrandSizeGuide(db, "ADIDAS",	"PULL-H",	82,	71,	82,		"28");
        createRecordBrandSizeGuide(db, "ADIDAS",	"PULL-H",	87,	75,	86,		"30");
        createRecordBrandSizeGuide(db, "ADIDAS",	"PULL-H",	94,	82,	93,		"32");
        createRecordBrandSizeGuide(db, "ADIDAS",	"PULL-H",	102,90,	101,	"34");
        createRecordBrandSizeGuide(db, "ADIDAS",	"PULL-H",	111,99,	110,	"36");
        createRecordBrandSizeGuide(db, "ADIDAS",	"PULL-H",	121,109,119,	"38");
        createRecordBrandSizeGuide(db, "ADIDAS",	"PULL-H",	132,121,128,	"40");
        createRecordBrandSizeGuide(db, "ADIDAS",	"PULL-H",	144,134,138,	"42");

        createRecordBrandSizeGuide(db, "ADIDAS",	"PULL-F",	0,	0,	0,		"0");
        createRecordBrandSizeGuide(db, "ADIDAS",	"PULL-F",	73,	57,	82,		"28");
        createRecordBrandSizeGuide(db, "ADIDAS",	"PULL-F",	77,	61,	86,		"30");
        createRecordBrandSizeGuide(db, "ADIDAS",	"PULL-F",	83,	67,	92,		"32");
        createRecordBrandSizeGuide(db, "ADIDAS",	"PULL-F",	89,	73,	98,		"34");
        createRecordBrandSizeGuide(db, "ADIDAS",	"PULL-F",	95,	79,	104,	"36");
        createRecordBrandSizeGuide(db, "ADIDAS",	"PULL-F",	102,86,	111,	"38");
        createRecordBrandSizeGuide(db, "ADIDAS",	"PULL-F",	110,94,	118,	"40");

        createRecordBrandSizeGuide(db, "MANGO",	"PULL-F",	0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "MANGO",	"PULL-F",	80,	60,	0,	"20");
        createRecordBrandSizeGuide(db, "MANGO",	"PULL-F",	82,	62,	0,	"22");
        createRecordBrandSizeGuide(db, "MANGO",	"PULL-F",	86,	66,	0,	"24");
        createRecordBrandSizeGuide(db, "MANGO",	"PULL-F",	90,	70,	0,	"26");
        createRecordBrandSizeGuide(db, "MANGO",	"PULL-F",	94,	74,	0,	"28");
        createRecordBrandSizeGuide(db, "MANGO",	"PULL-F",	98,	78,	0,	"30");
        createRecordBrandSizeGuide(db, "MANGO",	"PULL-F",	102,83,	0,	"32");
        createRecordBrandSizeGuide(db, "MANGO",	"PULL-F",	106,88,	0,	"34");
        createRecordBrandSizeGuide(db, "MANGO",	"PULL-F",	111,93,	0,	"36");

        createRecordBrandSizeGuide(db, "H&M","PULL-F",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "H&M","PULL-F",76,	60,	84,	"26");
        createRecordBrandSizeGuide(db, "H&M","PULL-F",78,	62,	86,	"28");
        createRecordBrandSizeGuide(db, "H&M","PULL-F",82,	64,	88,	"30");
        createRecordBrandSizeGuide(db, "H&M","PULL-F",86,	68,	92,	"32");
        createRecordBrandSizeGuide(db, "H&M","PULL-F",90,	72,	96,	"34");
        createRecordBrandSizeGuide(db, "H&M","PULL-F",94,	76,	100,	"36");
        createRecordBrandSizeGuide(db, "H&M","PULL-F",98,	80,	104,	"38");
        createRecordBrandSizeGuide(db, "H&M","PULL-F",102,	84,	108,	"40");
        createRecordBrandSizeGuide(db, "H&M","PULL-F",106,	88,	112,	"42");
        createRecordBrandSizeGuide(db, "H&M","PULL-F",110,	92,	116,	"44");
        createRecordBrandSizeGuide(db, "H&M","PULL-F",117,	97,	121,	"46");
        createRecordBrandSizeGuide(db, "H&M","PULL-F",123,	103,	127,	"48");

        createRecordBrandSizeGuide(db, "H&M", "PULL-H", 	0,	0,	0,	"0");
        createRecordBrandSizeGuide(db, "H&M", "PULL-H", 	82,	72,	0,	"30");
        createRecordBrandSizeGuide(db, "H&M",	"PULL-H", 	88,	76,	0,	"32");
        createRecordBrandSizeGuide(db, "H&M",	"PULL-H", 	92,	80,	0,	"34");
        createRecordBrandSizeGuide(db, "H&M",	"PULL-H", 	96,	84,	0,	"36");
        createRecordBrandSizeGuide(db, "H&M",	"PULL-H", 	100,	88, 0,		"38");
        createRecordBrandSizeGuide(db, "H&M",	"PULL-H", 	104,	92,	0,	"40");
        createRecordBrandSizeGuide(db, "H&M",	"PULL-H", 	108,	96,	0,	"42");
        createRecordBrandSizeGuide(db, "H&M",	"PULL-H", 	112,	100,	0,	"44");
        createRecordBrandSizeGuide(db, "H&M",	"PULL-H", 	116,	104, 0,	"46");
        createRecordBrandSizeGuide(db, "H&M",	"PULL-H", 	120,	108,	0,	"48");
        createRecordBrandSizeGuide(db, "H&M",	"PULL-H", 	124,    112,	0,	"50");

        // SLIP, CALECONS (Dim = Tour de taille et tour de hanche)
        createRecordBrandSizeGuide(db,"ASOS","SLIP-H",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"ASOS","SLIP-H",0,66,0,	"0");
        createRecordBrandSizeGuide(db,"ASOS","SLIP-H",0,71,0,	"1");
        createRecordBrandSizeGuide(db,"ASOS","SLIP-H",0,76,0,	"1");
        createRecordBrandSizeGuide(db,"ASOS","SLIP-H",0,81,0,	"2");
        createRecordBrandSizeGuide(db,"ASOS","SLIP-H",0,86,0,	"3");
        createRecordBrandSizeGuide(db,"ASOS","SLIP-H",0,91,0,	"4");
        createRecordBrandSizeGuide(db,"ASOS","SLIP-H",0,96,0,	"5");

        createRecordBrandSizeGuide(db,"CELIO",	"SLIP-H",0,	80,	0,	"0");
        createRecordBrandSizeGuide(db,"CELIO",	"SLIP-H",0,	82,	0,	"1");
        createRecordBrandSizeGuide(db,"CELIO",	"SLIP-H",0,	85,	0,	"2");
        createRecordBrandSizeGuide(db,"CELIO",	"SLIP-H",0,	88,	0,	"3");
        createRecordBrandSizeGuide(db,"CELIO",	"SLIP-H",0,	92,	0,	"4");
        createRecordBrandSizeGuide(db,"CELIO",	"SLIP-H",0,	97,	0,	"5");
        createRecordBrandSizeGuide(db,"CELIO",	"SLIP-H",0,	104,0,	"6");

        createRecordBrandSizeGuide(db,"MANGO",	"SLIP-H",	0,	0,	 0,   "0");
        createRecordBrandSizeGuide(db,"MANGO",	"SLIP-H",	80,	92,	 0,   "2");
        createRecordBrandSizeGuide(db,"MANGO",	"SLIP-H",	84,	96,	 0,   "3");
        createRecordBrandSizeGuide(db,"MANGO",	"SLIP-H",	90,	100, 0,   "4");
        createRecordBrandSizeGuide(db,"MANGO",	"SLIP-H",	96,	104, 0,   "5");

        // VESTE FEMME(Dim = Tour de poitrine, Tour de Taille, Tour de Hanche)
        createRecordBrandSizeGuide(db,"ASOS","VESTE-F",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"ASOS","VESTE-F",	81,0,0,	"36");
        createRecordBrandSizeGuide(db,"ASOS","VESTE-F",	86,0,0,	"38");
        createRecordBrandSizeGuide(db,"ASOS","VESTE-F",	91,0,0,	"40");
        createRecordBrandSizeGuide(db,"ASOS","VESTE-F",	96,0,0,	"42");
        createRecordBrandSizeGuide(db,"ASOS","VESTE-F",	101,0,0,	"44");
        createRecordBrandSizeGuide(db,"ASOS","VESTE-F",	106,0,0,	"45");
        createRecordBrandSizeGuide(db,"ASOS","VESTE-F",	111,0,0,	"46");

        createRecordBrandSizeGuide(db,"DESIGUAL","VESTE-F",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"VESTE-F",85,	65,	91,	"36");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"VESTE-F",89,	69,	95,	"38");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"VESTE-F",93,	73,	99,	"40");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"VESTE-F",98,	78,	104,	"42");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"VESTE-F",104,	84,	110,	"44");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"VESTE-F",110,	90,	116,	"46");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"VESTE-F",116,	96,	122,	"48");

        // VESTE HOMME (Dim = Tour de poitrine)

        createRecordBrandSizeGuide(db,"DESIGUAL",	"VESTE-H",	0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"VESTE-H",	96,	0,	0,	"38");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"VESTE-H",	100,0,	0,	"40");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"VESTE-H",	104,0,	0,	"42");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"VESTE-H",	108,0,	0,	"44");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"VESTE-H",	114,0,	0,	"46");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"VESTE-H",	120,0,	0,	"48");


        // MANTEAU (DIM = Tour de poitrine, Tour de taille, Tour de hanches)
        createRecordBrandSizeGuide(db,"ASOS","MANTEAU-F",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"ASOS","MANTEAU-F",	81,0,0,	"36");
        createRecordBrandSizeGuide(db,"ASOS","MANTEAU-F",	86,0,0,	"38");
        createRecordBrandSizeGuide(db,"ASOS","MANTEAU-F",	91,0,0,	"40");
        createRecordBrandSizeGuide(db,"ASOS","MANTEAU-F",	96,0,0,	"42");
        createRecordBrandSizeGuide(db,"ASOS","MANTEAU-F",	101,0,0,	"44");
        createRecordBrandSizeGuide(db,"ASOS","MANTEAU-F",	106,0,0,	"45");
        createRecordBrandSizeGuide(db,"ASOS","MANTEAU-F",	111,0,0,	"46");

        createRecordBrandSizeGuide(db,"DESIGUAL","MANTEAU-F",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-F",85,	65,	91,	"36");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-F",89,	69,	95,	"38");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-F",93,	73,	99,	"40");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-F",98,	78,	104,	"42");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-F",104,	84,	110,	"44");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-F",110,	90,	116,	"46");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-F",116,	96,	122,	"48");

        // MANTEAU (Dim = Tour de poitrine)

        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-H",	0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-H",	96,	0,	0,	"38");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-H",	100,0,	0,	"40");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-H",	104,0,	0,	"42");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-H",	108,0,	0,	"44");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-H",	114,0,	0,	"46");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-H",	120,0,	0,	"48");


        // BLOUSON (Dim = Tour de poitrine)
        createRecordBrandSizeGuide(db,"ASOS","BLOUSON-F",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"ASOS","BLOUSON-F",	81,0,0,	"36");
        createRecordBrandSizeGuide(db,"ASOS","BLOUSON-F",	86,0,0,	"38");
        createRecordBrandSizeGuide(db,"ASOS","BLOUSON-F",	91,0,0,	"40");
        createRecordBrandSizeGuide(db,"ASOS","BLOUSON-F",	96,0,0,	"42");
        createRecordBrandSizeGuide(db,"ASOS","BLOUSON-F",	101,0,0,	"44");
        createRecordBrandSizeGuide(db,"ASOS","BLOUSON-F",	106,0,0,	"45");
        createRecordBrandSizeGuide(db,"ASOS","BLOUSON-F",	111,0,0,	"46");

        createRecordBrandSizeGuide(db,"DESIGUAL","BLOUSON-F",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"BLOUSON-F",85,	65,	91,	"36");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"BLOUSON-F",89,	69,	95,	"38");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"BLOUSON-F",93,	73,	99,	"40");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"BLOUSON-F",98,	78,	104,	"42");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"BLOUSON-F",104,	84,	110,	"44");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"BLOUSON-F",110,	90,	116,	"46");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"BLOUSON-F",116,	96,	122,	"48");

        createRecordBrandSizeGuide(db,"DESIGUAL",	"BLOUSON-H",	0,	    0,	0,	"0");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"BLOUSON-H",	96,	    0,	0,	"38");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"BLOUSON-H",	100,	0,	0,	"40");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"BLOUSON-H",	104,	0,	0,	"42");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"BLOUSON-H",	108,	0,	0,	"44");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"BLOUSON-H",	114,	0,	0,	"46");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"BLOUSON-H",	120,	0,	0,	"48");

        // MANTEAU HOMME (Dim = Tour de poitrine)

        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-H",	0,	    0,	0,	"0");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-H",	96,	    0,	0,	"38");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-H",	100,	0,	0,	"40");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-H",	104,	0,	0,	"42");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-H",	108,	0,	0,	"44");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-H",    114,	0,	0,	"46");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"MANTEAU-H",	120,	0,	0,	"48");


        // COSTUME (Veste. Dim = Tour de poitrine)
        createRecordBrandSizeGuide(db,"ASOS","COSTUME",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"ASOS","COSTUME",0,	0,	86,	"34");
        createRecordBrandSizeGuide(db,"ASOS","COSTUME",0,	0,	91,	"36");
        createRecordBrandSizeGuide(db,"ASOS","COSTUME",0,	0,	96,	"38");
        createRecordBrandSizeGuide(db,"ASOS","COSTUME",0,	0,	101,	"40");
        createRecordBrandSizeGuide(db,"ASOS","COSTUME",0,	0,	106,	"42");
        createRecordBrandSizeGuide(db,"ASOS","COSTUME",0,	0,	111,	"44");
        createRecordBrandSizeGuide(db,"ASOS","COSTUME",0,	0,	116,	"46");

        // CHAUSSURE (Dim = Longueur de pied )
        createRecordBrandSizeGuide(db,"ASOS",	"CHAUSSURE-F",0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"ASOS",	"CHAUSSURE-F",0,	0,	24.6,	"6");
        createRecordBrandSizeGuide(db,"ASOS",	"CHAUSSURE-F",0,	0,	25.4,	"7");
        createRecordBrandSizeGuide(db,"ASOS",	"CHAUSSURE-F",0,	0,	26.2,	"8");
        createRecordBrandSizeGuide(db,"ASOS",	"CHAUSSURE-F",0,	0,	27.1,	"9");
        createRecordBrandSizeGuide(db,"ASOS",	"CHAUSSURE-F",0,	0,	27.9,	"10");
        createRecordBrandSizeGuide(db,"ASOS",	"CHAUSSURE-F",0,	0,	28.8,	"11");
        createRecordBrandSizeGuide(db,"ASOS",	"CHAUSSURE-F",0,	0,	29.6,	"12");

        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H", 0,0,	0,	"0");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H", 0,0,	22.1,	"3.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	22.5,	"4");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	22.9,	"4.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	23.3,	"5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	23.8,	"5.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	24.2,	"6");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	24.6,	"6.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	25,     "7");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	25.5,	"7.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	25.9,	"8");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	26.3,	"8.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	26.7,	"9");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	27.1,	"9.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	27.6,	"10");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	28,     "10.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	28.4,	"11");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	28.8,	"11.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	29.3,	"12");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	29.7,	"12.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	30.1,	"13");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	30.5,	"13.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	31,     "14");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	31.4,	"14.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	32.2,	"15");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	33.1,	"15.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	33.9,	"16");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	34.8,	"17");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-H",0,0,	35.6,	"18");

        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F", 0,0,	0,	"0");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F", 0,0,	22.1,	"3.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	22.5,	"4");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	22.9,	"4.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	23.3,	"5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	23.8,	"5.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	24.2,	"6");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	24.6,	"6.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	25,     "7");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	25.5,	"7.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	25.9,	"8");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	26.3,	"8.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	26.7,	"9");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	27.1,	"9.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	27.6,	"10");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	28,     "10.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	28.4,	"11");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	28.8,	"11.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	29.3,	"12");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	29.7,	"12.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	30.1,	"13");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	30.5,	"13.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	31,     "14");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	31.4,	"14.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	32.2,	"15");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	33.1,	"15.5");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	33.9,	"16");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	34.8,	"17");
        createRecordBrandSizeGuide(db,"ADIDAS", "CHAUSSURE-F",0,0,	35.6,	"18");

        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	0,	    "0");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	23,	    "4.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	23.5,	"5.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	24,	    "6");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	24.5,	"6.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	25,	    "7");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	25.5,	"8.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	26,	    "8");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	26.5,	"9");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	27,	    "9.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	27.5,	"10");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	28,	    "10.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	28.5,	"11");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	29,	    "11.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	29.5,	"12");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	30,	    "12.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	30.5,	"13");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	31,	    "13.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	31.5,	"14");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	32,	    "14.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	32.5,	"15");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	33,	    "15.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	33.5,	"16");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	34,	    "16.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	34.5,	"17");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	35,	    "17,5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	35.5,	"18");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	36,	    "18.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	36.5,	"19");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	37,	    "19.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	37.5,	"20");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	38,	    "20.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	38.5,	"21");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	39,	    "21.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	39.5,	"22");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	40,	    "22.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-H",0,0,	40.5,	"23");

        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	0,	    "0");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	23,	    "4");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	23.5,	"5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	24,	    "5.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	24.5,	"6");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	25,	    "6.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	25.5,	"7");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	26,	    "7.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	26.5,	"8");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	27,	    "8.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	27.5,	"9");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	28,	    "9.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	28.5,	"10");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	29,	    "10.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	29.5,	"11");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	30,	    "11.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	30.5,	"12");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	31,	    "12.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	31.5,	"13");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	32,	    "13.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	32.5,	"14");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	33,	    "14.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	33.5,	"15");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	34,	    "15.5");
        createRecordBrandSizeGuide(db,"NIKE",	"CHAUSSURE-F",0,0,	34.5,	"16");

        // PANTALONS

        createRecordBrandSizeGuide(db,"DESIGUAL",	"PANTALON-F",	0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"PANTALON-F",	62,	88,	0,	"24");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"PANTALON-F",	66,	92,	0,	"26");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"PANTALON-F",	70,	96,	0,	"28");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"PANTALON-F",	74,	100,0,	"30");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"PANTALON-F",	78,	104,0,	"32");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"PANTALON-F",	82,	108,0,	"34");

        createRecordBrandSizeGuide(db,"MANGO",	"PANTALON-H",	0,	0,	0,  "0");
        createRecordBrandSizeGuide(db,"MANGO",	"PANTALON-H",	72,	88,	0,  "26");
        createRecordBrandSizeGuide(db,"MANGO",	"PANTALON-H",	76,	92,	0,  "28");
        createRecordBrandSizeGuide(db,"MANGO",	"PANTALON-H",	80,	96,	0,  "30");
        createRecordBrandSizeGuide(db,"MANGO",	"PANTALON-H",	84,	100,0,	"32");
        createRecordBrandSizeGuide(db,"MANGO",	"PANTALON-H",	88,	104,0,  "34");
        createRecordBrandSizeGuide(db,"MANGO",	"PANTALON-H",	94,	110,0,	"36");

        createRecordBrandSizeGuide(db,"SANDRO",	"PANTALON-F",	0, 0,	0,	"0");
        createRecordBrandSizeGuide(db,"SANDRO",	"PANTALON-F",	0, 90,	0,	"24");
        createRecordBrandSizeGuide(db,"SANDRO",	"PANTALON-F",	0, 94,	0,	"26");
        createRecordBrandSizeGuide(db,"SANDRO",	"PANTALON-F",	0, 98,	0,	"28");
        createRecordBrandSizeGuide(db,"SANDRO",	"PANTALON-F",	0, 104,	0,	"30");
        createRecordBrandSizeGuide(db,"SANDRO",	"PANTALON-F",	0, 110,	0,	"32");

        createRecordBrandSizeGuide(db,"DEVRED",	"PANTALON-H",	0,0,0,		"0");
        createRecordBrandSizeGuide(db,"DEVRED",	"PANTALON-H",	74,0,0,		"28");
        createRecordBrandSizeGuide(db,"DEVRED",	"PANTALON-H",	78,0,0,		"30");
        createRecordBrandSizeGuide(db,"DEVRED",	"PANTALON-H",	82,0,0,		"32");
        createRecordBrandSizeGuide(db,"DEVRED",	"PANTALON-H",	86,0,0,		"34");
        createRecordBrandSizeGuide(db,"DEVRED",	"PANTALON-H",	90,0,0,		"36");
        createRecordBrandSizeGuide(db,"DEVRED",	"PANTALON-H",	94,0,0,		"38");
        createRecordBrandSizeGuide(db,"DEVRED",	"PANTALON-H",	98,0,0,		"40");
        createRecordBrandSizeGuide(db,"DEVRED",	"PANTALON-H",	102,0,0,	"42");



        // JEANS

        createRecordBrandSizeGuide(db,"DESIGUAL",	"JEANS-F",	0,	0,	0,	"0");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"JEANS-F",	62,	88,	0,	"24");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"JEANS-F",	66,	92,	0,	"26");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"JEANS-F",	70,	96,	0,	"28");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"JEANS-F",	74,	100,0,	"30");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"JEANS-F",	78,	104,0,	"32");
        createRecordBrandSizeGuide(db,"DESIGUAL",	"JEANS-F",	82,	108,0,	"34");


        /*

        createRecordBrandSizeGuide(db,"H&M","PANTALON",0,0,0,"0");
        createRecordBrandSizeGuide(db,"H&M","PANTALON",0,0,80,"2");
        createRecordBrandSizeGuide(db,"H&M","PANTALON",0,0,83,"4");
        createRecordBrandSizeGuide(db,"H&M","PANTALON",0,0,85,"6");
        createRecordBrandSizeGuide(db,"H&M","PANTALON",0,0,88,"8");
        createRecordBrandSizeGuide(db,"H&M","PANTALON",0,0,91,"10");
        createRecordBrandSizeGuide(db,"H&M","PANTALON",0,0,95,"12");
        createRecordBrandSizeGuide(db,"H&M","PANTALON",0,0,100,"14");
        createRecordBrandSizeGuide(db,"H&M","PANTALON",0,0,105,"16");
        createRecordBrandSizeGuide(db,"H&M","PANTALON",0,0,110,"18");
        createRecordBrandSizeGuide(db,"H&M","PANTALON",0,0,116,"20");
        createRecordBrandSizeGuide(db,"H&M","PANTALON",0,0,124,"22");
        createRecordBrandSizeGuide(db,"H&M","PANTALON",0,0,132,"24");
        createRecordBrandSizeGuide(db,"H&M","PANTALON",0,0,140,"26");
        createRecordBrandSizeGuide(db, "H&M", "PANTALON", 0, 0, 148, "28");

        createRecordBrandSizeGuide(db,"H&M","CHEMISE",0,0,0,"0");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",75,55,0,"2");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",78,60,0,"4");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",81,63,0,"6");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",84,66,0,"8");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",88,70,0,"10");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",93,74,0,"12");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",98,78,0,"14");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",104,82,0,"16");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",109,88,0,"18");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",116,95,0,"20");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",122,101,0,"22");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",128,108,0,"24");
        createRecordBrandSizeGuide(db,"H&M","CHEMISE",134,114,0,"26");
        createRecordBrandSizeGuide(db, "H&M", "CHEMISE", 140, 120, 0, "28");

        createRecordBrandSizeGuide(db,"H&M","CHEMISIER",0,0,0,"0");
        createRecordBrandSizeGuide(db,"H&M","CHEMISIER",70,50,0,"2");
        createRecordBrandSizeGuide(db,"H&M","CHEMISIER",73,55,0,"4");
        createRecordBrandSizeGuide(db,"H&M","CHEMISIER",76,58,0,"6");
        createRecordBrandSizeGuide(db,"H&M","CHEMISIER",80,61,0,"8");
        createRecordBrandSizeGuide(db,"H&M","CHEMISIER",84,65,0,"10");
        createRecordBrandSizeGuide(db,"H&M","CHEMISIER",89,70,0,"12");
        createRecordBrandSizeGuide(db,"H&M","CHEMISIER",94,74,0,"14");
        createRecordBrandSizeGuide(db,"H&M","CHEMISIER",99,79,0,"16");
        createRecordBrandSizeGuide(db,"H&M","CHEMISIER",105,85,0,"18");
        createRecordBrandSizeGuide(db,"H&M","CHEMISIER",111,91,0,"20");
        createRecordBrandSizeGuide(db,"H&M","CHEMISIER",118,98,0,"22");
        createRecordBrandSizeGuide(db,"H&M","CHEMISIER",126,106,0,"24");
        createRecordBrandSizeGuide(db,"H&M","CHEMISIER",134,114,0,"26");
        createRecordBrandSizeGuide(db, "H&M", "CHEMISIER", 142, 121, 0, "28");

        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-H",0,0,0,"0");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-H",0,0,14,"4");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-H",0,0,17,"5");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-H",0,0,20,"6");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-H",0,0,23,"7");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-H",0,0,26,"8");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-H",0,0,29,"9");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-H",0,0,31,"10");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-H",0,0,34,"11");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-H",0,0,37,"12");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-H",0,0,40,"13");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-H",0,0,43,"14");

        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-F",0,0,0,"0");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-F",0,0,14,"4");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-F",0,0,17,"5");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-F",0,0,20,"6");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-F",0,0,23,"7");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-F",0,0,26,"8");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-F",0,0,29,"9");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-F",0,0,32,"10");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-F",0,0,35,"11");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-F",0,0,38,"12");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-F",0,0,41,"13");
        createRecordBrandSizeGuide(db,"H&M","CHAUSSURE-F",0,0,44,"14");

        createRecordBrandSizeGuide(db,"ZARA","ROBE",0,0,0,"0");
        createRecordBrandSizeGuide(db,"ZARA","ROBE",71,48,75,"2");
        createRecordBrandSizeGuide(db,"ZARA","ROBE",73,50,78,"4");
        createRecordBrandSizeGuide(db,"ZARA","ROBE",76,53,80,"6");
        createRecordBrandSizeGuide(db,"ZARA","ROBE",79,56,83,"8");
        createRecordBrandSizeGuide(db,"ZARA","ROBE",83,61,87,"10");
        createRecordBrandSizeGuide(db,"ZARA","ROBE",87,66,92,"12");
        createRecordBrandSizeGuide(db,"ZARA","ROBE",95,71,97,"14");
        createRecordBrandSizeGuide(db,"ZARA","ROBE",100,76,102,"16");
        createRecordBrandSizeGuide(db,"ZARA","ROBE",105,85,107,"18");
        createRecordBrandSizeGuide(db,"ZARA","ROBE",110,91,112,"20");
        createRecordBrandSizeGuide(db,"ZARA","ROBE",115,98,118,"22");
        createRecordBrandSizeGuide(db,"ZARA","ROBE",122,105,125,"24");
        createRecordBrandSizeGuide(db,"ZARA","ROBE",128,110,131,"26");
        createRecordBrandSizeGuide(db,"ZARA", "ROBE", 134, 125, 137, "28");

        createRecordBrandSizeGuide(db,"ZARA","CHEMISIER",0,0,0,"0");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISIER",65,52,77,"2");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISIER",67,54,80,"4");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISIER",70,56,82,"6");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISIER",72,58,85,"8");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISIER",76,61,90,"10");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISIER",80,64,95,"12");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISIER",85,69,100,"14");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISIER",90,73,105,"16");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISIER",96,80,111,"18");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISIER",102,87,120,"20");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISIER",108,94,125,"22");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISIER",114,100,130,"24");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISIER",120,108,135,"26");
        createRecordBrandSizeGuide(db, "ZARA", "CHEMISIER", 127, 116, 141, "28");

        createRecordBrandSizeGuide(db,"ZARA","CHEMISE",0,0,0,"0");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISE",70,50,0,"2");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISE",73,55,0,"4");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISE",76,58,0,"6");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISE",79,61,0,"8");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISE",82,64,0,"10");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISE",86,68,0,"12");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISE",90,72,0,"14");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISE",95,76,0,"16");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISE",100,81,0,"18");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISE",106,88,0,"20");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISE",113,95,0,"22");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISE",119,102,0,"24");
        createRecordBrandSizeGuide(db,"ZARA","CHEMISE",125,109,0,"26");
        createRecordBrandSizeGuide(db, "ZARA", "CHEMISE", 131, 116, 0, "28");


        createRecordBrandSizeGuide(db,"ZARA","COSTUME",0,0,0,"0");
        createRecordBrandSizeGuide(db,"ZARA","COSTUME",70,52,78,"2");
        createRecordBrandSizeGuide(db,"ZARA","COSTUME",72,54,81,"4");
        createRecordBrandSizeGuide(db,"ZARA","COSTUME",76,57,83,"6");
        createRecordBrandSizeGuide(db,"ZARA","COSTUME",78,60,85,"8");
        createRecordBrandSizeGuide(db,"ZARA","COSTUME",82,64,90,"10");
        createRecordBrandSizeGuide(db,"ZARA","COSTUME",86,68,95,"12");
        createRecordBrandSizeGuide(db,"ZARA","COSTUME",90,73,100,"14");
        createRecordBrandSizeGuide(db,"ZARA","COSTUME",95,78,105,"16");
        createRecordBrandSizeGuide(db,"ZARA","COSTUME",101,83,110,"18");
        createRecordBrandSizeGuide(db,"ZARA","COSTUME",108,88,115,"20");
        createRecordBrandSizeGuide(db,"ZARA","COSTUME",115,94,120,"22");
        createRecordBrandSizeGuide(db,"ZARA","COSTUME",122,100,126,"24");
        createRecordBrandSizeGuide(db,"ZARA","COSTUME",130,106,132,"26");
        createRecordBrandSizeGuide(db, "ZARA", "COSTUME", 137, 112, 140, "28");

        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-H",0,0,0,"0");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-H",0,0,14,"4");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-H",0,0,17,"5");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-H",0,0,20,"6");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-H",0,0,23,"7");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-H",0,0,26,"8");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-H",0,0,29,"9");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-H",0,0,32,"10");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-H",0,0,34,"11");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-H",0,0,37,"12");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-H",0,0,40,"13");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-H",0,0,43,"14");

        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-F",0,0,0,"0");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-F",0,0,11,"4");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-F",0,0,13,"5");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-F",0,0,16,"6");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-F",0,0,19,"7");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-F",0,0,22,"8");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-F",0,0,25,"9");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-F",0,0,28,"10");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-F",0,0,31,"11");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-F",0,0,33,"12");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-F",0,0,36,"13");
        createRecordBrandSizeGuide(db,"ZARA","CHAUSSURE-F",0,0,39,"14");

        createRecordBrandSizeGuide(db,"BOSS","COSTUME",0,0,0,"0");
        createRecordBrandSizeGuide(db,"BOSS","COSTUME",74,56,81,"2");
        createRecordBrandSizeGuide(db,"BOSS","COSTUME",76,58,84,"4");
        createRecordBrandSizeGuide(db,"BOSS","COSTUME",79,61,86,"6");
        createRecordBrandSizeGuide(db,"BOSS","COSTUME",81,63,89,"8");
        createRecordBrandSizeGuide(db,"BOSS","COSTUME",86,68,94,"10");
        createRecordBrandSizeGuide(db,"BOSS","COSTUME",91,73,99,"12");
        createRecordBrandSizeGuide(db,"BOSS","COSTUME",96,78,104,"14");
        createRecordBrandSizeGuide(db,"BOSS","COSTUME",101,83,109,"16");
        createRecordBrandSizeGuide(db,"BOSS","COSTUME",109,91,116,"18");
        createRecordBrandSizeGuide(db,"BOSS","COSTUME",116,98,124,"20");
        createRecordBrandSizeGuide(db,"BOSS","COSTUME",122,104,130,"22");
        createRecordBrandSizeGuide(db,"BOSS","COSTUME",128,110,136,"24");
        createRecordBrandSizeGuide(db,"BOSS","COSTUME",134,116,142,"26");
        createRecordBrandSizeGuide(db, "BOSS", "COSTUME", 140, 122, 148, "28");

        createRecordBrandSizeGuide(db,"BOSS","CHEMISE",0,0,0,"0");
        createRecordBrandSizeGuide(db,"BOSS","CHEMISE",74,56,81,"2");
        createRecordBrandSizeGuide(db,"BOSS","CHEMISE",76,58,84,"4");
        createRecordBrandSizeGuide(db,"BOSS","CHEMISE",79,61,86,"6");
        createRecordBrandSizeGuide(db,"BOSS","CHEMISE",81,63,89,"8");
        createRecordBrandSizeGuide(db,"BOSS","CHEMISE",86,68,94,"10");
        createRecordBrandSizeGuide(db,"BOSS","CHEMISE",91,73,99,"12");
        createRecordBrandSizeGuide(db,"BOSS","CHEMISE",96,78,104,"14");
        createRecordBrandSizeGuide(db,"BOSS","CHEMISE",101,83,109,"16");
        createRecordBrandSizeGuide(db,"BOSS","CHEMISE",109,91,116,"18");
        createRecordBrandSizeGuide(db,"BOSS","CHEMISE",116,98,124,"20");
        createRecordBrandSizeGuide(db,"BOSS","CHEMISE",122,104,130,"22");
        createRecordBrandSizeGuide(db,"BOSS","CHEMISE",128,110,136,"24");
        createRecordBrandSizeGuide(db,"BOSS","CHEMISE",134,116,142,"26");
        createRecordBrandSizeGuide(db, "BOSS", "CHEMISE", 140, 122, 148, "28");

        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-H",0,0,0,"0");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-H",0,0,13,"4");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-H",0,0,16,"5");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-H",0,0,19,"6");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-H",0,0,21,"7");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-H",0,0,24,"8");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-H",0,0,27,"9");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-H",0,0,30,"10");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-H",0,0,32,"11");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-H",0,0,35,"12");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-H",0,0,38,"13");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-H",0,0,40,"14");

        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-F",0,0,0,"0");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-F",0,0,13,"4");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-F",0,0,16,"5");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-F",0,0,19,"6");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-F",0,0,22,"7");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-F",0,0,24,"8");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-F",0,0,27,"9");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-F",0,0,30,"10");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-F",0,0,32,"11");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-F",0,0,35,"12");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-F",0,0,38,"13");
        createRecordBrandSizeGuide(db,"NIKE","CHAUSSURE-F",0,0,40,"14");
*/
    }

    private void createRecordClotheType(SQLiteDatabase db, String type, String sexe){
        ContentValues values = new ContentValues(2);
        values.put("type", type);
        values.put("sexe", sexe);
        db.insert("clothe_type","id",values);
    }

    private void createRecordBrands(SQLiteDatabase db, String brand){
        ContentValues values = new ContentValues(1);
        try {
            values.put("brand", brand);
            db.insert("brands", "id", values);
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"Error init table 'brands' : " + e.getMessage());
        }
    }

    private void createRecordSizeConvert(SQLiteDatabase db, String type, String valueUS, String valueUK, String valueEU,
                                         String valueFR, String valueITA, String valueJAP, String valueAUS) {
        // id, Type,   US, UK,  EU,  FR, ITA, JAP, AUS
        ContentValues values = new ContentValues(8);
        values.put("type", type);
        values.put("valueUS", valueUS);
        values.put("valueUK", valueUK);
        values.put("valueUE", valueEU);
        values.put("valueFR", valueFR);
        values.put("valueITA", valueITA);
        values.put("valueJAP", valueJAP);
        values.put("valueAUS", valueAUS);
        db.insert("size_convert", "id", values);

    }

    private void createRecordBrandSizeGuide(SQLiteDatabase db, String brand, String type,
                                            double dim1, double dim2, double dim3, String size){
        ContentValues values = new ContentValues(6);
        values.put("brand", brand);
        values.put("type", type);
        values.put("dim1", dim1);
        values.put("dim2", dim2);
        values.put("dim3", dim3);
        values.put("size", size);
        db.insert("brand_size_guide", "id", values);

    }

    // C.R.U.D User
        public User createUser(String firstName, String lastName, String birthday,
                               String sexe, String avatar, String description){
            SQLiteDatabase db = this.getWritableDatabase();
            User user;
            String nickname = firstName+lastName+birthday;
            try {
                ContentValues values = new ContentValues(19);
                values.put("nickname", nickname);
                values.put("firstname", firstName);
                values.put("lastname", lastName);
                values.put("birthday", birthday);
                values.put("sexe", sexe);
                values.put("avatar", avatar);
                values.put("description", description);
                values.put("size", 0);
                values.put("weight", 0);
                values.put("chest", 0);
                values.put("collar", 0);
                values.put("bust", 0);
                values.put("waist", 0);
                values.put("hips", 0);
                values.put("sleeve", 0);
                values.put("inseam", 0);
                values.put("feet", 0);
                values.put("unitL", 0);
                values.put("unitW", 0);
                values.put("pointure", 0);
                db.insert("user", "userid", values);
            }
            catch (SQLiteException e) {
                Log.d(Constants.TAG,"Error creating user : " + e.getMessage() );
            }
            db.close();
            user = getUserByNickname(nickname);
            return user;
    }

    /*
    public User importUser(String nickname, String firstName, String lastName, String birthday,
                           String sexe, String avatar,  double size, double weight, double imc,
                           double chest, double collar, double bust, double waist, double hips,
                           double sleeve, double inseam, double feet, int unitLength, int unitWeight, double pointure){
        SQLiteDatabase db = this.getWritableDatabase();
        User user;
        try {
            ContentValues values = new ContentValues(18);
            values.put("nickname", nickname);
            values.put("firstname", firstName);
            values.put("lastname", lastName);
            values.put("birthday", birthday);
            values.put("sexe", sexe);
            values.put("avatar", avatar);
            values.put("size", size);
            values.put("weight", weight);
            values.put("imc", imc);
            values.put("chest", chest);
            values.put("collar", collar);
            values.put("bust", bust);
            values.put("waist", waist);
            values.put("hips", hips);
            values.put("sleeve", sleeve);
            values.put("inseam", inseam);
            values.put("feet", feet);
            values.put("unitL", unitLength);
            values.put("unitW", unitWeight);
            values.put("pointure", pointure);
            db.insert("user", "userid", values);
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"Error creating user : " + e.getMessage() );
        }
        db.close();
        user = getUserByNickname(nickname);
        return user;
    }
    */

    public User importUser(String nickname, String firstName, String lastName, String birthday, String sexe,
                String avatar, String description, String size, String weight, String bust,
                String chest, String collar, String waist, String hips, String sleeve, String inseam,
                String feet, String unitL, String unitW, String pointure){


        SQLiteDatabase db = this.getWritableDatabase();
        User user;
        try {
            ContentValues values = new ContentValues(25);
            values.put("nickname", nickname);
            values.put("firstname", firstName);
            values.put("lastname", lastName);
            values.put("birthday", birthday);
            values.put("sexe", sexe);
            values.put("avatar", avatar);
            values.put("description", description);
            values.put("size", size);
            values.put("weight", weight);
            //values.put("imc", imc);
            values.put("chest", chest);
            values.put("collar", collar);
            values.put("bust", bust);
            values.put("waist", waist);
            values.put("hips", hips);
            values.put("sleeve", sleeve);
            values.put("inseam", inseam);
            values.put("feet", feet);
            values.put("unitL", unitL);
            values.put("unitW", unitW);
            values.put("pointure", pointure);
            db.insert("user", "userid", values);
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"Error creating user : " + e.getMessage() );
        }
        db.close();
        user = getUserByNickname(nickname);
        return user;


        /*
        this.userid = convertToInt(userid);
        this.nickname = nickname;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.sexe = sexe;
        this.avatar = avatar;
        this.size = convertToDouble(size);
        this.weight = convertToDouble(weight);
        this.bust = convertToDouble(bust);
        this.chest = convertToDouble(chest);
        this.collar = convertToDouble(collar);
        this.waist = convertToDouble(waist);
        this.hips = convertToDouble(hips);
        this.sleeve = convertToDouble(sleeve);
        this.inseam = convertToDouble(inseam);
        this.feet = convertToDouble(feet);
        this.unitLength = convertToInt(unitL);
        this.unitWeight = convertToInt(unitW);
        this.pointure = convertToDouble(pointure);
        */
    }

    public ArrayList<String> getUserSizes(User user){

        ArrayList<String> mesures = new ArrayList<>();
        int userId = user.getUserid();
        String size = String.valueOf(user.getSize());
        String weight = String.valueOf(user.getWeight());
        String bust = String.valueOf(user.getBust());
        String chest = String.valueOf(user.getChest());
        String collar = String.valueOf(user.getCollar());
        String waist = String.valueOf(user.getWaist());
        String hips = String.valueOf(user.getHips());
        String sleeve = String.valueOf(user.getSleeve());
        String inseam = String.valueOf(user.getInseam());
        String feet = String.valueOf(user.getFeet());
        String unitLength = String.valueOf(user.getUnitLength());
        String unitWeight = String.valueOf(user.getUnitWeight());
        String pointure = String.valueOf(user.getPointure());

        //TODO test si != null :

        mesures.add(size);
        mesures.add(weight);
        mesures.add(bust);
        mesures.add(chest);
        mesures.add(collar);
        mesures.add(waist);
        mesures.add(hips);
        mesures.add(sleeve);
        mesures.add(inseam);
        mesures.add(feet);
        mesures.add(unitLength);
        mesures.add(unitWeight);
        mesures.add(pointure);

        return mesures;
    }

    private int convertToInt(String arg){
        if (arg.equals("")){
            return 0;
        } else {
            return Integer.parseInt(arg);
        }
    }

    private double convertToDouble(String arg){
        if (arg.equals("")){
            return 0.0;
        } else {
            return Double.parseDouble(arg);
        }
    }

    public User getUserByNickname(String nickname){
        User user = new User();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM user WHERE nickname = ?",new String[] {nickname} );
            boolean eof = c.moveToFirst();
            user.setUserid(c.getInt(0));
            user.setNickname(c.getString(1));
            user.setFirstname(c.getString(2));
            user.setLastname(c.getString(3));
            user.setBirthday(c.getString(4));
            user.setSexe(c.getString(5));
            user.setAvatar(c.getString(6));
            user.setDescription(c.getString(7));
            user.setSize(c.getDouble(8));
            user.setWeight(c.getDouble(9));
            user.setChest(c.getDouble(10));
            user.setCollar(c.getDouble(11));
            user.setBust(c.getDouble(12));
            user.setWaist(c.getDouble(13));
            user.setHips(c.getDouble(14));
            user.setSleeve(c.getDouble(15));
            user.setInseam(c.getDouble(16));
            user.setFeet(c.getDouble(17));
            user.setUnitLength(c.getInt(18));
            user.setUnitWeight(c.getInt(19));
            user.setPointure(c.getDouble(20));
            c.close();
            }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting user : " + e.getMessage());
            user = null;
            }
        db.close();
        return user;
        }

    public User getUserById(int userid){
        SQLiteDatabase db = this.getReadableDatabase();
        User user = new User();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM user WHERE userid = ?",new String[] {String.valueOf(userid)} );
            boolean eof = c.moveToFirst();
            user.setUserid(c.getInt(0));
            user.setNickname(c.getString(1));
            user.setFirstname(c.getString(2));
            user.setLastname(c.getString(3));
            user.setBirthday(c.getString(4));
            user.setSexe(c.getString(5));
            user.setAvatar(c.getString(6));
            user.setDescription(c.getString(7));
            user.setSize(c.getDouble(8));
            user.setWeight(c.getDouble(9));
            user.setChest(c.getDouble(10));
            user.setCollar(c.getDouble(11));
            user.setBust(c.getDouble(12));
            user.setWaist(c.getDouble(13));
            user.setHips(c.getDouble(14));
            user.setSleeve(c.getDouble(15));
            user.setInseam(c.getDouble(16));
            user.setFeet(c.getDouble(17));
            user.setUnitLength(c.getInt(18));
            user.setUnitWeight(c.getInt(19));
            user.setPointure(c.getInt(20));
            c.close();
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting user " + userid + " : " + e.getMessage());
            user = null;
        }
        db.close();
        return user;
    }

    public boolean updateUser(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            ContentValues values = new ContentValues(17);
            values.put("nickname", user.getNickname());
            values.put("firstname", user.getFirstname());
            values.put("lastname", user.getLastname());
            values.put("birthday", user.getBirthday());
            values.put("sexe", user.getSexe());
            values.put("avatar", user.getAvatar());
            values.put("description", user.getDescription());
            values.put("size", user.getSize());
            values.put("weight", user.getWeight());
            values.put("chest", user.getChest());
            values.put("collar", user.getCollar());
            values.put("bust", user.getBust());
            values.put("waist", user.getWaist());
            values.put("hips", user.getHips());
            values.put("sleeve", user.getSleeve());
            values.put("inseam", user.getInseam());
            values.put("feet", user.getFeet());
            values.put("unitL", user.getUnitLength());
            values.put("unitW", user.getUnitWeight());
            values.put("pointure", user.getPointure());
            db.update("user", values, "userid = ?", new String[]{String.valueOf(user.getUserid())});
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG, "Update user with error : " + e.getMessage());
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public ArrayList<User> getAllUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<User> users = new ArrayList<User>();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM user ",null );
            boolean eof = c.moveToFirst();
            while (eof) {
                User user = new User();
                user.setUserid(c.getInt(0));
                user.setNickname(c.getString(1));
                user.setFirstname(c.getString(2));
                user.setLastname(c.getString(3));
                user.setBirthday(c.getString(4));
                user.setSexe(c.getString(5));
                user.setAvatar(c.getString(6));
                user.setDescription(c.getString(7));
                user.setSize(c.getDouble(8));
                user.setWeight(c.getDouble(9));
                user.setChest(c.getDouble(10));
                user.setCollar(c.getDouble(11));
                user.setBust(c.getDouble(12));
                user.setWaist(c.getDouble(13));
                user.setHips(c.getDouble(14));
                user.setSleeve(c.getDouble(15));
                user.setInseam(c.getDouble(16));
                user.setFeet(c.getDouble(17));
                user.setUnitLength(c.getInt(18));
                user.setUnitWeight(c.getInt(19));
                user.setPointure(c.getDouble(20));
                users.add(user);
                eof = c.moveToNext();
            }
            c.close();
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting user : " + e.getMessage());
            users = null;
        }
        db.close();
        return users;
    }

    // Garments

    public ArrayList<GarmentType> getAllGarments(String sexe){
        ArrayList<GarmentType> garments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c;

            c = db.rawQuery("SELECT * FROM clothe_type where sexe = ? ", new String[]{sexe});

            boolean eof = c.moveToFirst();
            while (eof) {
                GarmentType g = new GarmentType();
                g.setId(c.getInt(0));
                g.setType(c.getString(1));
                garments.add(g);
                eof = c.moveToNext();
            }
            c.close();
            db.close();
            return garments;
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting garments : " + e.getMessage());
            garments = null;
        }
        db.close();
        return null;
    }


    public ArrayList<BrandsSizeGuide> getAllGarmentsByBrand(String brand){
        ArrayList<BrandsSizeGuide> garments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c;
            c = db.rawQuery("SELECT * FROM brand_size_guide where brand = ? AND size = ?", new String[]{brand,"0"});
            boolean eof = c.moveToFirst();
            while (eof) {
                BrandsSizeGuide b = new BrandsSizeGuide();
                b.setId(c.getInt(0));
                b.setBrand(c.getString(1));
                b.setType(c.getString(2));
                b.setDim1(c.getInt(3));
                b.setDim2(c.getInt(4));
                b.setDim3(c.getInt(5));
                b.setSize(c.getString(6));
                garments.add(b);
                eof = c.moveToNext();
            }
            c.close();
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting garments from brand : " + e.getMessage());
            garments = null;
        }
        db.close();
        return garments;
    }

    public ArrayList<BrandsSizeGuide> getAllBrandsByGarment(String garment){
        ArrayList<BrandsSizeGuide> brands = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c;
            c = db.rawQuery("SELECT * FROM brand_size_guide where type = ? AND size = ?", new String[]{garment,"0"});
            boolean eof = c.moveToFirst();
            while (eof) {
                BrandsSizeGuide b = new BrandsSizeGuide();
                b.setId(c.getInt(0));
                b.setBrand(c.getString(1));
                b.setType(c.getString(2));
                b.setDim1(c.getInt(3));
                b.setDim2(c.getInt(4));
                b.setDim3(c.getInt(5));
                b.setSize(c.getString(6));
                brands.add(b);
                eof = c.moveToNext();
            }
            c.close();
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting garments from brand : " + e.getMessage());
            brands = null;
        }
        db.close();
        return brands;
    }


    public ArrayList<BrandsSizeGuide> getBrandsSizes(String garment, String brand){
        ArrayList<BrandsSizeGuide> sizes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c;
            c = db.rawQuery("SELECT * FROM brand_size_guide where brand = ? AND type = ?", new String[]{brand,garment});
            boolean eof = c.moveToFirst();
            while (eof) {
                BrandsSizeGuide b = new BrandsSizeGuide();
                b.setId(c.getInt(0));
                b.setBrand(c.getString(1));
                b.setType(c.getString(2));
                b.setDim1(c.getInt(3));
                b.setDim2(c.getInt(4));
                b.setDim3(c.getInt(5));
                b.setSize(c.getString(6));
                if (!b.getSize().equals("0")) {
                    sizes.add(b);
                }
                eof = c.moveToNext();
            }
            c.close();
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting sizes from brand & garment: " + e.getMessage());
            sizes = null;
        }
        db.close();
        return sizes;
    }

    // Brands

    public ArrayList<Brands> getAllBrands(){
        ArrayList<Brands> brands = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c;
            c = db.rawQuery("SELECT * FROM brands", null);
            boolean eof = c.moveToFirst();
            while (eof) {
                Brands b = new Brands();
                b.setId(c.getInt(0));
                b.setBrand(c.getString(1));
                brands.add(b);
                eof = c.moveToNext();
            }
            c.close();
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting brands : " + e.getMessage());
            brands = null;
        }
        db.close();
        return brands;
    }

    // UserGarments

    public ArrayList<UserClothes> getAllUserGarments(User user){
        ArrayList<UserClothes> garments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Gson gson = new Gson();
        try {
            Cursor c;
            c = db.rawQuery("SELECT * FROM user_clothes WHERE userid = ?", new String[]{String.valueOf(user.getUserid())});
            boolean eof = c.moveToFirst();
            while (eof) {
                UserClothes uc = new UserClothes();
                uc.setClotheid(c.getInt(0));
                uc.setUserid(c.getInt(1));
                uc.setType(c.getString(2));
                uc.setBrand(c.getString(3));
                uc.setCountry(c.getString(4));
                uc.setSize(c.getString(5));
                uc.setComment(c.getString(6));
                ArrayList<TabSizes> tabs = gson.fromJson(c.getString(7), new TypeToken<ArrayList<TabSizes>>() {
                }.getType());
                uc.setSizes(tabs);
                uc.setCategory(gson.fromJson(c.getString(8), CategoryGarment.class));
                garments.add(uc);
                eof = c.moveToNext();
            }
            c.close();
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting user garments : " + e.getMessage());
            garments = null;
        }
        db.close();
        return garments;
    }

    // getSizeConvert
    public ArrayList<SizeConvert> getConvertSizesByGarment(String garment){
        ArrayList<SizeConvert> sizes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c;
            c = db.rawQuery("SELECT * FROM size_convert WHERE type = ?", new String[]{garment});
            boolean eof = c.moveToFirst();
            while (eof) {
                SizeConvert s = new SizeConvert();
                s.setId(c.getInt(0));
                s.setType(c.getString(1));
                s.setValueUS(c.getString(2));
                s.setValueUK(c.getString(3));
                s.setValueUE(c.getString(4));
                s.setValueFR(c.getString(5));
                s.setValueITA(c.getString(6));
                s.setValueJAP(c.getString(7));
                s.setValueAUS(c.getString(8));
                sizes.add(s);
                eof = c.moveToNext();
            }
            c.close();
        }
        catch (SQLiteException e){
            Log.d(Constants.TAG,"ERROR Getting Size_Convert : " + e.getMessage());
            sizes = null;
        }
        db.close();
        return sizes;
    }


    public ArrayList<GarmentType> getGarmentsSizeGuide(){
        ArrayList<GarmentType> garments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c;
            c = db.rawQuery("SELECT id, type FROM size_convert GROUP BY type", new String[]{});
            boolean eof = c.moveToFirst();
            while (eof) {
                GarmentType gt = new GarmentType();
                gt.setId(c.getInt(0));
                gt.setType(c.getString(1));

                garments.add(gt);
                eof = c.moveToNext();
            }
            c.close();
        }
        catch (SQLiteException e){
            Log.d(Constants.TAG,"ERROR Getting Garments for size guide : " + e.getMessage());
            garments = null;
        }
        db.close();
        return garments;
    }

    public boolean AddUserGarments(UserClothes uc){
        Gson gson = new Gson();
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues(8);
        values.put("userid", uc.getUserid() );
        values.put("type", uc.getType());
        values.put("brand", uc.getBrand());
        values.put("country", uc.getCountry());
        values.put("size", uc.getSize());
        values.put("comment", uc.getComment());
        values.put("sizes", gson.toJson(uc.getSizes()));
        values.put("category", gson.toJson(uc.getCategory()));
        /*
        if(uc.getComment() != "") {
            values.put("comment", uc.getComment());
        } else {
            values.put("comment", " ");
        }
        */
        try {
            db.insert("user_clothes", "id", values);
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG, "Error adding user garment : " + e.getMessage());
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public boolean updateUserGarment(UserClothes clothe){
        Gson gson = new Gson();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            ContentValues values = new ContentValues(8);
            values.put("userid", clothe.getUserid() );
            values.put("type", clothe.getType());
            values.put("brand", clothe.getBrand());
            values.put("country", clothe.getCountry());
            values.put("size", clothe.getSize());
            values.put("comment", clothe.getComment());
            values.put("sizes", gson.toJson(clothe.getSizes()));
            values.put("category", gson.toJson(clothe.getCategory()));
            db.update("user_clothes", values, "id = ?", new String[]{String.valueOf(clothe.getClotheid())});
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG, "Update clothe with error : " + e.getMessage());
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public boolean removeUserGarment(UserClothes clothe){
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.delete("user_clothes", "id = ?", new String[]{String.valueOf(clothe.getClotheid())});
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG, "delete clothe with error : " + e.getMessage());
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public ArrayList<Integer> getIndexMeasureNotNull(User user){

        ArrayList<Integer> nbElement = new ArrayList<>();
        ArrayList<String> sizes;
        sizes = getUserSizes(user);
        for (int i = 0 ; i < sizes.size() ; i++){
            if(!(sizes.get(i).equals("0.0")) && !(sizes.get(i).equals("0")) && !(sizes.get(i).equals("")) ){
                    nbElement.add(i);
            }
        }

        return nbElement;
    }

}
