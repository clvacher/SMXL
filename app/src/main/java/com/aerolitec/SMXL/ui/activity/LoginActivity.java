package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.aerolitec.SMXL.R;

/**
 * Created by Clement on 5/12/2015.
 */
public class LoginActivity extends Activity{

    private final static int LOGIN_SUCCESSFUL=20;

    private AutoCompleteTextView email;
    private TextView password;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_create_account);
        email=(AutoCompleteTextView) findViewById(R.id.email);
        password=(TextView) findViewById(R.id.password);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClickLogin(View v){
        //test connexion

        //if onClickLogin==successful
        setResult(LOGIN_SUCCESSFUL);
        finish();
    }

    public void showPassword(View v){
        CheckBox c=(CheckBox)v;
        if(c.isChecked()){
            password.setTransformationMethod(SingleLineTransformationMethod.getInstance());
        }
        else{
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
