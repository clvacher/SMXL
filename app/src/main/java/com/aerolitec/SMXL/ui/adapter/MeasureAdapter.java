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
public class MeasureAdapter extends ArrayAdapter<MeasureItem> {

    private Context context;

    public MeasureAdapter(Context context, ArrayList<MeasureItem> measuresItem){
        super(context, 0 , measuresItem);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //android.R.drawable.ic_dialog_info;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.measure_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.tvNameMeasure = (TextView) convertView.findViewById(R.id.tvNameMeasure);
            holder.tvValueMeasure = (TextView) convertView.findViewById(R.id.tvValueMeasure);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MeasureItem item = getItem(position);

        holder.tvNameMeasure.setText(item.getTypeMeasure());
        holder.tvValueMeasure.setText(String.valueOf(item.getValueMeasure()));

        return convertView;
    }

    public static class ViewHolder {
        TextView tvNameMeasure;
        TextView tvValueMeasure;
    }
}
