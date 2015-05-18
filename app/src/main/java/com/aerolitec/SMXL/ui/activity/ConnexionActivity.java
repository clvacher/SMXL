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
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

/**
 * Created by Jerome on 05/05/2015.
 */
public class ConnexionActivity extends Activity{

    private final static int CREATE_ACCOUNT=1;
    private final static int LOGIN=2;
    private final static int ACCOUNT_CREATED=10;
    private final static int LOGIN_SUCCESSFUL=20;

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private boolean isResumed = false;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        //loginButton.setReadPermissions("user_birthday");

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if (isResumed) {

                    if (currentAccessToken != null) {
                        Log.d("No Problem", "si si");

                    } else {
                        Log.d("Problem", "si si");
                        finish();
                        Intent intent = new Intent(getApplicationContext(), ConnexionActivity.class);
                        startActivity(intent);
                    }


                }
            }
        };

        if(AccessToken.getCurrentAccessToken()!=null){
            Toast.makeText(getBaseContext(), "Connecté avec Facebook", Toast.LENGTH_LONG).show();
            finish();
            Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
            startActivity(intent);
        }


//TODO jerome
        /*
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Success", "Login fb success");

                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
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
                                    //userJson.toString()
                                    ""
                            );


                            MainUserManager.get().setMainUser(mainUser);
                            SMXL.getUserDBManager().createUser(mainUser.getFirstname(), mainUser.getLastname(), mainUser.getBirthday(), mainUser.getSexe(), mainUser.getAvatar(), mainUser.getDescription());

                            new HttpAsyncTask().execute("http://api.smxl-app.com/users.json");

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
        */

    }

    @Override
    public void onResume() {
        super.onResume();
        isResumed = true;
    }

        public class HttpAsyncTask extends AsyncTask<String, Void, String> {
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




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case LOGIN:
                if (resultCode == LOGIN_SUCCESSFUL) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                    startActivity(intent);
                }
                break;
            case CREATE_ACCOUNT:
                if (resultCode == ACCOUNT_CREATED) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                    startActivity(intent);
                }

        }
    }


    public void skipConnexion (View v){
        finish();
        Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
        startActivity(intent);
    }

    public void onClickLogin(View v){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(intent, LOGIN);
    }

    public void onClickCreateAccount(View v){
        Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
        startActivityForResult(intent, CREATE_ACCOUNT);
    }
}
