package com.aerolitec.SMXL.tools.serverConnexion;

import com.aerolitec.SMXL.model.MainUser;

/**
 * Created by Clement on 5/27/2015.
 */
public interface LoginCreateAccountInterface {
    void accountRetrieved(MainUser mainUser);
    void nonExistingAccount();
    void wrongPassword();
    void serverError(String errorMsg);
}
