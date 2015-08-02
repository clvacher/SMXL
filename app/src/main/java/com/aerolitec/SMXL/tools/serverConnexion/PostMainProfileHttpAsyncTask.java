package com.aerolitec.SMXL.tools.serverConnexion;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.manager.UserManager;

/**
 * Created by Nelson on 30/07/2015.
 */
public class PostMainProfileHttpAsyncTask extends PostProfileHttpAsyncTask{

    public PostMainProfileHttpAsyncTask(Activity activity) {
        super(activity);
    }

    public PostMainProfileHttpAsyncTask(Fragment fragment) {
        super(fragment);
    }

    @Override
    protected void addToUser(Integer profileId) {
        User user = UserManager.get().getUser();
        user.setServer_id(profileId);
    }
}
