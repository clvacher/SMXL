package com.aerolitec.SMXL.tools.serverConnexion;

/**
 * Created by Clement on 7/13/2015.
 */
public interface PostProfileInterface {
    void onProfilePosted(Integer ProfileId);
    void onPostProfileFailure(String errorMsg);
}
