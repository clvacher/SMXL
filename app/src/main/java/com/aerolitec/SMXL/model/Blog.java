package com.aerolitec.SMXL.model;

import com.github.leonardoxh.fakesearchview.SearchItem;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by Jerome on 05/06/2015.
 */
public class Blog implements Serializable, Comparable, SearchItem {
    int id_blog;
    String blog_name;
    String blog_website;

    public Blog(){}

    public Blog(String blog_name, String blog_website, int id_blog) {
        this.blog_name = blog_name;
        this.blog_website = blog_website;
        this.id_blog = id_blog;
    }


    @Override
    public int compareTo(Object another) {
        return getBlog_name().compareTo(((Blog)another).getBlog_name());
    }

    @Override
    public boolean match(CharSequence charSequence) {
        return blog_name.toLowerCase(Locale.FRANCE).startsWith(charSequence.toString().toLowerCase(Locale.FRANCE));
    }

    public String getBlog_name() {
        return blog_name;
    }

    public void setBlog_name(String blog_name) {
        this.blog_name = blog_name;
    }

    public String getBlog_website() {
        return blog_website;
    }

    public void setBlog_website(String blog_website) {
        this.blog_website = blog_website;
    }

    public int getId_blog() {
        return id_blog;
    }

    public void setId_blog(int id_blog) {
        this.id_blog = id_blog;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "blog_name='" + blog_name + '\'' +
                ", id_blog=" + id_blog +
                ", blog_website='" + blog_website + '\'' +
                '}';
    }
}
