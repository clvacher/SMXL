package com.aerolitec.SMXL.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.RoundedTransformation;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.tools.services.OnProfileSelected;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.adapter.ProfileItem;
import com.aerolitec.SMXL.ui.fragment.ListBlogsFragment;
import com.aerolitec.SMXL.ui.fragment.ListBrandsFragment;
import com.aerolitec.SMXL.ui.fragment.ListShopOnLineFragment;
import com.aerolitec.SMXL.ui.fragment.MeasureDetailFragment;
import com.aerolitec.SMXL.ui.fragment.ProfilesDetailFragment;
import com.aerolitec.SMXL.ui.fragment.ProfilesFragment;
import com.aerolitec.SMXL.ui.fragment.SettingsFragment;
import com.aerolitec.SMXL.ui.fragment.ShareProfileFragment;
import com.aerolitec.SMXL.ui.fragment.SizeGuideFragment;
import com.aerolitec.SMXL.ui.fragment.TabsFragmentHomeDressingQuicksize;
import com.aerolitec.SMXL.ui.fragment.WardrobeDetailFragment;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;

import java.io.File;

import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;

public class MainNavigationActivity extends SuperNavigationActivity implements OnProfileSelected {

    private MainUser mainUser;
    private User user;
    private static final int PICKFILE_RESULT_CODE = 1;

    boolean drawerOpen = false;
    MaterialHeadItem mainUserHeadItem = null;
    MaterialSection sectionMyProfile = null;

    ProfileTracker mProfileTracker;
    int i=1;

    @Override
    public int headerType() {
        // set type. you get the available constant from MaterialNavigationDrawer class
        return MaterialNavigationDrawer.DRAWERHEADER_HEADITEMS;
    }


    @Override
    public void onBackPressed() {
        if(drawerOpen){
            drawer.closeDrawer();
        }
        else{
            super.onBackPressed();
        }
        updateHamburger();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProfileTracker.stopTracking();
    }

    // called from onCreate(), make your view init here or in your fragment.
    @Override
    public void init(Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getApplicationContext());

        drawer = this;

        drawer.setDrawerStateListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                drawerOpen = true;
                InputMethodManager inputManager = ( InputMethodManager ) getSystemService(INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                drawerOpen = false;
            }
            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        mainUser = MainUserManager.get().getMainUser();

        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                if(newProfile == null) {
                    Toast.makeText(getApplicationContext(), "Déconnexion", Toast.LENGTH_SHORT).show();
                    MainUserManager.get().setMainUser(null);

                    File file = new File(getFilesDir(),Constants.MAIN_USER_FILE);
                    file.delete();

                    SMXL.getUserDBManager().deleteAllUsers();

                    finish();
                    Intent intent = new Intent(getApplicationContext(), ConnexionActivity.class);
                    startActivity(intent);
                }
            }
        };
        mProfileTracker.startTracking();

        MaterialMenu menu = new MaterialMenu();

        // first section is loaded


        MaterialSection sectionTest = this.newSection("Accueil", this.getResources().getDrawable(R.drawable.ic_action_settings), new TabsFragmentHomeDressingQuicksize(), false, menu);

        MaterialSection sectionMesProfils = this.newSection(getResources().getString(R.string.my_profiles), this.getResources().getDrawable(R.drawable.ic_perm_group_social_info), new ProfilesFragment(), false, menu);

        this.newDevisor(menu);


        //sectionMesProfils.getIcon().setColorFilter(getResources().getColor(R.color.SectionTitle), PorterDuff.Mode.MULTIPLY);

        sectionMyProfile = this.newSection(getResources().getString(R.string.my_profile), this.getResources().getDrawable(R.drawable.avatar), new ProfilesDetailFragment(), false, menu);

        MaterialSection sectionMonDressing = this.newSection(getResources().getString(R.string.my_wardrobe), this.getResources().getDrawable(R.drawable.robe_lowpx), new WardrobeDetailFragment(), false, menu);
        MaterialSection sectionMesMesures = this.newSection(getResources().getString(R.string.measurements), this.getResources().getDrawable(R.drawable.tape_measure), new MeasureDetailFragment(), false, menu);


        this.newDevisor(menu);

        //this.newLabel("Pratiques", false, menu);
        MaterialSection sectionSizeGuide = this.newSection(getResources().getString(R.string.size_guide), this.getResources().getDrawable(R.drawable.tshirt), new SizeGuideFragment(), false, menu);
        sectionSizeGuide.getIcon().setColorFilter(getResources().getColor(R.color.SectionTitle), PorterDuff.Mode.MULTIPLY);

        //MaterialSection sectionMagasins = this.newSection("Magasins à proximité", this.getResources().getDrawable(android.R.drawable.ic_dialog_map), new EnChantierFragment(), false, menu);
        //sectionMagasins.getIcon().setColorFilter(getResources().getColor(R.color.SectionTitle), PorterDuff.Mode.MULTIPLY);

        //MaterialSection sectionWishlist = this.newSection("Liste d'envies", this.getResources().getDrawable(R.drawable.heart), new EnChantierFragment(), false, menu);


        this.newDevisor(menu);

        MaterialSection sectionBrands = this.newSection(getResources().getString(R.string.all_Brands), this.getResources().getDrawable(R.drawable.ic_action_labels) ,new ListBrandsFragment(), false, menu);
        sectionBrands.getIcon().setColorFilter(getResources().getColor(R.color.SectionTitle), PorterDuff.Mode.MULTIPLY);

        MaterialSection sectionBlogs = this.newSection("Blogs", this.getResources().getDrawable(R.drawable.blog) ,new ListBlogsFragment(), false, menu);
        //MaterialSection sectionMagazines = this.newSection("Magazines", new EnChantierFragment(), false, menu);

        MaterialSection sectionShops = this.newSection("Shop On Line", this.getResources().getDrawable(R.drawable.shopping_bag) ,new ListShopOnLineFragment(), false, menu);

        MaterialSection section7 = this.newSection(getResources().getString(R.string.share), this.getResources().getDrawable(android.R.drawable.ic_menu_share), new ShareProfileFragment(), false, menu);
        section7.getIcon().setColorFilter(getResources().getColor(R.color.SectionTitle), PorterDuff.Mode.MULTIPLY);

        this.newDevisor(menu);

        //this.newLabel("Paramètres", false, menu);

        //MaterialSection section7 = this.newSection("Partager mon profil", this.getResources().getDrawable(R.drawable.ic_action_share), new EnChantierFragment(), false, menu);
        //section7.getIcon().setColorFilter(getResources().getColor(R.color.SectionTitle), PorterDuff.Mode.MULTIPLY);

        MaterialSection section6 = this.newSection(getResources().getString(R.string.settings), this.getResources().getDrawable(R.drawable.ic_action_settings), new SettingsFragment(), false, menu);
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
                    return BitmapFactory.decodeFile(file.getAbsolutePath());
                }
            } catch (Exception e) {
                Log.e(Constants.TAG, "Error converting picture to file : " + e.getMessage());
            }
        }

        return BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
    }
}
