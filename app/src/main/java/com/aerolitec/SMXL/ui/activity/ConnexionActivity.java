package com.aerolitec.SMXL.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.ui.fragment.ConnexionDefaultFragment;
import com.aerolitec.SMXL.ui.fragment.CreateAccountFragment;
import com.aerolitec.SMXL.ui.fragment.LoginFragment;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;

import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

/**
 * Created by Jerome on 05/05/2015.
 */
public class ConnexionActivity extends NoDrawerActivity{

    private final static int CREATE_ACCOUNT=1;
    private final static int LOGIN=2;

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

        MaterialSection section1 = this.newSection("Connexion", this.getResources().getDrawable(R.drawable.ic_perm_group_social_info), new ConnexionDefaultFragment(), false, menu);
        MaterialSection section2 = this.newSection("Create Account", this.getResources().getDrawable(R.drawable.ic_perm_group_social_info), new CreateAccountFragment(), false, menu);
        MaterialSection section3 = this.newSection("Login", this.getResources().getDrawable(R.drawable.ic_perm_group_social_info), new LoginFragment(), false, menu);


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
