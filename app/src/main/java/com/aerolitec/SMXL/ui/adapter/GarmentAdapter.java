package com.aerolitec.SMXL.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.UserClothes;

import java.util.ArrayList;


/**
 * Created by stephaneL on 21/03/14.
 */
public class GarmentAdapter extends ArrayAdapter<UserClothes> {

    private Context context;

    public GarmentAdapter(Context context, ArrayList<UserClothes> garmentsItem){
        super(context, 0 , garmentsItem);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.garment_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.tvTypeGarment = (TextView) convertView.findViewById(R.id.tvNameGarment);
            holder.tvBrand = (TextView) convertView.findViewById(R.id.tvBrandGarment);
            holder.tvSize = (TextView) convertView.findViewById(R.id.tvSize);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        UserClothes clothe = getItem(position);

        holder.tvTypeGarment.setText(clothe.getType());
        holder.tvBrand.setText(clothe.getBrand());
        holder.tvSize.setText(clothe.getSize());

        return convertView;
    }

    public static class ViewHolder {
        TextView tvTypeGarment;
        TextView tvBrand;
        TextView tvSize;
    }
}
