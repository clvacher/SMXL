package com.aerolitec.SMXL.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.ui.customLayout.CheckableBrandLayout;
import com.github.leonardoxh.fakesearchview.FakeSearchAdapter;

import java.util.ArrayList;

/**
 * Created by Jerome on 17/04/2015.
 */
public class FavoriteCheckableBrandAdapter extends FakeSearchAdapter<Brand> {

    private final Context mContext;
    private ArrayList<Brand> brands;


    public ArrayList<Brand> getBrands() {
        return brands;
    }

    public FavoriteCheckableBrandAdapter(Activity context, int resource, ArrayList<Brand> brands) {
        super(brands);
        this.brands = brands;
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
        }

        l.setBrand(this.getItem(position));

        return l;
    }

}
