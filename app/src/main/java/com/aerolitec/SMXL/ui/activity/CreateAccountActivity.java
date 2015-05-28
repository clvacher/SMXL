package com.aerolitec.SMXL.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.tools.serverConnexion.GetMainUserHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.LoginCreateAccountInterface;
import com.aerolitec.SMXL.tools.serverConnexion.PostMainUserHttpAsyncTask;

/**
 * Created by Clement on 5/13/2015.
 */
public class CreateAccountActivity extends SuperLoginCreateAccountActivity implements LoginCreateAccountInterface{

    private static final int CREATE_ACCOUNT=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signIn.setText(getResources().getString(R.string.create_account));
    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.email_sign_in_button:
                view.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                requestStatus.setText(getResources().getString(R.string.checkingAvailability));
                requestStatus.setVisibility(View.VISIBLE);
                if(isConnected()) {
                    MainUser mainUser=new MainUser();
                    mainUser.setEmail(email.getText().toString());
                    mainUser.setPassword(password.getText().toString());
                    MainUserManager.get().setMainUser(mainUser);

                    new GetMainUserHttpAsyncTask(this).execute();
                }
                break;
            case R.id.show_password:
                showPassword();
                break;
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case CREATE_ACCOUNT:
                if(resultCode==RESULT_OK) {
                    MainUserManager.get().getMainUser().setMainProfile(UserManager.get().getUser());
                    PostMainUserHttpAsyncTask tmp =new PostMainUserHttpAsyncTask(this);
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
        Intent intent=new Intent(getApplicationContext(), CreateUpdateProfileActivity.class);
        intent.putExtra("fragmentType", "create");
        startActivityForResult(intent, CREATE_ACCOUNT);
    }

    @Override
    public void serverError(String errorMsg) {

    }
}
