package com.aerolitec.SMXL.ui.adapter;

import java.io.Serializable;

/**
 * Created by stephaneL on 20/03/14.
 */
public class ProfileItem implements Serializable{
    int id;
    String firstName;
    String lastName;
    String avatar;

    public ProfileItem() {}

    public ProfileItem(int id, String firstName, String lastName, String avatar){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
