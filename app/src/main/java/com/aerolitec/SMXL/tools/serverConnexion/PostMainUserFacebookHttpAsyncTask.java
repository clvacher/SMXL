package com.aerolitec.SMXL.tools.serverConnexion;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.aerolitec.SMXL.tools.manager.MainUserManager;

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
    protected void onPostExecute(String result) {
        Toast.makeText(activity, "Data Sent!", Toast.LENGTH_LONG).show();
        if(!result.equals("Did not work!")) {
            try {
                FileOutputStream fos = activity.openFileOutput(MAIN_USER_FILE, Context.MODE_PRIVATE);
                fos.flush();
                fos.write(MainUserManager.get().getMainUser().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
