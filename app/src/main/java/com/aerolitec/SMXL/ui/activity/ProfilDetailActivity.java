package com.aerolitec.SMXL.ui.activity;


import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.fragment.ProfilesDetailFragment;
import com.aerolitec.SMXL.ui.fragment.WardrobeDetailFragment;

public class ProfilDetailActivity extends FragmentActivity implements WardrobeDetailFragment.OnFragmentInteractionListener{

    private LinearLayout tab1,tab2,tab3;
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

        getActionBar().setTitle(" ");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);

        tab1= (LinearLayout) findViewById(R.id.profile);
        tab2= (LinearLayout) findViewById(R.id.wardrobe);
        tab3= (LinearLayout) findViewById(R.id.measurements);
        tab1.setBackgroundResource(R.drawable.button_fawn);

        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tab1.setBackgroundResource(R.drawable.button_fawn);
                ((TextView)findViewById(R.id.textView1)).setTextColor(getResources().getColor(R.color.SectionTitle));
                tab2.setBackgroundResource(R.drawable.button_orange_border);
                ((TextView)findViewById(R.id.textView2)).setTextColor(Color.parseColor("#FFFFFF"));
                tab3.setBackgroundResource(R.drawable.button_orange_border);
                ((TextView)findViewById(R.id.textView3)).setTextColor(Color.parseColor("#FFFFFF"));
                getFragmentManager().beginTransaction().replace(R.id.container, new ProfilesDetailFragment()).commit();
            }
        });
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tab1.setBackgroundResource(R.drawable.button_orange_border);
                ((TextView)findViewById(R.id.textView1)).setTextColor(Color.parseColor("#FFFFFF"));
                tab2.setBackgroundResource(R.drawable.button_fawn);
                ((TextView)findViewById(R.id.textView2)).setTextColor(getResources().getColor(R.color.SectionTitle));
                tab3.setBackgroundResource(R.drawable.button_orange_border);
                ((TextView)findViewById(R.id.textView3)).setTextColor(Color.parseColor("#FFFFFF"));
                getFragmentManager().beginTransaction().replace(R.id.container, new WardrobeDetailFragment()).commit();
            }
        });
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tab1.setBackgroundResource(R.drawable.button_orange_border);
                ((TextView)findViewById(R.id.textView1)).setTextColor(Color.parseColor("#FFFFFF"));
                tab2.setBackgroundResource(R.drawable.button_orange_border);
                ((TextView)findViewById(R.id.textView2)).setTextColor(Color.parseColor("#FFFFFF"));
                tab3.setBackgroundResource(R.drawable.button_fawn);
                ((TextView)findViewById(R.id.textView3)).setTextColor(getResources().getColor(R.color.SectionTitle));
            }
        });
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

