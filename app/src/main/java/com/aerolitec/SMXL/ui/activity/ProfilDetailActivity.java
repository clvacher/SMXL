package com.aerolitec.SMXL.ui.activity;


import android.os.Bundle;
import android.widget.LinearLayout;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.ui.fragment.TabsFragment;

import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

public class ProfilDetailActivity extends NoDrawerActivity{

    private LinearLayout tab1,tab2,tab3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*setContentView(R.layout.activity_profil_detail);

        if(UserManager.get().getUser() == null) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            ProfilesDetailFragment profilesDetailFragment = new ProfilesDetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, profilesDetailFragment, "profileDetail")
                    .commit();
        }*/

        /*getActionBar().setTitle(" ");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);*/

        /*tab1= (LinearLayout) findViewById(R.id.profile);
        final TextView tv1=(TextView)findViewById(R.id.textView1);
        tab2= (LinearLayout) findViewById(R.id.wardrobe);
        final TextView tv2_1=(TextView)findViewById(R.id.textView2);
        tab3= (LinearLayout) findViewById(R.id.measurements);
        final TextView tv3=(TextView)findViewById(R.id.textView3);

        tab2.setBackgroundResource(R.drawable.button_fawn_bottom_left);
        tab3.setBackgroundResource(R.drawable.button_fawn);

        tv1.setTextColor(Color.WHITE);
        tv2_1.setTextColor(getResources().getColor(R.color.SectionTitle));
        tv3.setTextColor(getResources().getColor(R.color.SectionTitle));

        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tab1.setBackgroundResource(R.drawable.button_orange_border);
                tab2.setBackgroundResource(R.drawable.button_fawn_bottom_left);
                tab3.setBackgroundResource(R.drawable.button_fawn);
                tv1.setTextColor(Color.WHITE);
                tv1.setText(tv1.getText()); //c'est debile mais ca marche (bug d'android version 4.1.2)
                tv2_1.setText(tv2_1.getText());
                tv3.setText(tv3.getText());
                tv2_1.setTextColor(getResources().getColor(R.color.SectionTitle));
                tv3.setTextColor(getResources().getColor(R.color.SectionTitle));
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfilesDetailFragment()).commit();
            }
        });
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tab1.setBackgroundResource(R.drawable.button_fawn_bottom_right);
                tab2.setBackgroundResource(R.drawable.button_orange_border);
                tab3.setBackgroundResource(R.drawable.button_fawn_bottom_left);
                tv1.setTextColor(getResources().getColor(R.color.SectionTitle));
                tv1.setText(tv1.getText()); //c'est debile mais ca marche (bug d'android version 4.1.2)
                tv2_1.setText(tv2_1.getText());
                tv3.setText(tv3.getText());
                tv2_1.setTextColor(Color.WHITE);
                tv3.setTextColor(getResources().getColor(R.color.SectionTitle));
                WardrobeDetailFragment wardrobeDetailFragment = new WardrobeDetailFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new WardrobeDetailFragment()).commit();
            }
        });
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tab1.setBackgroundResource(R.drawable.button_fawn);
                tab2.setBackgroundResource(R.drawable.button_fawn_bottom_right);
                tab3.setBackgroundResource(R.drawable.button_orange_border);
                tv1.setTextColor(getResources().getColor(R.color.SectionTitle));
                tv1.setText(tv1.getText()); //c'est debile mais ca marche (bug d'android version 4.1.2)
                tv2_1.setText(tv2_1.getText());
                tv3.setText(tv3.getText());
                tv2_1.setTextColor(getResources().getColor(R.color.SectionTitle));
                tv3.setTextColor(Color.WHITE);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new MeasureDetailFragment()).commit();
            }
        });*/
    }

    @Override
    public void init(Bundle bundle) {
        super.init(bundle);
        MaterialSection section1 = this.newSection(getResources().getString(R.string.profile), new TabsFragment(), false, menu);
    }

    @Override
    public int headerType() {
        return super.headerType();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDrawerBlocked();
        setBarAsNextFragment();
    }

    /*
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
                return true;/*
            case R.id.share :
                Intent share = new Intent(getApplicationContext(), CSVCreationActivity.class);
                startActivity(share);
                return true;*/
/*            case R.id.setting :
                showEditDialog();

                /*
                Intent intent = new Intent(getApplicationContext(), UserSettings.class);
                intent.putExtra("USER", user);
                startActivity(intent);
                */

/*            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        UserSettingsDialogFragment userSettingsDialogFragment = new UserSettingsDialogFragment();
        userSettingsDialogFragment.show(fm, "fragment_UserSettings");
    }*/
}

