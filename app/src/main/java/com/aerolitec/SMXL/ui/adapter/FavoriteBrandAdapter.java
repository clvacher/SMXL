package com.aerolitec.SMXL.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;

import java.util.ArrayList;


public class FavoriteBrandAdapter extends ArrayAdapter<Brand> {

    private Context context;

    public FavoriteBrandAdapter(Context context,int resource, ArrayList<Brand> brands){
        super(context, resource , brands);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.brand_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.tvBrandName = (TextView) convertView.findViewById(R.id.tvBrandName);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Brand brand = getItem(position);

        holder.tvBrandName.setText(brand.getBrand_name());

        return convertView;
    }

    public static class ViewHolder {
        TextView tvBrandName;
    }
}
