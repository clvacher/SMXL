package com.aerolitec.SMXL.ui;

import android.app.Application;

import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.dbmanager.BrandDBManager;
import com.aerolitec.SMXL.tools.dbmanager.BrandSizeGuideDBManager;
import com.aerolitec.SMXL.tools.dbmanager.CategoryGarmentDBManager;
import com.aerolitec.SMXL.tools.dbmanager.GarmentTypeDBManager;
import com.aerolitec.SMXL.tools.dbmanager.SizeConvertDBManager;
import com.aerolitec.SMXL.tools.dbmanager.UserBrandDBManager;
import com.aerolitec.SMXL.tools.dbmanager.UserClothesDBManager;
import com.aerolitec.SMXL.tools.dbmanager.UserDBManager;
import com.aerolitec.SMXL.tools.services.SQLiteSMXL;
import com.squareup.picasso.Picasso;

/**
 * Created by stephaneL on 20/03/14.
 */
public class SMXL extends Application{

    private static SMXL instance;
    private static SQLiteSMXL dataBase;
    private static BrandDBManager brandDBManager;
    private static BrandSizeGuideDBManager brandSizeGuideDBManager;
    private static CategoryGarmentDBManager categoryGarmentDBManager;
    private static GarmentTypeDBManager garmentTypeDBManager;
    private static SizeConvertDBManager sizeConvertDBManager;
    private static UserBrandDBManager userBrandDBManager;
    private static UserClothesDBManager userClothesDBManager;
    private static UserDBManager userDBManager;
    private Picasso picasso;
    //private static DisplayMetrics metrics;
    private User user;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dataBase= new SQLiteSMXL(getApplicationContext());
        brandDBManager = new BrandDBManager(getApplicationContext());
        brandSizeGuideDBManager = new BrandSizeGuideDBManager(getApplicationContext());
        categoryGarmentDBManager = new CategoryGarmentDBManager(getApplicationContext());
        garmentTypeDBManager = new GarmentTypeDBManager(getApplicationContext());
        sizeConvertDBManager = new SizeConvertDBManager(getApplicationContext());
        userBrandDBManager = new UserBrandDBManager(getApplicationContext());
        userClothesDBManager = new UserClothesDBManager(getApplicationContext());
        userDBManager = new UserDBManager(getApplicationContext());
        //metrics = getResources().getDisplayMetrics();

        picasso = new Picasso.Builder(getApplicationContext())
                .debugging(false)
                .build();
    }

    public Picasso getPicasso() {
        return picasso;
    }

    public static SMXL get() {
        return instance;
    }

    public static SQLiteSMXL getDataBase() {
        return dataBase;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    // Getters Manager

    public static BrandDBManager getBrandDBManager() {
        return brandDBManager;
    }

    public static BrandSizeGuideDBManager getBrandSizeGuideDBManager() {
        return brandSizeGuideDBManager;
    }

    public static CategoryGarmentDBManager getCategoryGarmentDBManager() {
        return categoryGarmentDBManager;
    }

    public static GarmentTypeDBManager getGarmentTypeDBManager() {
        return garmentTypeDBManager;
    }

    public static SizeConvertDBManager getSizeConvertDBManager() {
        return sizeConvertDBManager;
    }

    public static UserBrandDBManager getUserBrandDBManager() {
        return userBrandDBManager;
    }

    public static UserClothesDBManager getUserClothesDBManager() {
        return userClothesDBManager;
    }

    public static UserDBManager getUserDBManager() {
        return userDBManager;
    }
}
