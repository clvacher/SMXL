package com.aerolitec.SMXL.tools.serverConnexion;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.MainUserManager;

/**
 * Created by NelsonGay on 30/07/2015.
 */
public class GetMainProfileHttpAsyncTask extends GetProfileHttpAsyncTask{
    //used for the ability of displaying toasts
    private Context context;

    //used for interaction with the activity
    private LoginCreateAccountInterface loginCreateAccountInterface;

    public GetMainProfileHttpAsyncTask(Activity context) {
        super();
        this.context=context;

        //Casting of the calling activity to the correct interface. Raises exception if it fails
        try {
            if (context instanceof LoginCreateAccountInterface)
                loginCreateAccountInterface = (LoginCreateAccountInterface) context;
            else
                throw new Exception("Activity using GetMainUserHttpAsyncTask must implement LoginCreateAccountInterface");
        }
        catch (Exception e){
            e.printStackTrace();
            cancel(true);
        }

    }

    public GetMainProfileHttpAsyncTask(Fragment fragment) {
        super();
        this.context=fragment.getActivity();

        //Casting of the calling activity to the correct interface. Raises exception if it fails
        try {
            if (fragment instanceof LoginCreateAccountInterface)
                loginCreateAccountInterface = (LoginCreateAccountInterface) fragment;
            else
                throw new Exception("Fragment using GetMainUserHttpAsyncTask must implement LoginCreateAccountInterface");
        }
        catch (Exception e){
            e.printStackTrace();
            cancel(true);
        }
    }

    @Override
    protected void onPostExecute(String result) {
        switch (result){
            case "null":
            case "Did not work!":
                //error message with interface
                break;
            default :
                if(result.startsWith("{\"error\":{")) {
                    break;
                }
                User user = createUserLocally(result);
                if(user!=null){
                    loginCreateAccountInterface.accountRetrieved(user);
                }
                else{
                    Toast.makeText(context,"User couldn't be created locally",Toast.LENGTH_SHORT);
                }
        }
    }

    @Override
    protected void profileToUser(User user) {
        MainUserManager.get().getMainUser().setIdMainProfile(user.getId_user());
    }
}
