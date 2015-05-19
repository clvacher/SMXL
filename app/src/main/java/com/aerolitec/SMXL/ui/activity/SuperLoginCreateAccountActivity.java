package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.tools.manager.MainUserManager;

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
 * Created by Clément on 5/18/2015.
 */
public abstract class SuperLoginCreateAccountActivity extends Activity implements View.OnClickListener{

    protected AutoCompleteTextView email;
    protected TextView password;
    protected ProgressBar progressBar;
    protected Button signIn;
    protected CheckBox showPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_create_account);

        email=(AutoCompleteTextView) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        signIn=(Button) findViewById(R.id.email_sign_in_button);
        progressBar=(ProgressBar) findViewById(R.id.progressBar2);
        showPassword=(CheckBox) findViewById(R.id.show_password);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void showPassword(){
        if(showPassword.isChecked()){
            password.setTransformationMethod(SingleLineTransformationMethod.getInstance());
        }
        else{
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    protected boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }



}
