package com.aerolitec.SMXL.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.ui.fragment.BrowserFragment;

import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;


public class BrowserActivity extends NoDrawerActivity {

    BrowserFragment browserFragment;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void init(Bundle bundle) {
        super.init(bundle);

        browserFragment = new BrowserFragment();
        MaterialSection section1 = this.newSection("Browser", browserFragment, false, menu);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setDrawerBlocked();
    }

    @Override
    public void onBackPressed() {
        if (browserFragment.getWebView().copyBackForwardList().getCurrentIndex() > 0) {
            browserFragment.getWebView().goBack();
        }
        else {
            // Your exit alert code, or alternatively line below to finish
            super.onBackPressed(); // finishes activity
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            browserFragment.getWebView().reload();
        }

        if(id == android.R.id.home){
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
