package com.aerolitec.SMXL.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Blog;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.BrowserActivity;
import com.aerolitec.SMXL.ui.adapter.BlogAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListBlogsFragment extends Fragment {

    private ArrayList<Blog> blogs;

    private GridView gridViewBlogs;
    private BlogAdapter adapter;

    public ListBlogsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        blogs = SMXL.getBlogDBManager().getAllBlogs();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_blogs, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridViewBlogs = (GridView) view.findViewById(R.id.gridViewBlogs);

        adapter = new BlogAdapter(getActivity(), R.layout.blog_item, blogs);
        gridViewBlogs.setAdapter(adapter);

        gridViewBlogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String urlBlog = blogs.get(position).getBlog_website();
                if (urlBlog != null) {
                    if (!urlBlog.startsWith("http://") && !urlBlog.startsWith("https://")) {
                        urlBlog = "http://" + urlBlog;
                    }
                    Intent browserIntent = new Intent(getActivity(), BrowserActivity.class);
                    browserIntent.putExtra("URL", urlBlog);
                    browserIntent.putExtra("TITLE", blogs.get(position).getBlog_name());
                    startActivity(browserIntent);
                }
            }
        });

    }
}
