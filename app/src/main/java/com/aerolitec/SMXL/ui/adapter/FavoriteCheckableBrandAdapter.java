package com.aerolitec.SMXL.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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

    private Activity mContext;
    private ArrayList<Brand> brands;
    private ArrayList<Brand> alreadyCheckedBrands;



    public FavoriteCheckableBrandAdapter(Activity context, ArrayList<Brand> brands, ArrayList<Brand> alreadyCheckedBrands) {
        mContext = context;
        this.brands = brands;
        this.alreadyCheckedBrands = alreadyCheckedBrands;
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
            l = new CheckableBrandLayout(mContext, brands.get(position));

            l.setLayoutParams(new GridView.LayoutParams(
                    GridView.LayoutParams.MATCH_PARENT,
                    GridView.LayoutParams.MATCH_PARENT));

        } else {
            l = (CheckableBrandLayout) convertView;
        }

        //Log.d("L est", ""+l.getBrandName()+" "+l.isChecked());
        return l;
    }
}
