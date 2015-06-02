package com.aerolitec.SMXL.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by Jerome on 01/06/2015.
 */
public class NoDrawerActivity extends SuperNavigationActivity {

    @Override
    public void init(Bundle bundle) {

        drawer = this;
        drawer.setCustomMenu(menu);
    }

    @Override
    public int headerType() {
        return MaterialNavigationDrawer.DRAWERHEADER_NO_HEADER;
    }

    protected void setDrawerBlocked(){
        setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getActionBarToggle().onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getActionBarToggle().setDrawerIndicatorEnabled(false);
        getActionBarToggle().syncState();
    }
}
