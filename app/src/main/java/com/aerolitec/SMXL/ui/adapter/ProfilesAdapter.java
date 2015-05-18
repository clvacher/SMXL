package com.aerolitec.SMXL.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.ui.customLayout.ProfilePictureRoundedImageView;

import java.util.ArrayList;


public class ProfilesAdapter extends ArrayAdapter<ProfileItem> {

    private Context context;

    public ProfilesAdapter(Context context, ArrayList<ProfileItem> profilesItem){
        super(context, 0 , profilesItem);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null){
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.profiles_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.avatar = (ProfilePictureRoundedImageView) convertView.findViewById(R.id.imgAvatar);
            holder.lastName = (TextView) convertView.findViewById(R.id.tvLastName);
            holder.firstName = (TextView) convertView.findViewById(R.id.tvFirstName);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProfileItem item = getItem(position);

        holder.lastName.setText(item.getLastName());
        holder.firstName.setText(item.getFirstName());

        final String urlImage = item.getAvatar();

        holder.avatar.setImage(urlImage);

        return convertView;
    }

    public static class ViewHolder {
        ProfilePictureRoundedImageView avatar;
        TextView lastName;
        TextView firstName;
    }

}

