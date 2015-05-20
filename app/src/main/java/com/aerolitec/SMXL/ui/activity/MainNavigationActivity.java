package com.aerolitec.SMXL.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.tools.serverConnexion.PostMainUserFacebookHttpAsyncTask;
import com.aerolitec.SMXL.tools.services.OnProfileSelected;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.adapter.ProfileItem;
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

    private User user;
    private static final int PICKFILE_RESULT_CODE = 1;

    MaterialNavigationDrawer drawer = null;

    AccessTokenTracker mAccessTokenTracker;
    ProfileTracker mProfileTracker;

    @Override
    public int headerType() {
        // set type. you get the available constant from MaterialNavigationDrawer class
        return MaterialNavigationDrawer.DRAWERHEADER_HEADITEMS;
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

        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                Toast.makeText(getBaseContext(), "Déconnexion", Toast.LENGTH_SHORT).show();
                MainUserManager.get().setMainUser(null);

                File file = new File(getFilesDir(),PostMainUserFacebookHttpAsyncTask.MAIN_USER_FILE);
                file.delete();

                SMXL.getUserDBManager().deleteAllUsers();

                finish();
                Intent intent = new Intent(getApplicationContext(), ConnexionActivity.class);
                startActivity(intent);
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
        MaterialSection section5 = this.newSection("Mon Compte", this.getResources().getDrawable(R.drawable.avatar), new ProfilesFragment(), false, menu);
        MaterialSection section1 = this.newSection("Mes Profils", this.getResources().getDrawable(R.drawable.icone_hd), new ProfilesFragment(), false, menu);
        this.newDevisor(menu);

        //this.newLabel("Pratiques", false, menu);
        MaterialSection section2 = this.newSection("Guide des tailles", new SizeGuideFragment(), false, menu);
        MaterialSection section3 = this.newSection("Magasins à proximité", new ProfilesFragment(), false, menu);
        this.newDevisor(menu);

        MaterialSection section4 = this.newSection("Marques", new ProfilesFragment(), false, menu);
        MaterialSection section7 = this.newSection("Blogs", new ProfilesFragment(), false, menu);
        MaterialSection section8 = this.newSection("Magazines", new ProfilesFragment(), false, menu);


        this.newDevisor(menu);

        /*MaterialSection section6 = this.newSection("Magasins à proximité", this.getResources().getDrawable(R.drawable.icone_hd), new ProfilesFragment(), false, menu);
        MaterialSection section7 = this.newSection("Magasins à proximité", this.getResources().getDrawable(R.drawable.icone_hd), new ProfilesFragment(), false, menu);
        MaterialSection section8 = this.newSection("Magasins à proximité", this.getResources().getDrawable(R.drawable.icone_hd), new ProfilesFragment(), false, menu);
        MaterialSection section9 = this.newSection("Magasins à proximité", this.getResources().getDrawable(R.drawable.icone_hd), new ProfilesFragment(), false, menu);*/

        this.newLabel("Paramètres", false, menu);

        MaterialSection section6 = this.newSection("Réglages", this.getResources().getDrawable(R.drawable.ic_action_settings), new SettingsFragment(), false, menu);


        //section1.setFillIconColor(true);

        //section1.setSectionColor(getResources().getColor(R.color.DefaultBackgroundColor));
        //section2.setSectionColor(getResources().getColor(R.color.DefaultBackgroundColor));


        MainUser mainUser = MainUserManager.get().getMainUser();
        if(mainUser==null){

            final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
            final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);
            MaterialHeadItem headItem1 = new MaterialHeadItem(this, "Nom", "Prénom", drawableAppIcon, R.drawable.blur_geom, menu);
            this.addHeadItem(headItem1);

        }
        else{
            final Bitmap bitmap = getBitmapMainUser(mainUser.getAvatar());
            RoundedBitmapDrawable drawableFactory = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            MaterialHeadItem headItem1 = new MaterialHeadItem(this, mainUser.getFirstname()+" "+mainUser.getLastname(), mainUser.getEmail(), drawableFactory, R.drawable.blur_geom, menu);
            this.addHeadItem(headItem1);

        }
        //Log.d("Mainuser Profil", mainUser.toString());

        // use bitmap and make a circle photo

        //Log.d("Main user", mainUser.toString());
        //final Bitmap bitmap = BitmapFactory.decodeFile(mainUser.getAvatar());
        //final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);

        //final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);

        //RoundedBitmapDrawable drawableFactory = RoundedBitmapDrawableFactory.create(getResources(), mainUser.getAvatar());

        // create Head Item
        //MaterialHeadItem headItem1 = new MaterialHeadItem(this, "F HeadItem", "F Subtitle", drawableAppIcon, R.drawable.blur_geom, menu);
        //MaterialHeadItem headItem1 = new MaterialHeadItem(this, mainUser.getFirstname()+" "+mainUser.getLastname(), mainUser.getEmail(), drawableAppIcon, R.drawable.blur_geom, menu);
        //headItem1.setLoadFragmentOnChanged(true);


        // add head Item (menu will be loaded automatically)

        //this.addHeadItem(headItem);
        //this.addHeadItem(headItem1);
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
        final String urlImageFinal = urlImage;

        if(urlImage!=null) {
            if (urlImage.contains("https")){

                /*AsyncTask asyncTask = new AsyncTask() {

                    Bitmap bitmap;

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        try {
                            URL url = new URL(urlImageFinal);
                            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return bitmap;
                    }
                };
                asyncTask.execute();*/
                Log.d("HTTPS WARNING ProfActi", MainUserManager.get().getMainUser().getAvatar().toString());
            }
            else{
                try {
                    File file = new File(urlImage);

                    if (file.exists()) {
                        final BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        Log.d("Bitmap WARNING ProfActi", MainUserManager.get().getMainUser().getAvatar().toString());
                        Log.d("Bitmap WARNING ProfActi", file.getAbsolutePath());

                        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                    }
                } catch (Exception e) {
                    Log.e(Constants.TAG, "Error converting picture to file : " + e.getMessage());
                }
            }
        }

        return BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
    }

}
