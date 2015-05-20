package com.aerolitec.SMXL.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.adapter.FavoriteCheckableBrandAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListBrandsFragment extends Fragment {


    public ListBrandsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_brands, container, false);
    }


    private ArrayList<Brand> brands;
    private ArrayList<String> brandsCategory;

    private GridView gridViewBrands;
    private Spinner spinnerBrandsCategory;
    private FavoriteCheckableBrandAdapter gridViewBrandsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        brands=new ArrayList<>();

        brandsCategory=(SMXL.getBrandDBManager().getAllBrandCategory());
        brandsCategory.add(0, getResources().getString(R.string.select_category));

        gridViewBrands = (GridView) view.findViewById(R.id.gridViewBrands);
        spinnerBrandsCategory = (Spinner) view.findViewById(R.id.spinnerBrandsCategory);
        brands = SMXL.getBrandDBManager().getAllBrands();

        final ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_spinner_brand_category, brandsCategory);
        spinnerBrandsCategory.setAdapter(adapterSpinner);

        gridViewBrandsAdapter = new FavoriteCheckableBrandAdapter(getActivity(), R.layout.item_favorite_brand, brands);
        gridViewBrands.setAdapter(gridViewBrandsAdapter);

        //gridViewBrands.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);

        gridViewBrands.setChoiceMode(GridView.CHOICE_MODE_SINGLE);

        spinnerBrandsCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (position > 0) {
                    brands = SMXL.getBrandDBManager().getBrandsByBrandCategory(SMXL.getBrandDBManager().getAllBrandCategory().get(position - 1));//-1 car on a rajoute l'item d'en tete
                } else {
                    brands = SMXL.getBrandDBManager().getAllBrands();
                }

                gridViewBrandsAdapter.clear();
                gridViewBrandsAdapter.addAll(brands);
                gridViewBrandsAdapter.notifyDataSetChanged();

                gridViewBrands.clearChoices();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        if(gridViewBrandsAdapter==null){
            gridViewBrandsAdapter = new FavoriteCheckableBrandAdapter(getActivity(), R.layout.item_favorite_brand, brands);
            gridViewBrands.setAdapter(gridViewBrandsAdapter);
            spinnerBrandsCategory.setSelection(0);
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getActivity().getMenuInflater().inflate(R.menu.create_profil, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
