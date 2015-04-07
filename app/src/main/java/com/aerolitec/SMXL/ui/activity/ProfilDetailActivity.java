package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.aerolitec.SMXL.model.User;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.fragment.ProfilesDetailFragment;

public class ProfilDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_detail);

        if(UserManager.get().getUser() == null) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            ProfilesDetailFragment profilesDetailFragment = new ProfilesDetailFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, profilesDetailFragment, "profileDetail")
                    .commit();
        }

        getActionBar().setTitle(UserManager.get().getUser().getNickname());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profil_detail, menu);
        return true;
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
                Intent share = new Intent(getApplicationContext(), CSVCreationActivity.class);
                startActivity(share);
                return true;
            case R.id.setting :
                showEditDialog();

                /*
                Intent intent = new Intent(getApplicationContext(), UserSettings.class);
                intent.putExtra("USER", user);
                startActivity(intent);
                */

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showEditDialog() {
        FragmentManager fm = getFragmentManager();
        UserSettingsDialogFragment userSettingsDialogFragment = new UserSettingsDialogFragment();
        userSettingsDialogFragment.show(fm, "fragment_UserSettings");
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
