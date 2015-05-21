package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.serverConnexion.GetMainUserHttpAsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Clement on 5/12/2015.
 */
public class LoginActivity extends SuperLoginCreateAccountActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.email_sign_in_button:
                v.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                requestStatus.setVisibility(View.VISIBLE);
                requestStatus.setText(getResources().getString(R.string.checkingAvailability));
                if(isConnected()) {
                    //TODO
                    //Connexion au serveur
                    MainUser mainUser = new MainUser();
                    mainUser.setEmail(email.getText().toString());
                    mainUser.setPassword(password.getText().toString());
                    MainUserManager.get().setMainUser(mainUser);


                    new GetMainUserHttpAsyncTask(this).execute();//email.getText().toString(),password.getText().toString() dans execute
                }
                break;
            case R.id.show_password:
                showPassword();
                break;
        }
    }

    @Override
    public void nonExistingAccount() {
        requestStatus.setText(getResources().getString(R.string.errorRetrievingAccount));
        signIn.setVisibility(View.VISIBLE);
        setResult(RESULT_CANCELED);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void alreadyExistingAccount() {
        requestStatus.setText(getResources().getString(R.string.unimplemented));
        setResult(RESULT_OK);
    }
}
