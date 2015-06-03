package com.aerolitec.SMXL.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.ui.fragment.AddGarmentFragment;

import de.madcyph3r.materialnavigationdrawer.menu.item.MaterialSection;

/**
 * Created by Clement on 5/4/2015.
 *
 */
public class AddGarmentActivity extends NoDrawerActivity {

    private AddGarmentFragment addGarmentFragment;

    private Boolean validation=false;
    private Boolean update=false;
    private Menu menuBar;

    public User getUser() {return addGarmentFragment.getUser();}

    @Override
    public void init(Bundle bundle) {
        super.init(bundle);
        addGarmentFragment = new AddGarmentFragment();
        MaterialSection section1 = this.newSection(getResources().getString(R.string.edit_garment), addGarmentFragment, false, menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDrawerBlocked();
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        Log.d("PREPARE","PREPARE");
        (menu.findItem(R.id.validate)).setVisible(validation);
        (menu.findItem(R.id.update)).setVisible(update);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("CREATE","CREATE");
        getMenuInflater().inflate(R.menu.add_garment, menu);
        this.menuBar=menu;
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.validate:
                addGarmentFragment.saveGarment();
                finish();
                break;
            case R.id.update:
                addGarmentFragment.updateGarment();
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }





    public void setValidation(Boolean b){
        validation = b;
        onPrepareOptionsMenu(menuBar);
    }
    public void setUpdate(Boolean b){
        update = b;
        onPrepareOptionsMenu(menuBar);
    }

    public void setValueUpdate(Boolean b){
        update = b;
    }


    public AddGarmentFragment getAddGarmentFragment() {
        return addGarmentFragment;
    }
}
