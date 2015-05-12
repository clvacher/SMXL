package com.aerolitec.SMXL.model;

/**
 * Created by Jerome on 11/05/2015.
 */
public class MainUser extends User{
    private String email;

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

    private String password;


    public MainUser(String lastname, String firstname, String email, String password, String sex, String avatar, String descritpion){
        super();
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
        this.sexe = sex;
        this.avatar = avatar;
        this.description = descritpion;
    }
}
