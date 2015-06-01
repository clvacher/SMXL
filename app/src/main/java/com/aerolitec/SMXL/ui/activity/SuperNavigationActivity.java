package com.aerolitec.SMXL.ui.activity;

import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;

/**
 * Created by Jerome on 01/06/2015.
 */
public abstract class SuperNavigationActivity extends MaterialNavigationDrawer {

    protected MaterialNavigationDrawer drawer = null;
    protected MaterialMenu menu = new MaterialMenu();

    public void updateHamburger(){
        if(getSupportFragmentManager().getBackStackEntryCount()==0){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getActionBarToggle().setDrawerIndicatorEnabled(true);
        }
    }

    public void updateTitle(int idTitle){
        getSupportActionBar().setTitle(idTitle);
    }
    public void updateTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    public void setBarAsNextFragment(){
        getActionBarToggle().setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void restoreDefaultTitleCurrentSection(){
        updateTitle(getCurrentSection().getTitle());
    }

    public void disableIndicator(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

}
