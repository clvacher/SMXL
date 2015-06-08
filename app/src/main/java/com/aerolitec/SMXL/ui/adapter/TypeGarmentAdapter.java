package com.aerolitec.SMXL.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.GarmentType;

import java.util.ArrayList;


/**
 * Created by stephaneL on 30/03/14.
 */
public class TypeGarmentAdapter extends ArrayAdapter<GarmentType> {

    private Context context;

    public TypeGarmentAdapter(Context context,int resource, ArrayList<GarmentType> garmentItems){
        super(context, resource , garmentItems);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.type_garment_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.tvTypeGarment = (TextView) convertView.findViewById(R.id.tvTypeGarment);
            //holder.tvSex = (TextView) convertView.findViewById(R.id.tvSex);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        GarmentType item = getItem(position);
        Log.d("garmentTYPEDB",item.getType());
        int tmp=context.getResources().getIdentifier(item.getType(), "string", context.getPackageName());
        holder.tvTypeGarment.setText(tmp);
        //holder.tvSex.setText(item.getSex());

        return convertView;
    }

    public static class ViewHolder {
        TextView tvTypeGarment;
        //TextView tvSex;
    }
}
