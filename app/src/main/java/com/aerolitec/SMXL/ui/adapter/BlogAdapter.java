package com.aerolitec.SMXL.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Blog;
import com.github.leonardoxh.fakesearchview.FakeSearchAdapter;

import java.util.ArrayList;

/**
 * Created by Jerome on 05/06/2015.
 */
public class BlogAdapter extends FakeSearchAdapter<Blog> {

    private final int resource;
    private final Context mContext;
    private LayoutInflater inflater;

    private ArrayList<Blog> blogs;


    public ArrayList<Blog> getBlogs() {
        return blogs;
    }

    public BlogAdapter(Activity context, int resource, ArrayList<Blog> blogs) {
        super(blogs);
        this.resource = resource;
        this.blogs = blogs;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.nameBlog = (TextView) convertView.findViewById(R.id.tvBlogName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Blog blog = getItem(position);

        holder.nameBlog.setText(blog.getBlog_name().toUpperCase());

        return convertView;
    }

    public static class ViewHolder {
        TextView nameBlog;
    }
}
