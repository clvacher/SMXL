package com.aerolitec.SMXL.tools.serverConnexion;

import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.model.User;

/**
 * Created by Clement on 5/27/2015.
 */
public interface LoginCreateAccountInterface {
    void accountRetrieved(MainUser mainUser);
    void accountRetrieved(User user);
    void nonExistingAccount();
    void wrongPassword();
    void serverError(String errorMsg);
    void localError(String errorMsg);
}
