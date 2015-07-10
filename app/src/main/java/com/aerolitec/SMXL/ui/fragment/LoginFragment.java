package com.aerolitec.SMXL.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.tools.serverConnexion.GetCorrespondingProfilesHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.GetMainUserHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.LoginCreateAccountInterface;
import com.aerolitec.SMXL.tools.serverConnexion.PostMainUserHttpAsyncTask;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.MainNavigationActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class LoginFragment extends SuperLoginCreateAccountFragment implements LoginCreateAccountInterface{

    public LoginFragment() {
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
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                requestStatus.setVisibility(View.VISIBLE);
                requestStatus.setText(getResources().getString(R.string.retrieving_account));
                if (UtilityMethodsv2.isConnected(getActivity())) {

                    if(inputFormatIsValid()) {
                        MainUser mainUser = new MainUser();
                        mainUser.setEmail(email.getText().toString());
                        mainUser.setPassword(password.getText().toString());
                        MainUserManager.get().setMainUser(mainUser);

                        new GetMainUserHttpAsyncTask(fragment).execute();//todo email.getText().toString(),password.getText().toString() dans execute
                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        v.setVisibility(View.VISIBLE);
                    }

                }
                else {
                    requestStatus.setText(getResources().getString(R.string.no_connexion));
                    progressBar.setVisibility(View.GONE);
                    v.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public void accountRetrieved(MainUser mainUser) {

        new GetCorrespondingProfilesHttpAsyncTask().execute(mainUser.getServerId());

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

        getActivity().finish();
        Intent intent = new Intent(getActivity().getApplicationContext(), MainNavigationActivity.class);
        startActivity(intent);
    }

    @Override
    public void nonExistingAccount() {
        requestStatus.setText(getResources().getString(R.string.wrong_email));
        signIn.setVisibility(View.VISIBLE);
        getActivity().setResult(Activity.RESULT_CANCELED);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void wrongPassword() {
        requestStatus.setText(getResources().getString(R.string.wrong_password));
        signIn.setVisibility(View.VISIBLE);
        getActivity().setResult(Activity.RESULT_CANCELED);
        progressBar.setVisibility(View.GONE);
    }
}
