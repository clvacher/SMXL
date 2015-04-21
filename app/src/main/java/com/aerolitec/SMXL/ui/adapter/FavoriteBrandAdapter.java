package com.aerolitec.SMXL.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aerolitec.SMXL.R;

import java.util.ArrayList;


public class FavoriteBrandAdapter extends ArrayAdapter<String> {

    private Context context;

    public FavoriteBrandAdapter(Context context, ArrayList<String> brands){
        super(context, 0 , brands);
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

        String brand = getItem(position);

        holder.tvBrandName.setText(brand);

        return convertView;
    }

    public static class ViewHolder {
        TextView tvBrandName;
    }
}
