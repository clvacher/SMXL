package com.aerolitec.SMXL.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.tools.services.OnProfileSelected;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.adapter.ProfileItem;
import com.aerolitec.SMXL.ui.fragment.ProfilesFragment;
import com.aerolitec.SMXL.ui.fragment.SizeGuideFragment;

import java.io.File;

import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialDevisor;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

public class ProfilActivity extends MaterialNavigationDrawer implements OnProfileSelected {

    private User user;
    private static final int PICKFILE_RESULT_CODE = 1;

    MaterialNavigationDrawer drawer = null;

/*
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private SlidingLayer slidingLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_profil);

        //getActionBar().setDisplayShowTitleEnabled(false);


        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ProfilesFragment())
                    .commit();
        }

        slidingLayer = (SlidingLayer)findViewById(R.id.slidingLayerSizeGuide);
        slidingLayer.setSlidingFromShadowEnabled(false);
        slidingLayer.setSlidingEnabled(false);


        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.viewPagerSizeGuide);
        mPagerAdapter = new SizeGuideAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        //mPager.setPageTransformer(true, new DepthPageTransformer());

    }
    */



    @Override
    public int headerType() {
        // set type. you get the available constant from MaterialNavigationDrawer class
        return MaterialNavigationDrawer.DRAWERHEADER_HEADITEMS;
    }

    // called from onCreate(), make your view init here or in your fragment.
    @Override
    public void init(Bundle savedInstanceState) {

        //setContentView(R.layout.activity_profil);

        //getActionBar().setDisplayShowTitleEnabled(false);

/*
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ProfilesFragment())
                    .commit();
        }
*/
        /*
        slidingLayer = (SlidingLayer)findViewById(R.id.slidingLayerSizeGuide);
        slidingLayer.setSlidingFromShadowEnabled(false);
        slidingLayer.setSlidingEnabled(false);


        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.viewPagerSizeGuide);
        mPagerAdapter = new SizeGuideAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        //mPager.setPageTransformer(true, new DepthPageTransformer());
*/
        drawer = this;

        MaterialDevisor materialDevisor = new MaterialDevisor();
        MaterialMenu menu = new MaterialMenu();




        // first section is loaded
        MaterialSection section5 = this.newSection("Mon Compte", this.getResources().getDrawable(R.drawable.avatar), new ProfilesFragment(), false, menu);
        MaterialSection section1 = this.newSection("Mes Profils", this.getResources().getDrawable(R.drawable.icone_hd), new ProfilesFragment(), false, menu);
        this.newDevisor(menu);

        this.newLabel("Pratiques", false, menu);
        MaterialSection section2 = this.newSection("Guide des tailles", getResources().getDrawable(R.drawable.icone_hd), new SizeGuideFragment(), false, menu);
        MaterialSection section3 = this.newSection("Magasins à proximité", this.getResources().getDrawable(R.drawable.icone_hd), new ProfilesFragment(), false, menu);
        this.newDevisor(menu);

        /*MaterialSection section6 = this.newSection("Magasins à proximité", this.getResources().getDrawable(R.drawable.icone_hd), new ProfilesFragment(), false, menu);
        MaterialSection section7 = this.newSection("Magasins à proximité", this.getResources().getDrawable(R.drawable.icone_hd), new ProfilesFragment(), false, menu);
        MaterialSection section8 = this.newSection("Magasins à proximité", this.getResources().getDrawable(R.drawable.icone_hd), new ProfilesFragment(), false, menu);
        MaterialSection section9 = this.newSection("Magasins à proximité", this.getResources().getDrawable(R.drawable.icone_hd), new ProfilesFragment(), false, menu);*/


        this.newLabel("Paramètres", true, menu);

        MaterialSection section4 = this.newSection("Réglages", this.getResources().getDrawable(R.drawable.ic_action_settings), new ProfilesFragment(), true, menu);


        //section1.setFillIconColor(true);

        //section1.setSectionColor(getResources().getColor(R.color.DefaultBackgroundColor));
        //section2.setSectionColor(getResources().getColor(R.color.DefaultBackgroundColor));


        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);


        // create Head Item
        //MaterialHeadItem headItem = new MaterialHeadItem(this, "F HeadItem", "F Subtitle", drawableAppIcon, R.drawable.blur_geom, menu);
        MaterialHeadItem headItem1 = new MaterialHeadItem(this, "Nom Prénom", "E-Mail", drawableAppIcon, R.drawable.blur_geom, menu);
        headItem1.setLoadFragmentOnChanged(true);


        // add head Item (menu will be loaded automatically)

        //this.addHeadItem(headItem);
        this.addHeadItem(headItem1);
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
        user = SMXL.getUserDBManager().getUser(profile.getId());
        UserManager.get().setUser(user);
        Intent intent = new Intent(getApplicationContext(), ProfilDetailActivity.class);
        startActivity(intent);
    }

}
