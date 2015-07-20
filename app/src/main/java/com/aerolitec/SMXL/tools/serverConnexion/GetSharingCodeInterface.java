package com.aerolitec.SMXL.tools.serverConnexion;

/**
 * Created by Clément on 7/17/2015.
 */
public interface GetSharingCodeInterface {
    void onServerError(String ErrorMsg);
    void onCodeRetrieved(int sharingCode);
}
