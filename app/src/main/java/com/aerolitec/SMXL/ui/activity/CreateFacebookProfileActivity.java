package com.aerolitec.SMXL.ui.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.ui.fragment.SelectBrandsCreateProfileFragment;
import com.aerolitec.SMXL.ui.fragment.SelectBrandsFragment;

public class CreateFacebookProfileActivity extends NoDrawerActivity {

    @Override
    public void init(Bundle bundle) {
        super.init(bundle);
        this.newSection(" ", new SelectBrandsCreateProfileFragment(), false, menu);
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

}