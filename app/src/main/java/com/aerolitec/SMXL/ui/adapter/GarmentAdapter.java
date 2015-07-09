package com.aerolitec.SMXL.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.UserClothes;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.BrowserActivity;

import java.util.ArrayList;


public class GarmentAdapter extends ArrayAdapter<UserClothes> {

    private Context context;

    public GarmentAdapter(Context context,int resource, ArrayList<UserClothes> garmentsItem){
        super(context, resource , garmentsItem);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.garment_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            //holder.tvTypeGarment = (TextView) convertView.findViewById(R.id.tvNameGarment);
            holder.tvBrand = (TextView) convertView.findViewById(R.id.tvBrandGarment);
            holder.tvSize = (TextView) convertView.findViewById(R.id.tvSize);
            holder.shop = (ImageView) convertView.findViewById(R.id.shopGarment);
            holder.deleteGarment=(ImageView) convertView.findViewById(R.id.deleteGarmentIcon);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final UserClothes clothes = getItem(position);


        //holder.tvTypeGarment.setText(context.getResources().getIdentifier(clothes.getGarmentType().getType(),"string",context.getPackageName()));
        holder.tvBrand.setText(clothes.getBrand().getBrand_name());
        holder.tvSize.setText(clothes.getSize());

        holder.shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlBrand = clothes.getBrand().getBrandWebsite();
                if (urlBrand != null) {
                    if (!urlBrand.startsWith("http://") && !urlBrand.startsWith("https://")) {
                        urlBrand = "http://" + urlBrand;
                    }
                    Intent browserIntent = new Intent(getContext(), BrowserActivity.class);
                    browserIntent.putExtra("URL", urlBrand);
                    browserIntent.putExtra("TITLE", clothes.getBrand().getBrand_name());
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(browserIntent);
                }
            }
        });

        holder.deleteGarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMXL.getUserClothesDBManager().deleteUserClothes(clothes);
                remove(clothes);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public static class ViewHolder {
        TextView tvTypeGarment;
        TextView tvBrand;
        TextView tvSize;
        ImageView shop;
        ImageView deleteGarment;
    }
}
