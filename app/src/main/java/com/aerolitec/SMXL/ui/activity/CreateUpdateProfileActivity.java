package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.customLayout.ProfilePictureRoundedImageView;
import com.aerolitec.SMXL.ui.fragment.CreateProfileDetailsFragment;
import com.aerolitec.SMXL.ui.fragment.SelectBrandsFragment;
import com.aerolitec.SMXL.ui.fragment.UpdateProfileDetailsFragment;

/**
 * Created by Clement on 25/04/15.
 */
/*
* "fragmentType" Put extras:
 * create: create new profile
 * update: update existing profile
 * brands: updates existing brands for current profile
 */
public class CreateUpdateProfileActivity extends Activity{

    private boolean confirmExit=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_profile);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);


        String fragmentType=getIntent().getStringExtra("fragmentType");
        if (savedInstanceState == null) {
            Fragment f=null;
            switch(fragmentType){
                case "create":
                    f=new CreateProfileDetailsFragment();
                    break;
                case "update":
                    f=new UpdateProfileDetailsFragment();
                    break;
                case "brands":
                    f=new SelectBrandsFragment();
                    break;
            }
            getFragmentManager().beginTransaction()
                    .add(R.id.activity_create_profile, f)
                    .commit();
        }


    }

    @Override
    public void onBackPressed() {
        if (confirmExit) {
            super.onBackPressed();
        } else {
            confirmExit = true;
            Toast toast = Toast.makeText(this, getResources().getText(R.string.returnCreate), Toast.LENGTH_SHORT);
            AsyncTask task = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        Log.d("catchCreateProfile", "InterruptedException");
                    }
                    confirmExit = false;
                    return null;
                }
            };
            toast.show();
            task.execute();
        }
    }
}
