package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
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
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_connexion);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        //loginButton.setReadPermissions("user_birthday");


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Success", "Login fb success");
                accessToken = AccessToken.getCurrentAccessToken();


                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject userJson, GraphResponse response) {
                        if (userJson != null) {
                            Log.d("userJson", userJson.toString());
                            String sex;
                            if (userJson.optString("gender").equals("male")) {
                                sex = "H";
                            } else {
                                sex = "F";
                            }

                            MainUser mainUser = new MainUser(userJson.optString("last_name"),
                                    userJson.optString("first_name"),
                                    userJson.optString("email"),
                                    "facebook",
                                    sex,
                                    "https://graph.facebook.com/" + userJson.optString("id") + "/picture?type=large",
                                    //userJson.optString("birthday")
                                    userJson.toString()
                            );


                            MainUserManager.get().setMainUser(mainUser);
                            SMXL.getUserDBManager().createUser(mainUser.getFirstname(), mainUser.getLastname(), mainUser.getBirthday(), mainUser.getSexe(), mainUser.getAvatar(), mainUser.getDescription());

                            new HttpAsyncTask().execute("http://api.smxl-app.com/users.json");
                            /*try {
                                POSTUserOnServer(userJson.optString("email"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }*/

                            //Log.d("birthday", (userJson.optString("birthday")).toString());
                            //Log.d("email", (userJson.optString("email")).toString());

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

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return POSTActivity.POST(urls[0], MainUserManager.get().getMainUser());
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
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
        finish();
    }


}
