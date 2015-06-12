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
import com.aerolitec.SMXL.tools.serverConnexion.GetMainUserFacebookHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.LoginCreateAccountInterface;
import com.aerolitec.SMXL.tools.serverConnexion.PostMainUserFacebookHttpAsyncTask;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.MainNavigationActivity;
import com.aerolitec.SMXL.ui.activity.SuperNavigationActivity;
import com.aerolitec.SMXL.ui.adapter.TutoConnexionAdapter;
import com.facebook.AccessToken;
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
import java.net.URL;

import me.relex.circleindicator.CircleIndicator;

public class ConnexionDefaultFragment extends Fragment implements LoginCreateAccountInterface{

    private Fragment fragment = this;
    private CallbackManager mCallbackManager;

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

                        MainUserManager.get().setMainUser(new MainUser(userJson.optString("email"),"facebook",1,null));
                        new GetMainUserFacebookHttpAsyncTask(fragment).execute();
                    }
                }
            });
            request.executeAsync();


            GraphRequest graphRequest = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject userJson, GraphResponse response) {
                            if (userJson != null) {
                                String first_name = userJson.optString("first_name");
                                String last_name = userJson.optString("last_name");
                            }
                        }
                    });
            graphRequest.executeAsync();




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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();
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
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((SuperNavigationActivity)getActivity()).disableIndicator();
    }

    @Override
    public void nonExistingAccount() {
        Log.d("nonExistingAccount", "Connexion");
        queryToSetMainUserWithFacebookToken();
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
        User tmpUser = UserManager.get().getUser();
        User realUser = SMXL.getUserDBManager().createUser(tmpUser.getFirstname(), tmpUser.getLastname(), tmpUser.getBirthday(), tmpUser.getSexe(), null, null );
        mainUser.setMainProfile(realUser);
        MainUserManager.get().setMainUser(mainUser);
        UserManager.get().setUser(mainUser.getMainProfile());


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
        getActivity().finish();
    }



    private void queryToSetMainUserWithFacebookToken() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject userJson, GraphResponse response) {
                if (userJson != null) {

                    generateAndSetMainUserWithUserJSON(userJson);

                    //new HttpAsyncTask().execute("http://api.smxl-app.com/users.json");
                    new PostMainUserFacebookHttpAsyncTask(getActivity()).execute();

                    //Log.d("birthday", (userJson.optString("birthday")).toString());
                    //Log.d("email", (userJson.optString("email")).toString());

                    getActivity().finish();
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainNavigationActivity.class);
                    startActivity(intent);
                }
            }
        });
        request.executeAsync();
    }

    private void generateAndSetMainUserWithUserJSON(JSONObject userJson) {
        Log.d("userJson", userJson.toString());
        int sex;
        if (userJson.optString("gender").equals("male")) {
            sex = 1;
        } else {
            sex = 2;
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

                    User user = MainUserManager.get().getMainUser().getMainProfile();
                    user.setAvatar(file.getAbsolutePath());
                    SMXL.getUserDBManager().updateUser(user);

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


}
