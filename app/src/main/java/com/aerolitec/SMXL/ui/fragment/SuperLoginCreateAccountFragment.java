package com.aerolitec.SMXL.ui.fragment;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aerolitec.SMXL.R;

/**
 * Created by Jerome on 01/06/2015.
 */
public abstract class SuperLoginCreateAccountFragment extends Fragment{

    protected Fragment fragment = this;
    protected AutoCompleteTextView email;
    protected EditText password;
    protected TextView requestStatus;
    protected ProgressBar progressBar;
    protected Button signIn;
    protected CheckBox showPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account_login, container, false);

        email=(AutoCompleteTextView) view.findViewById(R.id.email);
        password=(EditText) view.findViewById(R.id.password);
        signIn=(Button) view.findViewById(R.id.email_sign_in_button);
        progressBar=(ProgressBar) view.findViewById(R.id.progressBar2);
        showPassword=(CheckBox) view.findViewById(R.id.show_password);
        requestStatus=(TextView) view.findViewById(R.id.requestStatusTextView);


        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showPassword();
            }
        });

        return view;
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
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}
