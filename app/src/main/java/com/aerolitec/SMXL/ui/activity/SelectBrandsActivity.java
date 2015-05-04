package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    private ArrayList<Brand> userBrands;
    private ArrayList<Brand> brands;
    private ArrayList<Brand> brandsSelected;

    private GridView gridViewBrands;
    private FavoriteCheckableBrandAdapter gridViewBrandsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        brands=new ArrayList<>();
        brandsSelected=new ArrayList<>();

        user = UserManager.get().getUser();
        if(user == null){
            Log.d("TestOnCreate", "user null");
        }

        userBrands = user.getBrands();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        setContentView(R.layout.activity_select_brands);

        getActionBar().setTitle(" ");
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(true);

        gridViewBrands = (GridView) this.findViewById(R.id.gridViewBrands);

        brands = SMXL.getBrandDBManager().getAllBrands();

        gridViewBrandsAdapter = new FavoriteCheckableBrandAdapter(this, R.layout.item_favorite_brand, brands);
        gridViewBrands.setAdapter(gridViewBrandsAdapter);

        gridViewBrands.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);

        brandsSelected.addAll(userBrands);

        gridViewBrands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long mylng) {
                Brand selectedBrand = (Brand) gridViewBrandsAdapter.getItem(position);

                brandsSelected.add(selectedBrand);

                /*
                if(brandsSelected.contains(selectedBrand)){
                    brandsSelected.remove(selectedBrand);
                }
                else{
                    brandsSelected.add(selectedBrand);
                }
                */

                //Log.d("Brand select" , selectedBrand.toString());
                //Log.d("all brand select", brandsSelected.toString());
            }
        });

        //gridViewBrands.setMultiChoiceModeListener(new MultiChoiceModeListener(gridViewBrands));

    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Brand> brandUser = user.getBrands();

        gridViewBrandsAdapter = new FavoriteCheckableBrandAdapter(this, R.layout.item_favorite_brand, brands);
        gridViewBrands.setAdapter(gridViewBrandsAdapter);

        //Log.d("isempty", Boolean.toString(gridViewBrandsAdapter.isEmpty()));
        //Log.d("viewtypecount", Integer.toString(gridViewBrandsAdapter.getViewTypeCount()));

        for(Brand b : brandUser) {
            gridViewBrands.setItemChecked(brands.indexOf(b), true);
        }



    }



    public void onClickAddBrandsUser(View view){

        SMXL.getUserBrandDBManager().deleteUserBrand(user);

        for(Brand b : brandsSelected){
                SMXL.getUserBrandDBManager().addUserBrand(user, b);
        }

        user.setBrands(brandsSelected);

        //Log.d("onclick selectedbrands", brandsSelected.toString());

        //Log.d("user onClickAddBrandUse", UserManager.get().getUser().toString());

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

        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
