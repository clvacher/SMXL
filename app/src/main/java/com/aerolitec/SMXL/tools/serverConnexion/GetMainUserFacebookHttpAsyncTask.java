package com.aerolitec.SMXL.tools.serverConnexion;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.aerolitec.SMXL.tools.manager.MainUserManager;

import org.json.JSONObject;

/**
 * Created by Clement on 5/29/2015.
 */
public class GetMainUserFacebookHttpAsyncTask extends GetMainUserHttpAsyncTask{

    public GetMainUserFacebookHttpAsyncTask(Activity context) {
        super(context);
    }

    public GetMainUserFacebookHttpAsyncTask(Fragment fragment) {
        super(fragment);
    }
    // params[0] : email
    // params[1] : password
    @Override
    protected String doInBackground(String... params) {
        String json = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("email", params[0]);
            jsonObject.accumulate("password",params[1]);
            json = jsonObject.toString();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return GET(SERVER_ADDRESS_GET_MAIN_USER_FACEBOOK_DEV, json);
    }
}
