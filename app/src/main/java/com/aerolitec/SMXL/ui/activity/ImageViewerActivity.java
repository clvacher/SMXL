package com.aerolitec.SMXL.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.ui.fragment.ImageViewerFragment;

public class ImageViewerActivity extends NoDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init(Bundle bundle) {
        super.init(bundle);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Uri uri = extras.getParcelable("image_URI");
            ImageViewerFragment imageViewerFragment= ImageViewerFragment.newInstance(uri);
            this.newSection(uri.getLastPathSegment(), imageViewerFragment, false, menu);
        }
        else{
            Log.d(Constants.TAG,"No Picture To Show");
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_viewer, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDrawerBlocked();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
