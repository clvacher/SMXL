package com.aerolitec.SMXL.tools.manager;

import com.aerolitec.SMXL.model.User;

/**
 * Created by Kevin on 17/06/2014.
 */
public class UserManager {

    private static UserManager instance;
    private User user;

    public static UserManager get() {
        if(instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
