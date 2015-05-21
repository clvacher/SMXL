package com.aerolitec.SMXL.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.RoundedTransformation;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.tools.serverConnexion.PostMainUserFacebookHttpAsyncTask;
import com.aerolitec.SMXL.tools.services.OnProfileSelected;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.adapter.ProfileItem;
import com.aerolitec.SMXL.ui.fragment.ListBrandsFragment;
import com.aerolitec.SMXL.ui.fragment.ProfilesDetailFragment;
import com.aerolitec.SMXL.ui.fragment.ProfilesFragment;
import com.aerolitec.SMXL.ui.fragment.SettingsFragment;
import com.aerolitec.SMXL.ui.fragment.SizeGuideFragment;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.Profile;
import com.facebook.ProfileTracker;

import java.io.File;

import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialDevisor;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

public class MainNavigationActivity extends MaterialNavigationDrawer implements OnProfileSelected {

    private MainUser mainUser;
    private User user;
    private static final int PICKFILE_RESULT_CODE = 1;

    MaterialNavigationDrawer drawer = null;
    MaterialHeadItem mainUserHeadItem = null;
    MaterialSection sectionMyProfile = null;

    AccessTokenTracker mAccessTokenTracker;
    ProfileTracker mProfileTracker;
    int i=1;

    @Override
    public int headerType() {
        // set type. you get the available constant from MaterialNavigationDrawer class
        return MaterialNavigationDrawer.DRAWERHEADER_HEADITEMS;
    }


    @Override
    protected void onResume() {
        super.onResume();
        mAccessTokenTracker.startTracking();
        mProfileTracker.startTracking();
        if(mainUser!=null){
            setValueMainUserHeadItem();
            UserManager.get().setUser(MainUserManager.get().getMainUser().getMainProfile());
        }
    }

    private void setValueMainUserHeadItem() {
        final Bitmap bitmap = getBitmapMainUser(mainUser.getAvatar());
        RoundedTransformation roundedTransformation = new RoundedTransformation();
        RoundedBitmapDrawable drawableFactory = RoundedBitmapDrawableFactory.create(getResources(), roundedTransformation.transform(bitmap));

        drawer.setHeadItemTitle(mainUser.getFirstname() + " " + mainUser.getLastname());
        drawer.setFirstHeadItemPhoto(drawableFactory);
        sectionMyProfile.setIcon(drawableFactory);

        Log.d("COUCOU", mainUser.getFirstname() + " " + mainUser.getLastname());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAccessTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
    }

    // called from onCreate(), make your view init here or in your fragment.
    @Override
    public void init(Bundle savedInstanceState) {

        mainUser = MainUserManager.get().getMainUser();

        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                if(newProfile == null) {
                    Toast.makeText(getApplicationContext(), "Déconnexion", Toast.LENGTH_SHORT).show();
                    MainUserManager.get().setMainUser(null);

                    File file = new File(getFilesDir(),PostMainUserFacebookHttpAsyncTask.MAIN_USER_FILE);
                    file.delete();

                    SMXL.getUserDBManager().deleteAllUsers();

                    finish();
                    Intent intent = new Intent(getApplicationContext(), ConnexionActivity.class);
                    startActivity(intent);
                }
            }
        };
        mProfileTracker.startTracking();

         mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };
        mAccessTokenTracker.startTracking();

        //Log.d("Main user picture", MainUserManager.get().getMainUser().getAvatar().toString());
        drawer = this;

        MaterialDevisor materialDevisor = new MaterialDevisor();
        MaterialMenu menu = new MaterialMenu();


        // first section is loaded
        MaterialSection sectionMesProfils = this.newSection("Mes Profils", new ProfilesFragment(), false, menu);

        sectionMyProfile = this.newSection("Mon Profil", this.getResources().getDrawable(R.drawable.avatar), new ProfilesDetailFragment(), false, menu);


        //sectionMesProfils.getIcon().setColorFilter(getResources().getColor(R.color.SectionTitle), PorterDuff.Mode.MULTIPLY);  this.getResources().getDrawable(R.drawable.ic_perm_group_social_info),
        this.newDevisor(menu);

        //this.newLabel("Pratiques", false, menu);
        MaterialSection sectionSizeGuide = this.newSection("Guide des tailles", this.getResources().getDrawable(R.drawable.tshirt), new SizeGuideFragment(), false, menu);
        MaterialSection sectionMagasins = this.newSection("Magasins à proximité", new ProfilesFragment(), false, menu);
        //sectionMagasins.getIcon().setColorFilter(getResources().getColor(R.color.SectionTitle), PorterDuff.Mode.MULTIPLY);     this.getResources().getDrawable(android.R.drawable.ic_dialog_map),
        this.newDevisor(menu);

        MaterialSection sectionBrands = this.newSection("Marques", this.getResources().getDrawable(R.drawable.ic_action_labels) ,new ListBrandsFragment(), false, menu);
        MaterialSection sectionBlogs = this.newSection("Blogs", new ProfilesFragment(), false, menu);
        MaterialSection sectionMagazines = this.newSection("Magazines", new ProfilesFragment(), false, menu);


        this.newDevisor(menu);

        this.newLabel("Paramètres", false, menu);

        MaterialSection section6 = this.newSection("Réglages", this.getResources().getDrawable(R.drawable.ic_action_settings), new SettingsFragment(), false, menu);
        section6.getIcon().setColorFilter(getResources().getColor(R.color.SectionTitle), PorterDuff.Mode.MULTIPLY);


        //section1.setFillIconColor(true);

        //section1.setSectionColor(getResources().getColor(R.color.DefaultBackgroundColor));
        //section2.setSectionColor(getResources().getColor(R.color.DefaultBackgroundColor));


        MainUser mainUser = MainUserManager.get().getMainUser();
        if(mainUser==null){

            final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
            final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);
            mainUserHeadItem = new MaterialHeadItem(this, "Nom", "Prénom", drawableAppIcon, R.drawable.blur_geom, menu);

        }
        else{
            final Bitmap bitmap = getBitmapMainUser(mainUser.getAvatar());
            RoundedTransformation roundedTransformation = new RoundedTransformation();
            RoundedBitmapDrawable drawableFactory = RoundedBitmapDrawableFactory.create(getResources(), roundedTransformation.transform(bitmap));
            mainUserHeadItem = new MaterialHeadItem(this, mainUser.getFirstname()+" "+mainUser.getLastname(), mainUser.getEmail(), drawableFactory, R.drawable.blur_geom, menu);
            sectionMyProfile.setIcon(drawableFactory);
        }

        drawer.addHeadItem(mainUserHeadItem);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch(requestCode){
            /*case PICKFILE_RESULT_CODE:
                if(resultCode==RESULT_OK){
                    Uri UriFile = data.getData();
                    File profil = new File(UriFile.getPath());
                    Intent intent = new Intent(getApplicationContext(), ImportProfilActivity.class);
                    intent.putExtra("PROFIL", profil);
                    startActivity(intent);
                }
                break;
            */
        }
    }


    @Override
    public void profileSelect(ProfileItem profile) {
        user = SMXL.getUserDBManager().getUser(profile.getId());
        UserManager.get().setUser(user);
        Intent intent = new Intent(getApplicationContext(), ProfilDetailActivity.class);
        startActivity(intent);
    }

    public Bitmap getBitmapMainUser(String urlImage){

        if(urlImage!=null) {
            try {
                File file = new File(urlImage);

                if (file.exists()) {
                    //Log.d("Bitmap WARNING ProfActi", MainUserManager.get().getMainUser().getAvatar().toString());
                    //Log.d("Bitmap WARNING ProfActi", file.getAbsolutePath());

                    return BitmapFactory.decodeFile(file.getAbsolutePath());
                }
            } catch (Exception e) {
                Log.e(Constants.TAG, "Error converting picture to file : " + e.getMessage());
            }
        }

        return BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
    }



}
