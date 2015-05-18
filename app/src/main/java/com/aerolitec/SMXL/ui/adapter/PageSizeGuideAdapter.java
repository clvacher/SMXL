package com.aerolitec.SMXL.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.SizeConvert;

import java.util.ArrayList;

/**
 * Created by Jerome on 18/05/2015.
 */
public class PageSizeGuideAdapter extends ArrayAdapter<SizeConvert>{

    private Context context;

    public PageSizeGuideAdapter(Context context, ArrayList<SizeConvert> sizeConvert){
        super(context, 0 , sizeConvert);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_line_page_size_guide, null);
            holder = new ViewHolder();
            convertView.setTag(holder);

            holder.textViewUE = (TextView) convertView.findViewById(R.id.textViewUE);
            holder.textViewUS = (TextView) convertView.findViewById(R.id.textViewUS);
            holder.textViewUK = (TextView) convertView.findViewById(R.id.textViewUK);
            holder.textViewFR = (TextView) convertView.findViewById(R.id.textViewFR);
            holder.textViewSMXL = (TextView) convertView.findViewById(R.id.textViewSMXL);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }




        SizeConvert item = getItem(position);

        if(position%2==1)
            convertView.setBackgroundColor(Color.WHITE);
        else
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.DefaultBackgroundColor));


        Point size = new Point();
        ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        int width=0, height=0;
        size.equals(width, height);

        holder.textViewUE.setWidth(width / 5);
        holder.textViewUS.setWidth(width/5);
        holder.textViewUK.setWidth(width/5);
        holder.textViewFR.setWidth(width/5);
        holder.textViewSMXL.setWidth(width / 5);

        holder.textViewUE.setText(item.getValueUE());
        holder.textViewUS.setText(item.getValueUS());
        holder.textViewUK.setText(item.getValueUK());
        holder.textViewFR.setText(item.getValueFR());
        holder.textViewSMXL.setText(item.getValueSMXL());

        return convertView;
    }

    public static class ViewHolder {
        TextView textViewUE;
        TextView textViewUS;
        TextView textViewUK;
        TextView textViewFR;
        TextView textViewSMXL;
    }

}
