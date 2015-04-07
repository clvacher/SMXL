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


/**
 * Created by stephaneL on 21/03/14.
 */
public class CountryAdapter extends ArrayAdapter<String> {

    private Context context;

    public CountryAdapter(Context context, ArrayList<String> countryItems){
        super(context, 0 , countryItems);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.country_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.tvCodeCountry = (TextView) convertView.findViewById(R.id.tvCodeCountry);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String item = getItem(position);

        holder.tvCodeCountry.setText(item);

        return convertView;
    }

    public static class ViewHolder {
        TextView tvCodeCountry;
    }
}
