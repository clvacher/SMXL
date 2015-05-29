package com.aerolitec.SMXL.tools.serverConnexion;

import android.app.Activity;

import com.aerolitec.SMXL.tools.manager.MainUserManager;

import org.json.JSONObject;

/**
 * Created by Clement on 5/29/2015.
 */
public class GetMainUserFacebookHttpAsyncTask extends GetMainUserHttpAsyncTask{

    public GetMainUserFacebookHttpAsyncTask(Activity context) {
        super(context);
    }

    @Override
    protected String doInBackground(String... params) {
        String json = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("email", MainUserManager.get().getMainUser().getEmail());
            jsonObject.accumulate("password", MainUserManager.get().getMainUser().getPassword());
            json = jsonObject.toString();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return GET(SERVER_ADDRESS_GET_MAIN_USER_FACEBOOK, json);
    }
}
