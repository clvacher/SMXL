package com.aerolitec.SMXL.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.serverConnexion.PostMainUserHttpAsyncTask;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.ConnexionActivity;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import java.io.File;


public class SettingsFragment extends Fragment {

    private View view;
    CallbackManager callbackManager;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        callbackManager = CallbackManager.Factory.create();
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        switch(MainUserManager.get().getMainUser().getAccountType()){
            case 0:
                break;
            case 1:
                break;

        }


        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button_settings);
        loginButton.setReadPermissions("email");


        return view;
    }

    private void disconnect(){
        Toast.makeText(getActivity(), "Déconnexion", Toast.LENGTH_SHORT).show();
        //TODO check si le toast apparait bien ou si il faut mettre getApplicationContext
        MainUserManager.get().setMainUser(null);

        File file = new File(getActivity().getFilesDir(), PostMainUserHttpAsyncTask.MAIN_USER_FILE);
        file.delete();

        SMXL.getUserDBManager().deleteAllUsers();

        getActivity().finish();
        Intent intent = new Intent(getActivity().getApplicationContext(), ConnexionActivity.class);
        startActivity(intent);
    }

}
