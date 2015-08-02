package com.aerolitec.SMXL.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.fragment.ShareProfileFragment;
import com.aerolitec.SMXL.ui.fragment.TabsProfileDetailFragment;

import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

/**
 * Created by NelsonGay on 30/07/2015.
 */
public class SharingActivity extends NoDrawerActivity{
    public void init(Bundle bundle) {
        super.init(bundle);
        this.newSection("Share"
                    , new ShareProfileFragment(), false, menu);

    }

    @Override
    public int headerType() {
        return super.headerType();
    }
    @Override
    protected void onResume() {
        super.onResume();
        setDrawerBlocked();
    }

    @Override
    public void onBackPressed() {
        restoreDefaultTitleCurrentSection();
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
