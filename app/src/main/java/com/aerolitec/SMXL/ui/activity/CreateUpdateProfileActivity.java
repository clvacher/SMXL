package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.fragment.ConnexionDefaultFragment;
import com.aerolitec.SMXL.ui.fragment.CreateAccountFragment;
import com.aerolitec.SMXL.ui.fragment.CreateProfileDetailsFragment;
import com.aerolitec.SMXL.ui.fragment.LoginFragment;
import com.aerolitec.SMXL.ui.fragment.SelectBrandsCreateProfileFragment;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;

import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

/**
 * Created by Clement on 25/04/15.
 */
/*
* "fragmentType" Put extras:
 * create: create new profile
 * update: update existing profile
 * brands: updates existing brands for current profile
 */

public class CreateUpdateProfileActivity extends NoDrawerActivity {

    private boolean confirmExit=false;

    @Override
    public void init(Bundle bundle) {
        super.init(bundle);

        MaterialSection section1 = this.newSection("Connexion", new CreateProfileDetailsFragment(), false, menu);
    }

    @Override
    public int headerType() {
        return super.headerType();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setDrawerBlocked();
    }


    @Override
    public void onBackPressed() {
        if (confirmExit) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
            if(fragment instanceof SelectBrandsCreateProfileFragment){
                SMXL.getUserDBManager().deleteUser(UserManager.get().getUser());
            }
            setResult(Activity.RESULT_CANCELED);
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
                        e.printStackTrace();
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
