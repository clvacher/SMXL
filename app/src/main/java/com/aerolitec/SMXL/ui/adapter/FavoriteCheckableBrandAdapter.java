package com.aerolitec.SMXL.ui.adapter;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.customLayout.CheckableBrandLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Jerome on 17/04/2015.
 */
public class FavoriteCheckableBrandAdapter extends BaseAdapter {

    private ArrayList<Brand> brands;

    public static ArrayList<CheckableBrandLayout> checkableBrands = new ArrayList<>();



    public FavoriteCheckableBrandAdapter(ArrayList<Brand> brands) {
        this.brands = brands;
    }

    @Override
    public int getCount() {
        return brands.size();
    }

    @Override
    public Object getItem(int position) {
        return brands.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CheckableBrandLayout l;

        if (convertView == null) {
            l = new CheckableBrandLayout(parent.getContext(), brands.get(position));

            l.setLayoutParams(new GridView.LayoutParams(
                    GridView.LayoutParams.MATCH_PARENT,
                    GridView.LayoutParams.MATCH_PARENT));

        } else {
            l = (CheckableBrandLayout) convertView;
        }

        //Log.d("l dans les checkable", "oui oui oui");
        //Log.d("checkablebrand after l", checkableBrands.toString());

        if (!checkableBrands.contains(l)){
            checkableBrands.add(l);
        }

        return l;
    }

    public void checkBrand (Brand b){
        Log.d("List brands fcbadapt", brands.toString());
        Log.d("Brand b", b.toString());
        Log.d("Brand b contains", Boolean.toString(brands.contains(b)));
        Log.d("index of b in brands", Integer.toString(brands.indexOf(b)));
        Log.d("checkablebrands", checkableBrands.toString());
        Log.d("checkable item at index", checkableBrands.get(brands.indexOf(b)).toString());

        Log.d("checkable size", Integer.toString(checkableBrands.size()));

        checkableBrands.get(brands.indexOf(b)).setChecked(true);
        Log.d("is checked", Boolean.toString(checkableBrands.get(brands.indexOf(b)).isChecked()));
    }

}
