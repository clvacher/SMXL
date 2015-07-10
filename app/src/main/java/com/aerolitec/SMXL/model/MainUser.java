package com.aerolitec.SMXL.model;

import android.util.Log;

import com.aerolitec.SMXL.ui.SMXL;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jerome on 11/05/2015.
 */
public class MainUser implements Serializable{
    private int serverId;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private int sex; //1 Homme 2 Femme
    private String avatar;

    private int idMainProfile;
    private int accountType;
    private String facebookId;

    private ArrayList<Integer> profiles = new ArrayList<>();

    /*
     * 0 connexion classique
     * 1 connexion Facebook
     * 2 ...

     */

    public int getServerId() {return serverId;}
    public void setServerId(int serverId) {this.serverId = serverId;}

    public String getFirstname() {return firstname;}
    public void setFirstname(String firstname) {this.firstname = firstname;}

    public String getLastname() {return lastname;}
    public void setLastname(String lastname) {this.lastname = lastname;}

    public int getSex() {return sex;}
    public void setSex(int sex) {this.sex = sex;}

    public String getAvatar() {return avatar;}
    public void setAvatar(String avatar) {this.avatar = avatar;}

    public User getMainProfile() { return SMXL.getUserDBManager().getUser(idMainProfile);} // /!\ renvoie un User en ReadOnly, les set n'affectent pas la base
    public void setMainProfile(User mainProfile) {
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

    public int getAccountType() {
        return accountType;
    }
    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getFacebookId() {
        return facebookId;
    }
    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public ArrayList<Integer> getProfiles() {return profiles;}
    public void addProfile(Integer profile) {this.profiles.add(profile);}

    public MainUser(){super();}

    public MainUser(String email, String password, int accountType, User mainProfile){
        super();
        this.email=email;
        this.password = password;
        this.accountType = accountType;
        if(mainProfile!=null)
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
                ", serverProfiles=" + profiles +
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
