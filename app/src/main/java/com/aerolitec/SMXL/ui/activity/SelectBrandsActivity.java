package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.ui.adapter.FavoriteCheckableBrandAdapter;
import com.aerolitec.SMXL.ui.customLayout.CheckableBrandLayout;


public class SelectBrandsActivity extends Activity {

    private static User user;
    private ArrayList<Brand> brands;
    private GridView gridViewBrands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        brands=new ArrayList<>();

        user = UserManager.get().getUser();
        if(user == null){
            Log.d("TestOnCreate", "user null");
        }

        CheckableBrandLayout.selectedBrands.clear();
        CheckableBrandLayout.selectedBrands = user.getBrands();

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

        //gridViewBrands.setMultiChoiceModeListener(new MultiChoiceModeListener(gridViewBrands));

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("coucou je resume", "oui oui oui");
        Log.d("adapter gridview", gridViewBrands.getAdapter().toString());

        ArrayList<Brand> brandUSer = (ArrayList)user.getBrands().clone();

        for(Brand b : brandUSer) {
            ((FavoriteCheckableBrandAdapter) gridViewBrands.getAdapter()).checkBrand(b);
        }

    }

    public void onClickAddBrandsUser(View view){

        Log.d("Selected brands", CheckableBrandLayout.selectedBrands.toString());

        user.setBrands(CheckableBrandLayout.selectedBrands);

        for(Brand b : CheckableBrandLayout.selectedBrands){
            if (!SMXL.getDataBase().alreadyExistUserBrand(user, b)) {
                SMXL.getDataBase().addUserBrand(user, b);
            }
        }
        Log.d("user onClickAddBrandUse", UserManager.get().getUser().toString());
        finish();
    }


    private void updateUI (){
        gridViewBrands.removeAllViews();

        for (int i = 0; i < brands.size(); i++) {

            ((FavoriteCheckableBrandAdapter)gridViewBrands.getAdapter()).getView(i, new CheckableBrandLayout(getApplicationContext(), brands.get(i)), gridViewBrands);

        }
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
