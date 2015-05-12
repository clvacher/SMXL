package com.aerolitec.SMXL.tools.manager;

import com.aerolitec.SMXL.model.MainUser;

/**
 * Created by Jerome on 12/05/2015.
 */
public class MainUserManager {

    private static MainUserManager instance;
    private MainUser mainUser;

    public static MainUserManager get() {
        if(instance == null) {
            instance = new MainUserManager();
        }
        return instance;
    }

    public MainUser getMainUser() {
        return mainUser;
    }

    public void setMainUser(MainUser mainUser) {
        this.mainUser = mainUser;
    }
}

