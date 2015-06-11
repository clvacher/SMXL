package com.aerolitec.SMXL.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.aerolitec.SMXL.R;

public class QuickSizeActivity extends NoDrawerActivity {

    //private QuickSizeFragment quickSizeFragment;


    /*public User getUser() {return quickSizeFragment.getUser();}*/

    @Override
    public void init(Bundle bundle) {
        super.init(bundle);
        //quickSizeFragment = new QuickSizeFragment();
        //this.newSection("QuickSize", quickSizeFragment, false, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quicksize, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        setDrawerBlocked();
        super.onResume();
    }

    /*public QuickSizeFragment getQuickSizeFragment(){
        return quickSizeFragment;
    }*/
}
