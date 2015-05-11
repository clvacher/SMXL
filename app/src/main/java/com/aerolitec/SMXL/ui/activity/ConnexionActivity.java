package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

/**
 * Created by Jerome on 05/05/2015.
 */
public class ConnexionActivity extends Activity{


    AccessToken accessToken;
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
                final Profile profile = Profile.getCurrentProfile();

                accessToken = AccessToken.getCurrentAccessToken();




                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject userJson, GraphResponse response) {
                        if (userJson != null) {
                            Log.d("userJson", userJson.toString());
                            String sex;
                            if(userJson.optString("gender").equals("male")){
                                sex = "H";
                            }
                            else{
                                sex = "F";
                            }

                            UserManager.get().setUser(SMXL.getUserDBManager().createUser(userJson.optString("first_name"),
                                            userJson.optString("last_name"),
                                            null,
                                            sex,
                                            "https://graph.facebook.com/" + userJson.optString("id") + "/picture?type=large",
                                            userJson.optString("birthday"))
                            );

                            Log.d("birthday", (userJson.optString("birthday")).toString());

                            finish();
                            Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                Log.d("Cancel", "Login fb cancel");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("Error", "Login fb error");

            }
        });

    }


    /*

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        Log.d("request complete", "tototototototo");
                    }
                });
                */
        /*Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        request.setParameters(parameters);
        request.executeAsync();*/


/*

        OnLoginListener onLoginListener = new OnLoginListener() {
            @Override
            public void onLogin() {
                // change the state of the button or do whatever you want
                Log.i(TAG, "Logged in");
            }

            @Override
            public void onNotAcceptingPermissions(Permission.Type type) {
                // user didn't accept READ or WRITE permission
                Log.w(TAG, String.format("You didn't accept %s permissions", type.name()));
            }

*/
    /*
     * You can override other methods here:
     * onThinking(), onFail(String reason), onException(Throwable throwable)
     */





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void skipConnexion (View v){
        Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
        startActivity(intent);
    }


}
