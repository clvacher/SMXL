package com.aerolitec.SMXL.tools.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.aerolitec.SMXL.model.Blog;

import java.util.ArrayList;

/**
 * Created by Jerome on 05/06/2015.
 */
public class BlogDBManager extends DBManager {
    
    public static final String TABLE_NAME = "blog";
    public static final String KEY_ID_BLOG="id_blog";
    public static final String KEY_NOM_BLOG="blog_name";
    public static final String KEY_WEBSITE_BLOG="blog_website";


    public static final String CREATE_TABLE_BRAND = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_BLOG+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+KEY_NOM_BLOG+" TEXT" +
            " "+KEY_WEBSITE_BLOG+" TEXT" +
            ");";

    // Constructeur
    public BlogDBManager(Context context)
    {
        super(context);
    }


    public int updateBlog(Blog blog) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête
        open();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM_BLOG, blog.getBlog_name());
        values.put(KEY_WEBSITE_BLOG, blog.getBlog_website());

        String where = KEY_ID_BLOG+" = ?";
        String[] whereArgs = {blog.getId_blog()+""};

        int i = db.update(TABLE_NAME, values, where, whereArgs);
        close();
        return i;
    }

    public int deleteBlog(Blog blog) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon
        open();
        String where = KEY_ID_BLOG+" = ?";
        String[] whereArgs = {blog.getId_blog()+""};
        int i = db.delete(TABLE_NAME, where, whereArgs);
        close();
        return i;
    }

    public Blog getBlog(int id) {
        // Retourne l'animal dont l'id est passé en paramètre

        open();
        Blog b=new Blog();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID_BLOG + "=" + id, null);
        if (c.moveToFirst()) {
            b.setId_blog(c.getInt(c.getColumnIndex(KEY_ID_BLOG)));
            b.setBlog_name(c.getString(c.getColumnIndex(KEY_NOM_BLOG)));
            b.setBlog_website(c.getString(c.getColumnIndex(KEY_WEBSITE_BLOG)));
            c.close();
        }
        close();
        return b;
    }

    public ArrayList<Blog> getAllBlogs(){
        open();
        ArrayList<Blog> blogs = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_NOM_BLOG, null);
        boolean eof = c.moveToFirst();
        while (eof) {
            Blog b = new Blog();
            b.setId_blog(c.getInt(c.getColumnIndex(KEY_ID_BLOG)));
            b.setBlog_name(c.getString(c.getColumnIndex(KEY_NOM_BLOG)));
            b.setBlog_website(c.getString(c.getColumnIndex(KEY_WEBSITE_BLOG)));
            blogs.add(b);
            eof = c.moveToNext();
        }
        c.close();
        close();
        return blogs;
    }

} // class BlogDBManager