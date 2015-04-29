package com.aerolitec.SMXL.tools.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.aerolitec.SMXL.model.User;

import java.util.ArrayList;

/**
 * Created by Jerome on 28/04/2015.
 */
public class UserDBManager extends DBManager{

    public static final String TABLE_NAME = "user";
    public static final String KEY_ID_USER="id_user";
    public static final String KEY_NICKNAME_USER="nickname";
    public static final String KEY_FIRSTNAME_USER="firstname";
    public static final String KEY_LASTNAME_USER="lastname";
    public static final String KEY_BIRTHDAY_USER="birthday";
    public static final String KEY_SEX_USER="sexe";
    public static final String KEY_AVATAR_USER="avatar";
    public static final String KEY_DESCRIPTION_USER="description";
    public static final String KEY_SIZE_USER="size";
    public static final String KEY_WEIGHT_USER="weight";
    public static final String KEY_CHEST_USER="chest";
    public static final String KEY_COLLAR_USER="collar";
    public static final String KEY_BUST_USER="bust";
    public static final String KEY_WAIST_USER="waist";
    public static final String KEY_HIPS_USER="hips";
    public static final String KEY_SLEEVE_USER="sleeve";
    public static final String KEY_INSEAM_USER="inseam";
    public static final String KEY_FEET_USER="feet";
    public static final String KEY_UNITLENGTH_USER="unitL";
    public static final String KEY_UNITWEIGHT_USER="unitW";
    public static final String KEY_POINTURE_USER="pointure";


    public static final String CREATE_TABLE_USER = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_USER+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            " "+KEY_NICKNAME_USER+" TEXT NOT NULL UNIQUE" +
            " "+KEY_FIRSTNAME_USER+" TEXT" +
            " "+KEY_LASTNAME_USER+" TEXT" +
            " "+KEY_BIRTHDAY_USER+" TEXT" +
            " "+KEY_SEX_USER+" TEXT" +
            " "+KEY_AVATAR_USER+" TEXT" +
            " "+KEY_DESCRIPTION_USER+" TEXT" +
            " "+KEY_SIZE_USER+" TEXT" +
            " "+KEY_WEIGHT_USER+" TEXT" +
            " "+KEY_CHEST_USER+" TEXT" +
            " "+KEY_COLLAR_USER+" TEXT" +
            " "+KEY_BUST_USER+" TEXT" +
            " "+KEY_WAIST_USER+" TEXT" +
            " "+KEY_HIPS_USER+" TEXT" +
            " "+KEY_SLEEVE_USER+" TEXT" +
            " "+KEY_INSEAM_USER+" TEXT" +
            " "+KEY_FEET_USER+" TEXT" +
            " "+KEY_UNITLENGTH_USER+" TEXT" +
            " "+KEY_UNITWEIGHT_USER+" TEXT" +
            " "+KEY_POINTURE_USER+" TEXT" +
            ");";

    // Constructeur
    public UserDBManager(Context context)
    {
        super(context);
    }

    public long addUser(User user){
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_NICKNAME_USER, user.getNickname());
        values.put(KEY_FIRSTNAME_USER, user.getFirstname());
        values.put(KEY_LASTNAME_USER, user.getLastname());
        values.put(KEY_BIRTHDAY_USER, user.getBirthday());
        values.put(KEY_SEX_USER, user.getSexe());
        values.put(KEY_AVATAR_USER, user.getAvatar());
        values.put(KEY_DESCRIPTION_USER, user.getDescription());
        values.put(KEY_SIZE_USER, user.getSize());
        values.put(KEY_WEIGHT_USER, user.getWeight());
        values.put(KEY_CHEST_USER, user.getChest());
        values.put(KEY_COLLAR_USER, user.getCollar());
        values.put(KEY_BUST_USER, user.getBust());
        values.put(KEY_WAIST_USER, user.getWaist());
        values.put(KEY_HIPS_USER, user.getHips());
        values.put(KEY_SLEEVE_USER, user.getSleeve());
        values.put(KEY_INSEAM_USER, user.getInseam());
        values.put(KEY_FEET_USER, user.getFeet());
        values.put(KEY_UNITLENGTH_USER, user.getUnitLength());
        values.put(KEY_UNITWEIGHT_USER, user.getUnitWeight());
        values.put(KEY_POINTURE_USER, user.getPointure());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public User createUser(String firstName, String lastName, String birthday,
                           String sexe, String avatar, String description){
        User user;
        String nickname = firstName+lastName+birthday;
            ContentValues values = new ContentValues();
            values.put(KEY_NICKNAME_USER, nickname);
            values.put(KEY_FIRSTNAME_USER, firstName);
            values.put(KEY_LASTNAME_USER, lastName);
            values.put(KEY_BIRTHDAY_USER, birthday);
            values.put(KEY_SEX_USER, sexe);
            values.put(KEY_AVATAR_USER, avatar);
            values.put(KEY_DESCRIPTION_USER, description);
            values.put(KEY_SIZE_USER, 0);
            values.put(KEY_WEIGHT_USER, 0);
            values.put(KEY_CHEST_USER, 0);
            values.put(KEY_COLLAR_USER, 0);
            values.put(KEY_BUST_USER, 0);
            values.put(KEY_WAIST_USER, 0);
            values.put(KEY_HIPS_USER, 0);
            values.put(KEY_SLEEVE_USER, 0);
            values.put(KEY_INSEAM_USER, 0);
            values.put(KEY_FEET_USER, 0);
            values.put(KEY_UNITLENGTH_USER, 0);
            values.put(KEY_UNITWEIGHT_USER, 0);
            values.put(KEY_POINTURE_USER, 0);

            db.insert(TABLE_NAME, null, values);

        user = getUserByNickname(nickname);
        return user;
    }

    private User getUserByNickname(String nickname) {
        User u = new User();

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_NICKNAME_USER+"="+nickname, null);
        if (c.moveToFirst()) {
            u.setId_user(c.getInt(c.getColumnIndex(KEY_ID_USER)));
            u.setNickname(c.getString(c.getColumnIndex(KEY_NICKNAME_USER)));
            u.setFirstname(c.getString(c.getColumnIndex(KEY_FIRSTNAME_USER)));
            u.setLastname(c.getString(c.getColumnIndex(KEY_LASTNAME_USER)));
            u.setBirthday(c.getString(c.getColumnIndex(KEY_BIRTHDAY_USER)));
            u.setSexe(c.getString(c.getColumnIndex(KEY_SEX_USER)));
            u.setAvatar(c.getString(c.getColumnIndex(KEY_AVATAR_USER)));
            u.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION_USER)));

            u.setSize(convertToDouble(c.getString(c.getColumnIndex(KEY_SIZE_USER))));
            u.setWeight(convertToDouble(c.getString(c.getColumnIndex(KEY_WEIGHT_USER))));
            u.setChest(convertToDouble(c.getString(c.getColumnIndex(KEY_CHEST_USER))));
            u.setCollar(convertToDouble(c.getString(c.getColumnIndex(KEY_COLLAR_USER))));
            u.setBust(convertToDouble(c.getString(c.getColumnIndex(KEY_BUST_USER))));
            u.setWaist(convertToDouble(c.getString(c.getColumnIndex(KEY_WAIST_USER))));
            u.setHips(convertToDouble(c.getString(c.getColumnIndex(KEY_HIPS_USER))));
            u.setSleeve(convertToDouble(c.getString(c.getColumnIndex(KEY_SLEEVE_USER))));
            u.setInseam(convertToDouble(c.getString(c.getColumnIndex(KEY_INSEAM_USER))));
            u.setFeet(convertToDouble(c.getString(c.getColumnIndex(KEY_FEET_USER))));

            u.setUnitLength(convertToInt(c.getString(c.getColumnIndex(KEY_UNITLENGTH_USER))));
            u.setUnitWeight(convertToInt(c.getString(c.getColumnIndex(KEY_UNITWEIGHT_USER))));

            u.setPointure(convertToDouble(c.getString(c.getColumnIndex(KEY_POINTURE_USER))));

            c.close();
        }

        return u;
    }

    public int updateUser(User user) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_NICKNAME_USER, user.getNickname());
        values.put(KEY_FIRSTNAME_USER, user.getFirstname());
        values.put(KEY_LASTNAME_USER, user.getLastname());
        values.put(KEY_BIRTHDAY_USER, user.getBirthday());
        values.put(KEY_SEX_USER, user.getSexe());
        values.put(KEY_AVATAR_USER, user.getAvatar());
        values.put(KEY_DESCRIPTION_USER, user.getDescription());
        values.put(KEY_SIZE_USER, user.getSize());
        values.put(KEY_WEIGHT_USER, user.getWeight());
        values.put(KEY_CHEST_USER, user.getChest());
        values.put(KEY_COLLAR_USER, user.getCollar());
        values.put(KEY_BUST_USER, user.getBust());
        values.put(KEY_WAIST_USER, user.getWaist());
        values.put(KEY_HIPS_USER, user.getHips());
        values.put(KEY_SLEEVE_USER, user.getSleeve());
        values.put(KEY_INSEAM_USER, user.getInseam());
        values.put(KEY_FEET_USER, user.getFeet());
        values.put(KEY_UNITLENGTH_USER, user.getUnitLength());
        values.put(KEY_UNITWEIGHT_USER, user.getUnitWeight());
        values.put(KEY_POINTURE_USER, user.getPointure());

        String where = KEY_ID_USER+" = ?";
        String[] whereArgs = {user.getId_user()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteUser(User user) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_USER+" = ?";
        String[] whereArgs = {user.getId_user()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public User getUser(int id) {
        // Retourne l'animal dont l'id est passé en paramètre

        User u = new User();

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_USER+"="+id, null);
        if (c.moveToFirst()) {
            u.setId_user(c.getInt(c.getColumnIndex(KEY_ID_USER)));
            u.setNickname(c.getString(c.getColumnIndex(KEY_NICKNAME_USER)));
            u.setFirstname(c.getString(c.getColumnIndex(KEY_FIRSTNAME_USER)));
            u.setLastname(c.getString(c.getColumnIndex(KEY_LASTNAME_USER)));
            u.setBirthday(c.getString(c.getColumnIndex(KEY_BIRTHDAY_USER)));
            u.setSexe(c.getString(c.getColumnIndex(KEY_SEX_USER)));
            u.setAvatar(c.getString(c.getColumnIndex(KEY_AVATAR_USER)));
            u.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION_USER)));

            u.setSize(convertToDouble(c.getString(c.getColumnIndex(KEY_SIZE_USER))));
            u.setWeight(convertToDouble(c.getString(c.getColumnIndex(KEY_WEIGHT_USER))));
            u.setChest(convertToDouble(c.getString(c.getColumnIndex(KEY_CHEST_USER))));
            u.setCollar(convertToDouble(c.getString(c.getColumnIndex(KEY_COLLAR_USER))));
            u.setBust(convertToDouble(c.getString(c.getColumnIndex(KEY_BUST_USER))));
            u.setWaist(convertToDouble(c.getString(c.getColumnIndex(KEY_WAIST_USER))));
            u.setHips(convertToDouble(c.getString(c.getColumnIndex(KEY_HIPS_USER))));
            u.setSleeve(convertToDouble(c.getString(c.getColumnIndex(KEY_SLEEVE_USER))));
            u.setInseam(convertToDouble(c.getString(c.getColumnIndex(KEY_INSEAM_USER))));
            u.setFeet(convertToDouble(c.getString(c.getColumnIndex(KEY_FEET_USER))));

            u.setUnitLength(convertToInt(c.getString(c.getColumnIndex(KEY_UNITLENGTH_USER))));
            u.setUnitWeight(convertToInt(c.getString(c.getColumnIndex(KEY_UNITWEIGHT_USER))));

            u.setPointure(convertToDouble(c.getString(c.getColumnIndex(KEY_POINTURE_USER))));

            c.close();
        }

        return u;
    }

    public Cursor getUsers() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<User>();
        Cursor c = getUsers();
        boolean eof = c.moveToFirst();
        while(eof){
            User u = new User();
            u.setId_user(c.getInt(c.getColumnIndex(KEY_ID_USER)));
            u.setNickname(c.getString(c.getColumnIndex(KEY_NICKNAME_USER)));
            u.setFirstname(c.getString(c.getColumnIndex(KEY_FIRSTNAME_USER)));
            u.setLastname(c.getString(c.getColumnIndex(KEY_LASTNAME_USER)));
            u.setBirthday(c.getString(c.getColumnIndex(KEY_BIRTHDAY_USER)));
            u.setSexe(c.getString(c.getColumnIndex(KEY_SEX_USER)));
            u.setAvatar(c.getString(c.getColumnIndex(KEY_AVATAR_USER)));
            u.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION_USER)));

            u.setSize(convertToDouble(c.getString(c.getColumnIndex(KEY_SIZE_USER))));
            u.setWeight(convertToDouble(c.getString(c.getColumnIndex(KEY_WEIGHT_USER))));
            u.setChest(convertToDouble(c.getString(c.getColumnIndex(KEY_CHEST_USER))));
            u.setCollar(convertToDouble(c.getString(c.getColumnIndex(KEY_COLLAR_USER))));
            u.setBust(convertToDouble(c.getString(c.getColumnIndex(KEY_BUST_USER))));
            u.setWaist(convertToDouble(c.getString(c.getColumnIndex(KEY_WAIST_USER))));
            u.setHips(convertToDouble(c.getString(c.getColumnIndex(KEY_HIPS_USER))));
            u.setSleeve(convertToDouble(c.getString(c.getColumnIndex(KEY_SLEEVE_USER))));
            u.setInseam(convertToDouble(c.getString(c.getColumnIndex(KEY_INSEAM_USER))));
            u.setFeet(convertToDouble(c.getString(c.getColumnIndex(KEY_FEET_USER))));

            u.setUnitLength(convertToInt(c.getString(c.getColumnIndex(KEY_UNITLENGTH_USER))));
            u.setUnitWeight(convertToInt(c.getString(c.getColumnIndex(KEY_UNITWEIGHT_USER))));

            u.setPointure(convertToDouble(c.getString(c.getColumnIndex(KEY_POINTURE_USER))));

            users.add(u);
            eof=c.moveToNext();
        }
        c.close();
        return users;
    }

} // class UserDBManager

