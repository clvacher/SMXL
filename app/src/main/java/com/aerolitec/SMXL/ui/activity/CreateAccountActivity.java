package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.aerolitec.SMXL.R;

/**
 * Created by Cl?ment on 5/13/2015.
 */
public class CreateAccountActivity extends Activity {

    private final static int ACCOUNT_CREATED=10;

    private AutoCompleteTextView email;
    private EditText password;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_create_account);
        email=(AutoCompleteTextView) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        signIn=(Button) findViewById(R.id.email_sign_in_button);

        signIn.setText(getResources().getString(R.string.create_account));

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void login(View v){
        //test connexion

        //if login==successful
        setResult(ACCOUNT_CREATED);
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
