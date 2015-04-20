package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.aerolitec.SMXL.model.Brands;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;

import java.util.ArrayList;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.ui.adapter.FavoriteBrandAdapter;
import com.aerolitec.SMXL.ui.customLayout.CheckableBrandLayout;


public class SelectBrandsActivity extends Activity {

    private static User user;
    private ArrayList<Brands> brands;
    private ArrayList<Brands> selectedBrands;
    GridView gridViewBrands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        brands=new ArrayList<>();
        selectedBrands=new ArrayList<>();


        user = UserManager.get().getUser();
        if(user == null){
            Log.d("TestOnCreate", "user null");
        }



        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        setContentView(R.layout.activity_select_brands);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);

        ArrayList<Brands> brands = SMXL.get().getDataBase().getAllBrands();

        gridViewBrands = (GridView) this.findViewById(R.id.gridViewBrands);

        gridViewBrands.setAdapter(new FavoriteBrandAdapter(brands));
        gridViewBrands.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);


        //gridViewBrands.setMultiChoiceModeListener(new MultiChoiceModeListener(gridViewBrands));



    }

    public void onClickAddBrandsUser(View view){
        selectedBrands = CheckableBrandLayout.selectedBrands;
        user.addBrands(selectedBrands);
        //Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
        //startActivity(intent);
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
