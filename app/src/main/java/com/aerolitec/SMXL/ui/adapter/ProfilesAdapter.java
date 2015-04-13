package com.aerolitec.SMXL.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.ImageHelper;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.widget.RoundedTransformation;
import com.makeramen.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by stephaneL on 20/03/14.
 */
public class ProfilesAdapter extends ArrayAdapter<ProfileItem> {

    private Context context;

    public ProfilesAdapter(Context context, ArrayList<ProfileItem> profilesItem){
        super(context, 0 , profilesItem);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.profiles_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.avatar = (RoundedImageView) convertView.findViewById(R.id.imgAvatar);
            holder.lastName = (TextView) convertView.findViewById(R.id.tvLastName);
            holder.firstName = (TextView) convertView.findViewById(R.id.tvFirstName);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProfileItem item = getItem(position);

        int width=holder.avatar.getLayoutParams().width;

        holder.lastName.setText(item.getLastName());
        holder.firstName.setText(item.getFirstName());


        String urlImage = item.getAvatar();

        if(urlImage != null) {
            try {
                File file = new File(urlImage);

                if (file.exists()) {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                    // Calculate inSampleSize
                    options.inSampleSize = ImageHelper.calculateInSampleSize(options, width, width);

                    // Decode bitmap with inSampleSize set
                    options.inJustDecodeBounds = false;
                    holder.avatar.setImageBitmap(ImageHelper.getCorrectBitmap(BitmapFactory.decodeFile(file.getAbsolutePath(), options), file.getAbsolutePath()));
                }
            } catch (Exception e) {
                Log.e(Constants.TAG, "Error converting picture to file : " + e.getMessage());
            }
        }
        else{
            holder.avatar.setImageResource(R.drawable.avatar);
        }


        return convertView;
    }

    public static class ViewHolder {
        RoundedImageView avatar;
        TextView lastName;
        TextView firstName;
    }


}

