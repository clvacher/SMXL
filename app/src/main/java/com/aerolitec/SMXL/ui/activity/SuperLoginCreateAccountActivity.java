package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.MainUser;

/**
 * Created by Clement on 5/18/2015.
 */
/*
public abstract class SuperLoginCreateAccountActivity extends Activity implements View.OnClickListener{

    protected AutoCompleteTextView email;
    protected EditText password;
    protected TextView requestStatus;
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
        requestStatus=(TextView) findViewById(R.id.requestStatusTextView);

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
*/