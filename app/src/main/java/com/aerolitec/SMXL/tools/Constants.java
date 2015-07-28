package com.aerolitec.SMXL.tools;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;

import com.aerolitec.SMXL.R;

/**
 * Created by stephaneL on 20/03/14.
 */
public class Constants {
    public static final String TAG = "SMXL";

    public static final String MAIN_USER_FILE = "mainUser";

    public static final String SHARED_PREF = "appSharedPreferences";
    public static final String FIRST_LAUNCH = "firstLaunch";

    public static final Float inch = 2.54F;
    public static final Float pounds = 2.2046F;
    public static final Float cm = 0.3937F;

    public static int INCH = 1;
    public static int CM = 0;
    public static int POUNDS = 2;
    public static int KG = 3;

    public static String MALE = "H";
    public static String FEMALE = "F";

    public static final int TSHIRT_CATEGORYGARMENT = 1;
    public  static final int PANTS_SHORTS_CATEGORYGARMENT = 3;

    public static class ColorUtils{

        /**
         * Create an array of int with colors
         *
         * @param context
         * @return
         */
        public static int[] colorChoice(Context context){

            int[] mColorChoices=null;
            String[] color_array = context.getResources().getStringArray(R.array.default_color_choice_values);

            if (color_array!=null && color_array.length>0) {
                mColorChoices = new int[color_array.length];
                for (int i = 0; i < color_array.length; i++) {
                    mColorChoices[i] = Color.parseColor(color_array[i]);
                }
            }
            return mColorChoices;
        }

        /**
         * Parse whiteColor
         *
         * @return
         */
        public static int parseWhiteColor(){
            return Color.parseColor("#FFFFFF");
        }

    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }



}
