package com.aerolitec.SMXL.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.ui.fragment.QuickSizeSelectGarmentFragmentv2;

public class QuickSizeActivityv2 extends NoDrawerActivity {

    @Override
    public void init(Bundle bundle) {
        super.init(bundle);
        this.newSection("Select", new QuickSizeSelectGarmentFragmentv2(), false, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quicksize, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
