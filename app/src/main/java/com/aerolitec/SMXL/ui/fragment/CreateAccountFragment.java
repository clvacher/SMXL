package com.aerolitec.SMXL.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.tools.serverConnexion.GetMainUserHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.LoginCreateAccountInterface;
import com.aerolitec.SMXL.tools.serverConnexion.PostAvatarProfileHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.PostMainProfileHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.PostMainUserHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.PostProfileHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.PostProfileInterface;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.CreateUpdateProfileActivity;
import com.aerolitec.SMXL.ui.activity.MainNavigationActivity;


public class CreateAccountFragment extends SuperLoginCreateAccountFragment implements LoginCreateAccountInterface,PostProfileInterface{

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
        signIn.setText(getResources().getString(R.string.create_account));
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                requestStatus.setVisibility(View.VISIBLE);
                requestStatus.setText(getResources().getString(R.string.checkingAvailability));
                if (UtilityMethodsv2.isConnected(getActivity())) {
                    if(inputFormatIsValid()) {
                        MainUser mainUser = new MainUser();
                        mainUser.setEmail(email.getText().toString());
                        mainUser.setPassword(password.getText().toString());
                        MainUserManager.get().setMainUser(mainUser);

                        new GetMainUserHttpAsyncTask(fragment).execute();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case CREATE_ACCOUNT:
                if(resultCode== Activity.RESULT_OK) {
                    //sets up the id of the main profile (local only)
                    MainUser mainUser= MainUserManager.get().getMainUser();
                    User user = UserManager.get().getUser();
                    mainUser.setMainProfile(user);
                    new PostMainProfileHttpAsyncTask(this).execute(user);
                }
                else if(resultCode == Activity.RESULT_CANCELED){
                    SMXL.getUserDBManager().deleteUser(UserManager.get().getUser());
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
    public void accountRetrieved(User user) {

    }

    @Override
    public void nonExistingAccount(){
        Intent intent=new Intent(getActivity().getApplicationContext(), CreateUpdateProfileActivity.class);
        startActivityForResult(intent, CREATE_ACCOUNT);

//        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame_container, new CreateProfileDetailsFragment()).commit();

    }

    @Override
    public void wrongPassword() {
        accountRetrieved((MainUser)null);
    }

    @Override
    public void onProfilePosted(Integer ProfileId) {

        User user = UserManager.get().getUser();
        //new PostAvatarProfileHttpAsyncTask(user.getAvatar()).execute(user.getServer_id());
        user.setServer_id(ProfileId);
        MainUser mainUser = MainUserManager.get().getMainUser();
        SMXL.getUserDBManager().updateUser(user);
        new PostMainUserHttpAsyncTask(getActivity()).execute();
/*
        //starts next activity
        Intent intent = new Intent(getActivity().getApplicationContext(), MainNavigationActivity.class);
        startActivity(intent);
        getActivity().finish();
*/
    }

    @Override
    public void onPostProfileFailure(String errorMsg) {
        //TODO display errorMsg with Toast
    }
}
