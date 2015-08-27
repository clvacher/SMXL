package com.aerolitec.SMXL.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.UserWishList;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;

import java.util.ArrayList;

/**
 * Created by Nelson on 13/08/2015.
 */
public class UserWishListAdapter extends ArrayAdapter<UserWishList> {

    public UserWishListAdapter(Context context,int resource, ArrayList<UserWishList> arrayList){
        super(context, resource , arrayList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_wishlist, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.iv_thumbnail= (ImageView) convertView.findViewById(R.id.iv_thumbnail);
            holder.tv_brand = (TextView) convertView.findViewById(R.id.tv_brand);
            holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        UserWishList userWishList = getItem(position);
        String filePath = userWishList.getPicture();
        holder.iv_thumbnail.setImageResource(userWishList.getGarmentType().getCategoryGarment().getIcon());
        holder.tv_brand.setText(userWishList.getBrand().getBrand_name());
        holder.tv_size.setText(userWishList.getSize());


        return convertView;
    }

    public static class ViewHolder {
        ImageView iv_thumbnail;
        TextView tv_brand;
        TextView tv_size;
    }

}
