package com.aerolitec.SMXL.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.BrandSizeGuideMeasuresRow;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.ui.adapter.BrandSizeGuideMeasureRowAdapter;
import com.aerolitec.SMXL.ui.fragment.AddGarmentFragment;
import com.aerolitec.SMXL.ui.fragment.AddGarmentWishListFragment;
import com.aerolitec.SMXL.ui.fragment.SelectSizeFragment;
import com.aerolitec.SMXL.ui.fragment.SelectSizeFragment.OnFragmentInteractionListener;

import java.io.File;

/**
 * Created by Nelson on 10/08/2015.
 */
public class AddWishListActivity extends NoDrawerActivity {

    private AddGarmentWishListFragment wishListFragment;

    private Boolean validation = false;
    private Boolean update = false;
    private Menu menuBar;

    @Override
    public void init(Bundle bundle) {
        super.init(bundle);
        wishListFragment = new AddGarmentWishListFragment();
        wishListFragment.setArguments(getIntent().getExtras());
        this.newSection("Ajout a la WishList", wishListFragment, false, menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDrawerBlocked();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        (menu.findItem(R.id.validate)).setVisible(validation);
        (menu.findItem(R.id.update)).setVisible(update);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_garment, menu);
        this.menuBar = menu;
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.validate:
                wishListFragment.saveToWishList();
                finish();
                break;
            case R.id.update:
                wishListFragment.updateWishList();
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setValidation(Boolean b) {
        validation = b;
        onPrepareOptionsMenu(menuBar);
    }

    public void setUpdate(Boolean b) {
        update = b;
        onPrepareOptionsMenu(menuBar);
    }

    @Override
    protected void onDestroy() {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root, "SMXL");
        myDir.mkdirs();
        File tmpFile = new File(myDir,"temp.png");
        if(tmpFile.exists()){
            tmpFile.delete();
        }
        super.onDestroy();
    }

    public void setValueUpdate(Boolean b) {
        update = b;
    }
    public void setValueValidation(Boolean b) {
        validation = b;
    }
}