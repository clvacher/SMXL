package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
    private ArrayList<Brand> brandsSelected;
    private ArrayList<String> brandsCategory;

    private GridView gridViewBrands;
    private Spinner spinnerBrandsCategory;
    private FavoriteCheckableBrandAdapter gridViewBrandsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        brands=new ArrayList<>();
        brandsSelected=new ArrayList<>();

        brandsCategory=(SMXL.getBrandDBManager().getAllBrandCategory());
        brandsCategory.add(0,getResources().getString(R.string.select_category));


        user = UserManager.get().getUser();
        if(user == null){
            Log.d("TestOnCreate", "user null");
        }

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        setContentView(R.layout.activity_select_brands);

        getActionBar().setTitle(" ");
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(true);

        gridViewBrands = (GridView) this.findViewById(R.id.gridViewBrands);
        spinnerBrandsCategory = (Spinner) this.findViewById(R.id.spinnerBrandsCategory);
        brands = SMXL.getBrandDBManager().getAllBrands();

        final ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_spinner_brand_category, brandsCategory);
        spinnerBrandsCategory.setAdapter(adapterSpinner);

        gridViewBrandsAdapter = new FavoriteCheckableBrandAdapter(this, R.layout.item_favorite_brand, brands);
        gridViewBrands.setAdapter(gridViewBrandsAdapter);

        gridViewBrands.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);

        brandsSelected.addAll(user.getBrands());

        gridViewBrands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long mylng) {
                Brand selectedBrand = (Brand) gridViewBrandsAdapter.getItem(position);
                brandsSelected.add(selectedBrand);

                if(user.getBrands().contains(selectedBrand)){
                    user.getBrands().remove(selectedBrand);
                    SMXL.getUserBrandDBManager().deleteUserBrand(user, selectedBrand);
                }
                else{
                    user.getBrands().add(selectedBrand);
                    SMXL.getUserBrandDBManager().addUserBrand(user, selectedBrand);
                }
            }
        });

        spinnerBrandsCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if(position>0){
                    brands = SMXL.getBrandDBManager().getBrandsByBrandCategory(SMXL.getBrandDBManager().getAllBrandCategory().get(position-1));//-1 car on a rajouté l'item d'en tete
                }
                else{
                    brands = SMXL.getBrandDBManager().getAllBrands();
                }

                gridViewBrandsAdapter.clear();
                gridViewBrandsAdapter.addAll(brands);
                gridViewBrandsAdapter.notifyDataSetChanged();

                gridViewBrands.clearChoices();
                for(Brand b : user.getBrands()) {
                    gridViewBrands.setItemChecked(brands.indexOf(b), true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //gridViewBrands.setMultiChoiceModeListener(new MultiChoiceModeListener(gridViewBrands));

    }



    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<Brand> brandUser = user.getBrands();
        if(gridViewBrandsAdapter==null){
            gridViewBrandsAdapter = new FavoriteCheckableBrandAdapter(this, R.layout.item_favorite_brand, brands);
            gridViewBrands.setAdapter(gridViewBrandsAdapter);
            spinnerBrandsCategory.setSelection(0);
        }

        //Log.d("isempty", Boolean.toString(gridViewBrandsAdapter.isEmpty()));
        //Log.d("viewtypecount", Integer.toString(gridViewBrandsAdapter.getViewTypeCount()));

        for(Brand b : user.getBrands()) {
            gridViewBrands.setItemChecked(brands.indexOf(b), true);
        }
    }



    public void onClickAddBrandsUser(View view){
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
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
