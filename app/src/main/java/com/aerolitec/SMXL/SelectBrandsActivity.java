package com.aerolitec.SMXL;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aerolitec.SMXL.model.Brands;
import com.aerolitec.SMXL.model.BrandsSizeGuide;
import com.aerolitec.SMXL.ui.SMXL;

import java.util.ArrayList;


public class SelectBrandsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_brands);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);

        GridView gridViewBrands;
        ArrayList<String> brandstring = new ArrayList<>();

        ArrayList<Brands> brands = SMXL.get().getDataBase().getAllBrands();

        for(Brands b : brands){
            brandstring.add(b.getBrand());
        }

        gridViewBrands = (GridView) this.findViewById(R.id.gridViewBrands);

        ArrayAdapter<String> adapterBrands = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, brandstring);
        gridViewBrands.setAdapter(adapterBrands);

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
