package com.aerolitec.SMXL.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.tools.services.OnProfileSelected;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.adapter.ProfileItem;
import com.aerolitec.SMXL.ui.fragment.ProfilesFragment;
import com.aerolitec.SMXL.ui.fragment.SettingsFragment;
import com.aerolitec.SMXL.ui.fragment.SizeGuideFragment;

import java.io.File;
import java.io.IOException;
import java.net.URL;

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


    @Override
    public int headerType() {
        // set type. you get the available constant from MaterialNavigationDrawer class
        return MaterialNavigationDrawer.DRAWERHEADER_HEADITEMS;
    }

    // called from onCreate(), make your view init here or in your fragment.
    @Override
    public void init(Bundle savedInstanceState) {

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

        MaterialSection section4 = this.newSection("Réglages", this.getResources().getDrawable(R.drawable.ic_action_settings), new SettingsFragment(), true, menu);


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

                AsyncTask asyncTask = new AsyncTask() {

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
                asyncTask.execute();
            }
            else{
                try {
                    File file = new File(urlImage);

                    if (file.exists()) {
                        final BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
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
