package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.serverConnexion.PostMainUserFacebookHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.PostMainUserHttpAsyncTask;
import com.aerolitec.SMXL.ui.SMXL;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
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

    private Activity activity = this;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());



        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if (isResumed) {

                    if (currentAccessToken != null) {
                        Log.d("No Problem", "si si");
                        Toast.makeText(getBaseContext(), "Connecté avec Facebook", Toast.LENGTH_LONG).show();
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
                                    MainUser mainUser = new MainUser(
                                            userJson.optString("email"),
                                            "facebook",
                                            SMXL.getUserDBManager().createUser(
                                                    userJson.optString("first_name"),
                                                    userJson.optString("last_name"),
                                                    null, // birthday
                                                    sex,
                                                    "https://graph.facebook.com/" + userJson.optString("id") + "/picture?type=large",
                                                    null // description
                                            )
                                    );


                                    MainUserManager.get().setMainUser(mainUser);

                                    //Log.d("MainUserManagerTest", MainUserManager.get().getMainUser().toString());


                                    //FIXME
                                    //new HttpAsyncTask().execute("http://api.smxl-app.com/users.json");

                                    new PostMainUserFacebookHttpAsyncTask(activity).execute();

                                    //Log.d("birthday", (userJson.optString("birthday")).toString());
                                    //Log.d("email", (userJson.optString("email")).toString());
                                    finish();
                                    Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                        request.executeAsync();

                    } else {
                        Log.d("Deconnexion Token", "si si");
                        finish();
                        Intent intent = new Intent(getApplicationContext(), ConnexionActivity.class);
                        startActivity(intent);
                    }


                }
            }
        };
        accessTokenTracker.startTracking();

        //Skips connexion if the mainUser exists
        if((MainUserManager.get().getMainUser())!=null){
            finish();
            Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_connexion);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        //loginButton.setReadPermissions("user_birthday");
    }

    @Override
    public void onResume() {
        super.onResume();
        isResumed = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //accessTokenTracker.stopTracking();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case LOGIN:
                if (resultCode == RESULT_OK) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                    startActivity(intent);
                }
                break;
            case CREATE_ACCOUNT:
                if (resultCode == RESULT_OK) {
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
