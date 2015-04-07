package com.aerolitec.SMXL.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.CategoryGarment;

import java.util.List;

/**
 * Created by kevin on 12/06/14.
 */
public class CategoryGarmentAdapter extends ArrayAdapter<CategoryGarment> {

    private final int resource;
    private LayoutInflater inflater;

    public CategoryGarmentAdapter(Context context, int resource, List<CategoryGarment> objects) {
        super(context, resource, objects);

        this.resource = resource;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.textCategory = (TextView) convertView.findViewById(R.id.textCategory);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CategoryGarment category = getItem(position);

        holder.textCategory.setText(category.getName());

        holder.image.setImageResource(category.getIcon());

        return convertView;
    }

    public static class ViewHolder {
        ImageView image;
        TextView textCategory;
    }
}
