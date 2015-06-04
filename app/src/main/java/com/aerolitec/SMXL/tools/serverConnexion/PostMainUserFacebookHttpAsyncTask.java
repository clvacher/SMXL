package com.aerolitec.SMXL.tools.serverConnexion;

import android.app.Activity;
import android.content.Context;

import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;
import com.aerolitec.SMXL.tools.manager.MainUserManager;

import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Clement on 5/19/2015.
 */
public class PostMainUserFacebookHttpAsyncTask extends PostMainUserHttpAsyncTask{

    public PostMainUserFacebookHttpAsyncTask(Activity activity) {
        super(activity);
    }

    @Override
    protected String doInBackground(Void... params){
        MainUser mainUser = MainUserManager.get().getMainUser();
        String json = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("name", mainUser.getLastname());
            jsonObject.accumulate("firstname", mainUser.getFirstname());
            jsonObject.accumulate("email", mainUser.getEmail());
            jsonObject.accumulate("password", mainUser.getPassword());
            jsonObject.accumulate("sex", mainUser.getSex());
            jsonObject.accumulate("id_facebook",mainUser.getFacebookId());
            String birthday = mainUser.getMainProfile().getBirthday();
            if (birthday != null) {
                jsonObject.accumulate("birthdate", UtilityMethodsv2.reverseBirthdayOrder(birthday));
            }
            jsonObject.accumulate("social", mainUser.getAccountType());

            json = jsonObject.toString();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return POST(SERVER_ADDRESS_CREATE_MAIN_USER, json);
    }

    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(activity, "Data Sent!", Toast.LENGTH_LONG).show();
        if(!result.equals("Did not work!")) {
            try {
                FileOutputStream fos = activity.openFileOutput(Constants.MAIN_USER_FILE, Context.MODE_PRIVATE);
                fos.flush();
                fos.write(MainUserManager.get().getMainUser().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
