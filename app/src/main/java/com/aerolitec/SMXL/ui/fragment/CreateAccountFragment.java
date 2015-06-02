package com.aerolitec.SMXL.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.tools.UtilityMethods;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.tools.serverConnexion.GetMainUserHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.LoginCreateAccountInterface;
import com.aerolitec.SMXL.tools.serverConnexion.PostMainUserHttpAsyncTask;
import com.aerolitec.SMXL.ui.activity.CreateUpdateProfileActivity;
import com.aerolitec.SMXL.ui.activity.MainNavigationActivity;


public class CreateAccountFragment extends SuperLoginCreateAccountFragment implements LoginCreateAccountInterface{

    private static final int CREATE_ACCOUNT=1;

    public CreateAccountFragment() {
        // Required empty public constructor
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
                requestStatus.setText(getResources().getString(R.string.checkingAvailability));
                if (UtilityMethods.isConnected(getActivity())) {
                    MainUser mainUser = new MainUser();
                    mainUser.setEmail(email.getText().toString());
                    mainUser.setPassword(password.getText().toString());
                    MainUserManager.get().setMainUser(mainUser);

                    new GetMainUserHttpAsyncTask(fragment).execute();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case CREATE_ACCOUNT:
                if(resultCode== Activity.RESULT_OK) {
                    MainUserManager.get().getMainUser().setMainProfile(UserManager.get().getUser());
                    new PostMainUserHttpAsyncTask(getActivity()).execute();
                    getActivity().finish();
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainNavigationActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }


    @Override
    public void accountRetrieved(MainUser mainUser){
        signIn.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        requestStatus.setText(getResources().getString(R.string.alreadyExistingAccount));
    }

    @Override
    public void nonExistingAccount(){
        Intent intent=new Intent(getActivity().getApplicationContext(), CreateUpdateProfileActivity.class);
        startActivityForResult(intent, CREATE_ACCOUNT);

//        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame_container, new CreateProfileDetailsFragment()).commit();

    }

    @Override
    public void wrongPassword() {
        accountRetrieved(null);
    }
}
