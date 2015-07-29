package com.aerolitec.SMXL.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.ui.fragment.ConnexionDefaultFragment;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;

/**
 * Created by Jerome on 05/05/2015.
 */
public class ConnexionActivity extends NoDrawerActivity{

    private ProfileTracker mProfileTracker;

    private void displayWelcomeMessage(Profile profile) {
        if(profile != null){
            Toast.makeText(getApplicationContext(), "Welcome "+profile.getName(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void init(Bundle bundle) {
        super.init(bundle);

        FacebookSdk.sdkInitialize(getApplicationContext());

        this.newSection(" ", new ConnexionDefaultFragment(), false, menu);

        getSupportActionBar().setIcon(R.drawable.logo);

        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayWelcomeMessage(newProfile);
            }
        };

        mProfileTracker.startTracking();

        //Skips connexion if the mainUser exists
        if((MainUserManager.get().getMainUser())!=null){

            Intent intent = new Intent(getApplicationContext(), MainNavigationActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public int headerType() {
        return super.headerType();
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        displayWelcomeMessage(profile);
        setDrawerBlocked();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProfileTracker.stopTracking();
    }


}
