package com.aerolitec.SMXL.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.ui.fragment.ConnexionDefaultFragment;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;

import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

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

        this.newSection("Connexion", new ConnexionDefaultFragment(), false, menu);


        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayWelcomeMessage(newProfile);
            }
        };

        mProfileTracker.startTracking();

        //Skips connexion if the mainUser exists
        if((MainUserManager.get().getMainUser())!=null){
            finish();
            Log.d("connexionSkip", "Connexion");
            Intent intent = new Intent(getApplicationContext(), MainNavigationActivity.class);
            startActivity(intent);
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


    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CREATE_ACCOUNT || requestCode == LOGIN){
            if (resultCode == RESULT_OK) {
                Log.d("OnactivityResult", "Connexion");
                finish();
                Intent intent = new Intent(getApplicationContext(), MainNavigationActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Result Error", Toast.LENGTH_LONG);
                //finish();
            }
        }
    }
*/
}
