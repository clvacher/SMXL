package com.aerolitec.SMXL.model;

/**
 * Created by Jerome on 11/05/2015.
 */
public class MainUser{
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String sex;
    private String avatar;
    private User mainProfile;

    public String getFirstname() {return firstname;}
    public void setFirstname(String firstname) {this.firstname = firstname;}

    public String getLastname() {return lastname;}
    public void setLastname(String lastname) {this.lastname = lastname;}

    public String getSex() {return sex;}
    public void setSex(String sex) {this.sex = sex;}

    public String getAvatar() {return avatar;}
    public void setAvatar(String avatar) {this.avatar = avatar;}

    public User getMainProfile() { return mainProfile;}
    public void setMainProfile(User mainProfile) {
        this.mainProfile = mainProfile;
        this.firstname = mainProfile.firstname;
        this.lastname = mainProfile.lastname;
        this.sex = mainProfile.sexe;
        this.avatar = mainProfile.avatar;
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

    public MainUser(String lastname, String firstname, String email, String password, String sex, String avatar, User mainProfile){
        super();
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
        this.sex = sex;
        this.avatar = avatar;
        this.mainProfile = mainProfile;
    }
}
