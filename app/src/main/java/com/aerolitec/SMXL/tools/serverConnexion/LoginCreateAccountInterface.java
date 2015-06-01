package com.aerolitec.SMXL.tools.serverConnexion;

import com.aerolitec.SMXL.model.MainUser;

/**
 * Created by Clement on 5/27/2015.
 */
public interface LoginCreateAccountInterface {
    void alreadyExistingAccount(MainUser mainUser);
    void nonExistingAccount();
    void serverError(String errorMsg);
}
