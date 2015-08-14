package com.aerolitec.SMXL.ui.activity;

import android.os.Bundle;
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
public class AddWishListActivity extends NoDrawerActivity implements OnFragmentInteractionListener {

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
                break;
            case R.id.update:
                wishListFragment.updateWishList();
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

    public void setValueUpdate(Boolean b) {
        update = b;
    }
    public void setValueValidation(Boolean b) {
        validation = b;
    }
    @Override
    public void onFragmentInteraction(BrandSizeGuideMeasuresRow selectedRow,String selectedColumn) {
        wishListFragment.setSize(selectedRow,selectedColumn);
    }
}