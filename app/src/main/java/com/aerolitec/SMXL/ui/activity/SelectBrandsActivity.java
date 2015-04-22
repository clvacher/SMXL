package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;

import java.util.ArrayList;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.ui.adapter.FavoriteCheckableBrandAdapter;
import com.aerolitec.SMXL.ui.customLayout.CheckableBrandLayout;


public class SelectBrandsActivity extends Activity {

    private static User user;
    private ArrayList<Brand> brands;
    private ArrayList<Brand> selectedBrands;
    private ArrayList<CheckableBrandLayout> checkableBrand;
    GridView gridViewBrands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        brands=new ArrayList<>();
        selectedBrands=new ArrayList<>();
        CheckableBrandLayout.selectedBrands.clear();


        user = UserManager.get().getUser();
        if(user == null){
            Log.d("TestOnCreate", "user null");
        }

        selectedBrands=user.getBrands();
        CheckableBrandLayout.selectedBrands = selectedBrands;

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        setContentView(R.layout.activity_select_brands);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);

        ArrayList<Brand> brands = SMXL.get().getDataBase().getAllBrands();

        gridViewBrands = (GridView) this.findViewById(R.id.gridViewBrands);



        gridViewBrands.setAdapter(new FavoriteCheckableBrandAdapter(brands));
        gridViewBrands.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);

        for(Brand b : selectedBrands){
            //((FavoriteCheckableBrandAdapter)gridViewBrands.getAdapter()).getCheckableLayoutByBrand(b).setChecked(true);
        }


        //gridViewBrands.setMultiChoiceModeListener(new MultiChoiceModeListener(gridViewBrands));



    }

    public void onClickAddBrandsUser(View view){
        selectedBrands = CheckableBrandLayout.selectedBrands;

        Log.d("Selected brands", selectedBrands.toString());

        //user.setBrands(selectedBrands);

        for(Brand b : selectedBrands){
            SMXL.getDataBase().addUserBrand(user, b);
        }
        //Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
        //startActivity(intent);
        Log.d("user onClickAddBrandUse", UserManager.get().getUser().toString());
        finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_brands, menu);
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
