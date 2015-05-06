package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by Jerome on 05/05/2015.
 */
public class ConnexionActivity extends Activity {


    CallbackManager callbackManager;
    LoginButton loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Success", "Login fb success");
                Profile profile = Profile.getCurrentProfile();

                User user = null;
                try {
                    user = SMXL.getUserDBManager().createUser(profile.getFirstName(),profile.getLastName(), null, null, null, profile.getLinkUri().toString());
                    Log.d(Constants.TAG, "New profile created : " + user.toString());
                } catch (Exception e) {
                    Log.d(Constants.TAG, "Create user with error : " + e.getMessage());
                }
                if (user != null)
                    setResult(user.getId_user());
                else
                    setResult(0);

                finish();
                UserManager.get().setUser(user);
                Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Log.d("Cancel","Login fb cancel");

            }

            @Override
            public void onError(FacebookException e) {
                Log.d("Error","Login fb error");

            }
        });




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
