package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.serverConnexion.PostMainUserFacebookHttpAsyncTask;
import com.aerolitec.SMXL.ui.SMXL;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Jerome on 05/05/2015.
 */
public class ConnexionActivity extends Activity{

    private final static int CREATE_ACCOUNT=1;
    private final static int LOGIN=2;
    private final static int ACCOUNT_CREATED=10;
    private final static int LOGIN_SUCCESSFUL=20;

    private CallbackManager mCallbackManager;

    private AccessTokenTracker mAccessTokenTracker;
    private ProfileTracker mProfileTracker;
    private Activity activity = this;

    private FacebookCallback<LoginResult> mFacebookCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken  = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            displayWelcomeMessage(profile);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    private void displayWelcomeMessage(Profile profile) {
        if(profile != null){
            Toast.makeText(getApplicationContext(), "Welcome "+profile.getName(), Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
                if (newToken != null && MainUserManager.get().getMainUser() == null) {
                    queryToSetMainUserWithFacebookToken(newToken);
                }
            }
        };

        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayWelcomeMessage(newProfile);
            }
        };

        mAccessTokenTracker.startTracking();
        mProfileTracker.startTracking();

        //Skips connexion if the mainUser exists
        if((MainUserManager.get().getMainUser())!=null){
            finish();
            Intent intent = new Intent(getApplicationContext(), MainNavigationActivity.class);
            startActivity(intent);
        }
        
        setContentView(R.layout.activity_connexion);
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        //loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(mCallbackManager, mFacebookCallBack);


        //loginButton.setReadPermissions("user_birthday");

    }

    private void queryToSetMainUserWithFacebookToken(AccessToken newToken) {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject userJson, GraphResponse response) {
                if (userJson != null) {

                    generateAndSetMainUserWithUserJSON(userJson);

                    //FIXME
                    //new HttpAsyncTask().execute("http://api.smxl-app.com/users.json");
                    new PostMainUserFacebookHttpAsyncTask(activity).execute();

                    //Log.d("birthday", (userJson.optString("birthday")).toString());
                    //Log.d("email", (userJson.optString("email")).toString());

                    finish();
                    Intent intent = new Intent(getApplicationContext(), MainNavigationActivity.class);
                    startActivity(intent);
                }
            }
        });
        request.executeAsync();
    }

    private void generateAndSetMainUserWithUserJSON(JSONObject userJson) {
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
                1,
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
        saveProfilePictureMainUserAsPNG();
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        displayWelcomeMessage(profile);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAccessTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case LOGIN:
                if (resultCode == RESULT_OK) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), MainNavigationActivity.class);
                    startActivity(intent);
                }
                break;
            case CREATE_ACCOUNT:
                if (resultCode == RESULT_OK) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), MainNavigationActivity.class);
                    startActivity(intent);
                }
        }
    }


    public void onClickLogin(View v){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(intent, LOGIN);
    }

    public void onClickCreateAccount(View v){
        Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
        startActivityForResult(intent, CREATE_ACCOUNT);
    }

    public void saveProfilePictureMainUserAsPNG(){


        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                Bitmap bitmap = null;
                try {
                    URL url = new URL(MainUserManager.get().getMainUser().getAvatar());
                    bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //String root = Environment.getExternalStorageDirectory().toString();
                //File myDir = new File(root + "/req_images");

                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                File myDir = new File(root, "SMXL");

                myDir.mkdirs();

                String fname = "Image-ProfilePicture.jpg";
                File file = new File(myDir, fname);
                //Log.i(TAG, "" + file);
                if (file.exists())
                    file.delete();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();
                    MainUserManager.get().getMainUser().setAvatar(file.getAbsolutePath());

                    User user = MainUserManager.get().getMainUser().getMainProfile();
                    user.setAvatar(file.getAbsolutePath());
                    SMXL.getUserDBManager().updateUser(user);

                    //Log.d("Main user avatar", MainUserManager.get().getMainUser().getAvatar().toString());
                    //Log.d("Mainuser profile avatar", MainUserManager.get().getMainUser().getMainProfile().getAvatar().toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                /*
                sendBroadcast(new Intent(
                        Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                */

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Log.d("Image sauvegardée", "dans le main et le user");
            }
        };
        asyncTask.execute();


    }

}
