package com.aerolitec.SMXL.model;

import android.util.Log;

import com.aerolitec.SMXL.ui.SMXL;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Jerome on 11/05/2015.
 */
public class MainUser implements Serializable{
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String sex;
    private String avatar;
    private int idMainProfile;
    private int accountType;
    /*
     * 0 connexion classique
     * 1 connexion Facebook
     * 2 ...
     */

    public String getFirstname() {return firstname;}
    public void setFirstname(String firstname) {this.firstname = firstname;}

    public String getLastname() {return lastname;}
    public void setLastname(String lastname) {this.lastname = lastname;}

    public String getSex() {return sex;}
    public void setSex(String sex) {this.sex = sex;}

    public String getAvatar() {return avatar;}
    public void setAvatar(String avatar) {this.avatar = avatar;}

    public User getMainProfile() { return SMXL.getUserDBManager().getUser(idMainProfile);} // /!\ renvoie un User en ReadOnly, les set n'affectent pas la base
    public void setMainProfile(User mainProfile) {
        Log.d("setMainProfile ID", mainProfile.getId_user()+"");
        this.idMainProfile = mainProfile.getId_user();
        this.firstname = mainProfile.getFirstname();
        this.lastname = mainProfile.getLastname();
        this.sex = mainProfile.getSexe();
        this.avatar = mainProfile.getAvatar();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public MainUser(){super();}

    public MainUser(String email, String password, User mainProfile){
        super();
        this.email = email;
        this.password = password;
        setMainProfile(mainProfile);
    }

    @Override
    public String toString() {
        return "MainUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", sex='" + sex + '\'' +
                ", avatar='" + avatar + '\'' +
                ", idMainProfile=" + idMainProfile +
                '}';
    }


    public byte[] getBytes() throws IOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream= new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(this);

        byte[] data = byteArrayOutputStream.toByteArray();

        objectOutputStream.close();

        return data;
    }

}
