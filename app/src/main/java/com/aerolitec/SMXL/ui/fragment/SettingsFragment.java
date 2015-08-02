package com.aerolitec.SMXL.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.serverConnexion.PostMainUserHttpAsyncTask;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.ConnexionActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import java.io.File;


public class SettingsFragment extends Fragment {

    private View view;
    CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;


    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        //callbackManager = CallbackManager.Factory.create();
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button_settings);
        Button classicLogoutButton = (Button) view.findViewById(R.id.buttonClassicDisconnection);
        classicLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnect();
                launchConnexionActivity();
            }
        });
        switch(MainUserManager.get().getMainUser().getAccountType()){
            case 1:
                loginButton.setVisibility(View.VISIBLE);
                classicLogoutButton.setVisibility(View.GONE);
                loginButton.setReadPermissions("email");
                break;
            default:
                loginButton.setVisibility(View.GONE);
                classicLogoutButton.setVisibility(View.VISIBLE);
                break;

        }
        return view;
    }

    private void disconnect() {
        Toast.makeText(getActivity(), getResources().getString(R.string.disconnected), Toast.LENGTH_SHORT).show();
        MainUserManager.get().setMainUser(null);

        File file = new File(getActivity().getFilesDir(), Constants.MAIN_USER_FILE);
        file.delete();

        SMXL.getUserDBManager().deleteAllUsers();

    }

    private void launchConnexionActivity() {
        getActivity().finish();
        Intent intent = new Intent(getActivity().getApplicationContext(), ConnexionActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }
}
