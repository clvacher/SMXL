package com.aerolitec.SMXL.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aerolitec.SMXL.R;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;


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
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button_settings);
        loginButton.setReadPermissions("email");


        return view;
    }


}
