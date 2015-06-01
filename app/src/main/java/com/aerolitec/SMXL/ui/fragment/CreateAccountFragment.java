package com.aerolitec.SMXL.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.tools.serverConnexion.GetMainUserHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.LoginCreateAccountInterface;
import com.aerolitec.SMXL.tools.serverConnexion.PostMainUserHttpAsyncTask;


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
                requestStatus.setText(getResources().getString(R.string.checkingAvailability));
                requestStatus.setVisibility(View.VISIBLE);
                if(isConnected()) {
                    MainUser mainUser=new MainUser();
                    mainUser.setEmail(email.getText().toString());
                    mainUser.setPassword(password.getText().toString());
                    MainUserManager.get().setMainUser(mainUser);

                    new GetMainUserHttpAsyncTask(fragment).execute();
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
                    PostMainUserHttpAsyncTask tmp =new PostMainUserHttpAsyncTask(getActivity());
                    tmp.execute();
                }
                break;
        }
    }


    @Override
    public void alreadyExistingAccount(MainUser mainUser){
        signIn.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        requestStatus.setText(getResources().getString(R.string.alreadyExistingAccount));
    }

    @Override
    public void nonExistingAccount(){
        /*Intent intent=new Intent(getActivity().getApplicationContext(), CreateUpdateProfileActivity.class);
        intent.putExtra("fragmentType", "create");
        startActivityForResult(intent, CREATE_ACCOUNT);*/
    }

    @Override
    public void serverError(String errorMsg) {

    }


}
