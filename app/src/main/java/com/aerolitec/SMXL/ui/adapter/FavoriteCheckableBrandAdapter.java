package com.aerolitec.SMXL.ui.adapter;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.ui.customLayout.CheckableBrandLayout;

import java.util.ArrayList;

/**
 * Created by Jerome on 17/04/2015.
 */
public class FavoriteCheckableBrandAdapter extends BaseAdapter {

    private ArrayList<Brand> brands;

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
        TextView tv;

        if (convertView == null) {
            tv = new TextView(parent.getContext());
            tv.setPadding(5, 30, 5, 30);
            tv.setSingleLine(true);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(17);
            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            //tv.setBackgroundResource(R.drawable.item_brand_checkable);


            l = new CheckableBrandLayout(parent.getContext(), tv, brands.get(position));
            l.setLayoutParams(new GridView.LayoutParams(
                    GridView.LayoutParams.MATCH_PARENT,
                    GridView.LayoutParams.MATCH_PARENT));

            l.addView(tv);

        } else {
            l = (CheckableBrandLayout) convertView;
            tv = (TextView) l.getChildAt(0);
        }


        return l;
    }

}
