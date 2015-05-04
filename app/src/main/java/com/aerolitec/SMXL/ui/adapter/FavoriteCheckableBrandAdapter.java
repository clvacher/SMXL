package com.aerolitec.SMXL.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
public class FavoriteCheckableBrandAdapter extends ArrayAdapter<Brand> {

    private Context mContext;

    public FavoriteCheckableBrandAdapter(Activity context, int resource, ArrayList<Brand> brands) {
        super(context, resource, brands);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CheckableBrandLayout l;

        if (convertView == null) {
            l = new CheckableBrandLayout(mContext, this.getItem(position));

            l.setLayoutParams(new GridView.LayoutParams(
                    GridView.LayoutParams.MATCH_PARENT,
                    GridView.LayoutParams.MATCH_PARENT));

        } else {
            l = (CheckableBrandLayout) convertView;
            l.setBrand(this.getItem(position));
        }

        return l;
    }
}
