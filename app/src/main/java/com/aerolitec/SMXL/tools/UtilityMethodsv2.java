package com.aerolitec.SMXL.tools;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jerome on 28/05/2015.
 */
public class UtilityMethodsv2 {

    //Allows the ListView to adapt to its content test tet
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    //Converts an input stream into a String
    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null){
            result += line;
        }

        inputStream.close();
        return result;
    }

    //reverses the birthday from dd-mm-yyyy to yyy-mm-dd
    public static String reverseBirthdayOrder(String birthday){
        return birthday.substring(6,10)+"-"+birthday.substring(3,5)+"-"+birthday.substring(0,2);
    }

    public static boolean isConnected(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static double convertFeetSizeToCm(double size){
        Locale defaultLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);
        NumberFormat format = new DecimalFormat("#0.0");
        double feet = Double.parseDouble(format.format(((2f / 3f) * size) - 1f));
        Locale.setDefault(defaultLocale);
        return feet;
    }


    public static boolean hasTopBottomFavoriteBrand(){
        User user = UserManager.get().getUser();
        int sex = user.getSexe();

        GarmentType garmentTypeTop=null,garmentTypeBottom=null;
        garmentTypeTop = SMXL.getGarmentTypeDBManager().getAllGarmentTypeByCategory(SMXL.getCategoryGarmentDBManager().getCategoryGarment(Constants.TSHIRT_CATEGORYGARMENT), sex).get(0);
        garmentTypeBottom = SMXL.getGarmentTypeDBManager().getAllGarmentTypeByCategory(SMXL.getCategoryGarmentDBManager().getCategoryGarment(Constants.PANTS_SHORTS_CATEGORYGARMENT), sex).get(1);

        ArrayList<Brand> brandsTop = new ArrayList<>(user.getBrands());
        ArrayList<Brand> brandsBottom = new ArrayList<>(user.getBrands());
        List<Brand> allBrands = SMXL.getBrandSizeGuideDBManager().getAllBrandsByGarment(garmentTypeTop);

        for (Iterator<Brand> brandIterator = brandsTop.iterator(); brandIterator.hasNext(); ) {
            Brand brand = brandIterator.next();
            if (!allBrands.contains(brand)) {
                brandIterator.remove();
            }
        }
        allBrands = SMXL.getBrandSizeGuideDBManager().getAllBrandsByGarment(garmentTypeBottom);
        for (Iterator<Brand> brandIterator = brandsBottom.iterator(); brandIterator.hasNext(); ) {
            Brand brand = brandIterator.next();
            if (!allBrands.contains(brand)) {
                brandIterator.remove();
            }
        }
        if(brandsBottom.isEmpty() || brandsTop.isEmpty()){
            return false;
        }
        return true;
    }
}
