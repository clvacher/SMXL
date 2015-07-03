package com.aerolitec.SMXL.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.ShopOnLine;
import com.github.leonardoxh.fakesearchview.FakeSearchAdapter;

import java.util.ArrayList;

/**
 * Created by Jerome on 03/07/2015.
 */
public class ShopOnLineAdapter extends FakeSearchAdapter<ShopOnLine> {

    private final int resource;
    private final Context mContext;
    private LayoutInflater inflater;

    private ArrayList<ShopOnLine> shops;


    public ArrayList<ShopOnLine> getShops() {
        return shops;
    }

    public ShopOnLineAdapter(Activity context, int resource, ArrayList<ShopOnLine> shops) {
        super(shops);
        this.resource = resource;
        this.shops = shops;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.nameShop = (TextView) convertView.findViewById(R.id.tvShopName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ShopOnLine shop = getItem(position);

        holder.nameShop.setText(shop.getShoponline_name().toUpperCase());

        return convertView;
    }

    public static class ViewHolder {
        TextView nameShop;
    }
}