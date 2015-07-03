package com.aerolitec.SMXL.ui;

import android.app.Application;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.tools.dbmanager.BlogDBManager;
import com.aerolitec.SMXL.tools.dbmanager.BrandDBManager;
import com.aerolitec.SMXL.tools.dbmanager.BrandSizeGuideDBManager;
import com.aerolitec.SMXL.tools.dbmanager.CategoryGarmentDBManager;
import com.aerolitec.SMXL.tools.dbmanager.GarmentTypeDBManager;
import com.aerolitec.SMXL.tools.dbmanager.ShopOnLineDBManager;
import com.aerolitec.SMXL.tools.dbmanager.SizeConvertDBManager;
import com.aerolitec.SMXL.tools.dbmanager.UserBrandDBManager;
import com.aerolitec.SMXL.tools.dbmanager.UserClothesDBManager;
import com.aerolitec.SMXL.tools.dbmanager.UserDBManager;
import com.squareup.picasso.Picasso;

/**
 * Created by stephaneL on 20/03/14.
 */
public class SMXL extends Application{

    private static SMXL instance;
    private static BrandDBManager brandDBManager;
    private static BrandSizeGuideDBManager brandSizeGuideDBManager;
    private static CategoryGarmentDBManager categoryGarmentDBManager;
    private static GarmentTypeDBManager garmentTypeDBManager;
    private static SizeConvertDBManager sizeConvertDBManager;
    private static UserBrandDBManager userBrandDBManager;
    private static UserClothesDBManager userClothesDBManager;
    private static UserDBManager userDBManager;
    private static BlogDBManager blogDBManager;
    private static ShopOnLineDBManager shopOnLineDBManager;

    private Picasso picasso;
    //private static DisplayMetrics metrics;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        brandDBManager = new BrandDBManager(getApplicationContext());
        brandSizeGuideDBManager = new BrandSizeGuideDBManager(getApplicationContext());
        categoryGarmentDBManager = new CategoryGarmentDBManager(getApplicationContext());
        garmentTypeDBManager = new GarmentTypeDBManager(getApplicationContext());
        sizeConvertDBManager = new SizeConvertDBManager(getApplicationContext());
        userBrandDBManager = new UserBrandDBManager(getApplicationContext());
        userClothesDBManager = new UserClothesDBManager(getApplicationContext());
        userDBManager = new UserDBManager(getApplicationContext());
        blogDBManager = new BlogDBManager(getApplicationContext());
        shopOnLineDBManager = new ShopOnLineDBManager(getApplicationContext());
        //metrics = getResources().getDisplayMetrics();

        categoryGarmentDBManager.updateCategoryGarment(1, R.drawable.tshirt_lowpx);
        categoryGarmentDBManager.updateCategoryGarment(2, R.drawable.robe_lowpx);
        categoryGarmentDBManager.updateCategoryGarment(3, R.drawable.pantalon_lowpx);
        categoryGarmentDBManager.updateCategoryGarment(4, R.drawable.chemise_lowpx);
        categoryGarmentDBManager.updateCategoryGarment(5, R.drawable.blouson_lowpx);
        categoryGarmentDBManager.updateCategoryGarment(6, R.drawable.chaussure_lowpx);
        categoryGarmentDBManager.updateCategoryGarment(7, R.drawable.pull_lowpx);
        categoryGarmentDBManager.updateCategoryGarment(8, R.drawable.veste_lowpx);
        categoryGarmentDBManager.updateCategoryGarment(9, R.drawable.costume_lowpx);
        categoryGarmentDBManager.updateCategoryGarment(10, R.drawable.sousvet_lowpx);

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

    public static BlogDBManager getBlogDBManager() {
        return blogDBManager;
    }

    public static ShopOnLineDBManager getShopOnLineDBManager() {
        return shopOnLineDBManager;
    }

}
