package com.aerolitec.SMXL.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.BrowserActivity;

import java.util.ArrayList;


public class FavoriteBrandAdapter extends ArrayAdapter<Brand> {

    private Context context;

    public FavoriteBrandAdapter(Context context,int resource, ArrayList<Brand> brands){
        super(context, resource , brands);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.brand_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.tvBrandName = (TextView) convertView.findViewById(R.id.tvBrandName);
            holder.deleteBrand = (ImageView) convertView.findViewById(R.id.deleteBrandIcon);
            //holder.browser = (ImageView) convertView.findViewById(R.id.browserIcon);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Brand brand = getItem(position);

        View.OnClickListener onClickListenerBrowser = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlBrand = getItem(position).getBrandWebsite();
                if (urlBrand != null && !urlBrand.isEmpty()) {
                    if (!urlBrand.startsWith("http://") && !urlBrand.startsWith("https://")) {
                        urlBrand = "http://" + urlBrand;
                    }
                    Intent browserIntent = new Intent(context, BrowserActivity.class);
                    browserIntent.putExtra("URL", urlBrand);
                    browserIntent.putExtra("TITLE", getItem(position).getBrand_name());
                    context.startActivity(browserIntent);
                }
            }
        };

        holder.tvBrandName.setText(brand.getBrand_name());

        holder.deleteBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMXL.getUserBrandDBManager().deleteUserBrand(UserManager.get().getUser(), brand);
                remove(brand);
                notifyDataSetChanged();
                Log.d("Brand adapter remove", brand.toString());
                UtilityMethodsv2.setListViewHeightBasedOnChildren((ListView) parent);
            }
        });

        //holder.browser.setOnClickListener(onClickListenerBrowser);

        holder.tvBrandName.setOnClickListener(onClickListenerBrowser);


        return convertView;
    }

    public static class ViewHolder {
        TextView tvBrandName;
        ImageView deleteBrand;
        //ImageView browser;
    }
}
