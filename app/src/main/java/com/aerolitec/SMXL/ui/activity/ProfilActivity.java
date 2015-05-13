package com.aerolitec.SMXL.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.tools.services.OnProfileSelected;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.adapter.ProfileItem;
import com.aerolitec.SMXL.ui.fragment.ProfilesFragment;
import com.wunderlist.slidinglayer.SlidingLayer;

import java.io.File;

import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

public class ProfilActivity extends MaterialNavigationDrawer implements OnProfileSelected {

    private User user;
    private static final int PICKFILE_RESULT_CODE = 1;


    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private SlidingLayer slidingLayer;

/*
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


    MaterialNavigationDrawer drawer = null;

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

        MaterialMenu menu = new MaterialMenu();

        // first section is loaded
        MaterialSection section1 = this.newSection("Mes Profils", this.getResources().getDrawable(R.drawable.icone_hd), new ProfilesFragment(), false, menu);
        MaterialSection section2 = this.newSection("Mes Profils", this.getResources().getDrawable(R.drawable.icone_hd), new ProfilesFragment(), false, menu);

        //section1.setFillIconColor(true);

        //section1.setSectionColor(getResources().getColor(R.color.DefaultBackgroundColor));
        //section2.setSectionColor(getResources().getColor(R.color.DefaultBackgroundColor));


        // use bitmap and make a circle photo
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);

        // create Head Item
        MaterialHeadItem headItem = new MaterialHeadItem(this, "F HeadItem", "F Subtitle", drawableAppIcon, R.drawable.orange, menu);
        // add head Item (menu will be loaded automatically)
        this.addHeadItem(headItem);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.profil, menu);
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.addProfile) {
            //Intent intent = new Intent(getApplicationContext(), CreateProfileActivity.class);
            Intent intent = new Intent(getApplicationContext(), AccueilActivity.class);
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
    }*/

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


    @Override
        protected void onResume() {
/*
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new ProfilesFragment())
                    .commit();
*/
        super.onResume();
    }



    @Override
    public void onBackPressed(){
        /*if(slidingLayer.isOpened()){
            slidingLayer.closeLayer(true);
            return;
        }*/
        super.onBackPressed();
    }
}
