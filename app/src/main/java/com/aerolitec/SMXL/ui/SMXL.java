package com.aerolitec.SMXL.ui;

import android.app.Application;

import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.services.SizeGuideDataBase;
import com.squareup.picasso.Picasso;

/**
 * Created by stephaneL on 20/03/14.
 */
public class SMXL extends Application{

    private static SMXL instance;
    private static SizeGuideDataBase dataBase;
    private Picasso picasso;
    //private static DisplayMetrics metrics;
    private User user;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
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

    public static SizeGuideDataBase getDataBase() {
        return dataBase;
    }

    public static void setDataBase(SizeGuideDataBase dataBase) {
        SMXL.dataBase = dataBase;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
