package com.aerolitec.SMXL.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.fragment.ShareProfileFragment;
import com.aerolitec.SMXL.ui.fragment.TabsProfileDetailFragment;

import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

public class ProfilDetailActivity extends NoDrawerActivity{

    @Override
    public void init(Bundle bundle) {
        super.init(bundle);
        MaterialSection section1;
        if(UserManager.get().getUser()!= null){
            section1 = this.newSection(UserManager.get().getUser().getFirstname()+" "+UserManager.get().getUser().getLastname()
                    , new TabsProfileDetailFragment(), false, menu);
        }
        else{
            section1 = this.newSection(getResources().getString(R.string.profile), new TabsProfileDetailFragment(), false, menu);
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profil_detail, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        restoreDefaultTitleCurrentSection();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                if(getFragmentManager().findFragmentByTag("addMesure") != null || getFragmentManager().findFragmentByTag("addGarment") != null){
                    getFragmentManager().popBackStack();
                } else {
                    finish();
                }
                return true;
            case R.id.share :
                Intent intent = new Intent(getApplicationContext(), SharingActivity.class);
                startActivity(intent);
                return true;
            /*case R.id.setting :
                showEditDialog();


                Intent intent = new Intent(getApplicationContext(), UserSettings.class);
                intent.putExtra("USER", user);
                startActivity(intent);
                */

           default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        UserSettingsDialogFragment userSettingsDialogFragment = new UserSettingsDialogFragment();
        userSettingsDialogFragment.show(fm, "fragment_UserSettings");
    }
}

