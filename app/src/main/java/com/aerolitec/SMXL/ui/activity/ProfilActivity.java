package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.model.UserClothes;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.tools.services.OnProfileSelected;
import com.aerolitec.SMXL.tools.services.SizeGuideDataBase;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.adapter.ProfileItem;
import com.aerolitec.SMXL.ui.fragment.ProfilesDetailFragment;
import com.aerolitec.SMXL.ui.fragment.ProfilesFragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProfilActivity extends Activity implements OnProfileSelected {

    private User user;
    private SMXL SMXL;
    private static final int PICKFILE_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        if (SMXL == null)
            SMXL = new SMXL();

        getActionBar().setDisplayShowTitleEnabled(false);

        SizeGuideDataBase db = new SizeGuideDataBase(this);
        SMXL.setDataBase(db);
        db.getWritableDatabase();
        db.close();
        user = SMXL.getUser();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ProfilesFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.addProfile) {
            Intent intent = new Intent(getApplicationContext(), CreateProfile.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_apropos){
            Intent intent = new Intent(getApplicationContext(), AProposActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.importProfile){
            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*.smxl");
                startActivityForResult(intent,PICKFILE_RESULT_CODE);
            } catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(this , "Veuillez installer un gestionnaire de fichier", Toast.LENGTH_LONG).show();
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch(requestCode){
            case PICKFILE_RESULT_CODE:
                if(resultCode==RESULT_OK){
                    Uri UriFile = data.getData();
                    File profil = new File(UriFile.getPath());
                    Intent intent = new Intent(getApplicationContext(), ImportProfilActivity.class);
                    intent.putExtra("PROFIL", profil);
                    startActivity(intent);
                }
                break;

        }
    }


    @Override
    public void profileSelect(ProfileItem profile) {
        user = SMXL.get().getDataBase().getUserById(profile.getId());
        UserManager.get().setUser(user);
        Intent intent = new Intent(getApplicationContext(), ProfilDetailActivity.class);
        startActivity(intent);
    }


    @Override
        protected void onResume() {
        getFragmentManager().beginTransaction()
                .add(R.id.container, new ProfilesFragment())
                .commit();
        super.onResume();
    }
}
