package com.aerolitec.SMXL.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.tools.serverConnexion.GetCorrespondingProfilesHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.GetMainProfileHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.GetMainUserFacebookHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.GetProfileHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.LoginCreateAccountInterface;
import com.aerolitec.SMXL.tools.serverConnexion.PostMainProfileHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.PostMainUserFacebookHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.PostMainUserHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.PostProfileHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.PostProfileInterface;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.CreateFacebookProfileActivity;
import com.aerolitec.SMXL.ui.activity.MainNavigationActivity;
import com.aerolitec.SMXL.ui.activity.SuperNavigationActivity;
import com.aerolitec.SMXL.ui.adapter.TutoConnexionAdapter;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class ConnexionDefaultFragment extends Fragment implements LoginCreateAccountInterface,PostProfileInterface {

    private Fragment fragment = this;
    private CallbackManager mCallbackManager;
    private JSONObject resultGraphRequest;

    private FacebookCallback<LoginResult> mFacebookCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d("SUCCESS", "TEST");

            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject userJson, GraphResponse response) {
                    if (userJson != null) {
                        resultGraphRequest = userJson;
                        MainUser tmpMainuser = new MainUser(userJson.optString("email"),"facebook",1,null);
                        MainUserManager.get().setMainUser(tmpMainuser);
                        new GetMainUserFacebookHttpAsyncTask(fragment).execute();
                    }
                }
            });
            request.executeAsync();
        }


        @Override
        public void onCancel() {
            Log.d("TEST", "TEST");
        }

        @Override
        public void onError(FacebookException e) {
            Log.d("ERROR", "ERROR");

        }

    };

    public ConnexionDefaultFragment() {
        // Required empty public constructor
    }

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private CircleIndicator circleIndicator;
    private static final int CREATE_ACCOUNT_FACEBOOK = 420;
    private AccessTokenTracker accessTokenTracker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if(currentAccessToken==null){
                    disconnect();
                }

            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connexion_default, container, false);

        Button login = (Button)view.findViewById(R.id.button_login_classic);
        Button createAccount = (Button)view.findViewById(R.id.button_create_account);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame_container, new LoginFragment()).commit();
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame_container, new CreateAccountFragment()).commit();
            }
        });

        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallbackManager, mFacebookCallBack);



        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) view.findViewById(R.id.viewPagerConnexion);
        mPagerAdapter = new TutoConnexionAdapter(getChildFragmentManager(),getActivity());
        mPager.setAdapter(mPagerAdapter);
        circleIndicator = (CircleIndicator) view.findViewById(R.id.circleIndicator);
        circleIndicator.setViewPager(mPager);
        //circleIndicator.setBackgroundColor(getResources().getColor(R.color.SectionTitle));

//        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_ACCOUNT_FACEBOOK) {
            if(resultCode == Activity.RESULT_OK) {
                User user = UserManager.get().getUser();
                new PostMainProfileHttpAsyncTask(this).execute(user);
            }
            else if(resultCode == Activity.RESULT_CANCELED)
            {
                SMXL.getUserDBManager().deleteAllUsers();
            }
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        ((SuperNavigationActivity)getActivity()).disableIndicator();
    }

    @Override
    public void nonExistingAccount() {
        Log.d("nonExistingAccount", "Connexion");
        //queryToSetMainUserWithFacebookToken();
        createProfileNotSavedYet();
    }

    @Override
    public void wrongPassword() {
        //euhhhhh... ca devrait pas passer la normalement... D=
    }

    @Override
    public void serverError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void accountRetrieved(MainUser mainUser) {
        new GetMainProfileHttpAsyncTask(this).execute(mainUser.getIdMainProfile());
        MainUserManager.get().setMainUser(mainUser);
    }

    @Override
    public void accountRetrieved(User user) {
        MainUser mainUser = MainUserManager.get().getMainUser();
        mainUser.setMainProfile(user);
        MainUserManager.get().setMainUser(mainUser);
        UserManager.get().setUser(user);

        try {
            FileOutputStream fos = getActivity().openFileOutput(Constants.MAIN_USER_FILE, Context.MODE_PRIVATE);
            fos.flush();
            fos.write(MainUserManager.get().getMainUser().getBytes());
            getActivity().setResult(Activity.RESULT_OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getActivity().getApplicationContext(), MainNavigationActivity.class);
        startActivity(intent);
        new GetCorrespondingProfilesHttpAsyncTask().execute(mainUser.getServerId());
        getActivity().finish();
    }


    private void queryToSetMainUserWithFacebookToken() {
        /*
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject userJson, GraphResponse response) {
                if (userJson != null) {

                    generateAndSetMainUserWithUserJSON(userJson);

                    //new HttpAsyncTask().execute("http://api.smxl-app.com/users.json");
                    new PostMainUserFacebookHttpAsyncTask(getActivity()).execute();

                    //Log.d("birthday", (userJson.optString("birthday")).toString());
                    //Log.d("email", (userJson.optString("email")).toString());

                    Intent intent = new Intent(getActivity().getApplicationContext(), MainNavigationActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        request.executeAsync();
        */
        if (resultGraphRequest!= null) {

            generateAndSetMainUserWithUserJSON(resultGraphRequest);
            new PostMainUserFacebookHttpAsyncTask(getActivity()).execute();

            Intent intent = new Intent(getActivity().getApplicationContext(), CreateFacebookProfileActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void generateAndSetMainUserWithUserJSON(JSONObject userJson) {
        Log.d("userJson", userJson.toString());
        int sex;
        if (userJson.optString("gender").equals("male")) {
            sex = 1;
        } else {
            sex = 2;
        }
        User user = SMXL.getUserDBManager().createUser(
                userJson.optString("first_name"),
                userJson.optString("last_name"),
                null, // birthday
                sex,
                "https://graph.facebook.com/" + userJson.optString("id") + "/picture?type=large",
                null // description

        );
        UserManager.get().setUser(user);
        MainUser mainUser = new MainUser(
                userJson.optString("email"),
                "facebook",
                1,
                user
        );
        mainUser.setFacebookId(userJson.optString("id"));

        MainUserManager.get().setMainUser(mainUser);

        Log.d("ConnexionActivity", MainUserManager.get().getMainUser().toString());

        saveProfilePictureMainUserAsPNG();
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

                    User user = UserManager.get().getUser();
                    //MainUserManager.get().getMainUser().getMainProfile();
                    user.setAvatar(file.getAbsolutePath());
                    //SMXL.getUserDBManager().updateUser(user);

                    //Log.d("Main user avatar", MainUserManager.get().getMainUser().getAvatar().toString());
                    //Log.d("Mainuser profile avatar", MainUserManager.get().getMainUser().getMainProfile().getAvatar().toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    private void createProfileNotSavedYet() {
        if (resultGraphRequest != null) {
            Intent intent = new Intent(getActivity().getApplicationContext(), CreateFacebookProfileActivity.class);
            generateAndSetMainUserWithUserJSON(resultGraphRequest);
            startActivityForResult(intent, CREATE_ACCOUNT_FACEBOOK);
        }
    }

    @Override
    public void onProfilePosted(Integer ProfileId) {
        User user = UserManager.get().getUser();
        user.setServer_id(ProfileId);
        SMXL.getUserDBManager().updateUser(user);
        new PostMainUserFacebookHttpAsyncTask(getActivity()).execute();
        /*Intent intent = new Intent(getActivity().getApplicationContext(), MainNavigationActivity.class);
        startActivity(intent);
        getActivity().finish();*/
    }

    @Override
    public void onPostProfileFailure(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT);
    }

    private void disconnect() {
        Toast.makeText(getActivity(), getResources().getString(R.string.disconnected), Toast.LENGTH_SHORT).show();
        MainUserManager.get().setMainUser(null);

        File file = new File(getActivity().getFilesDir(), Constants.MAIN_USER_FILE);
        file.delete();

        SMXL.getUserDBManager().deleteAllUsers();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }
}
