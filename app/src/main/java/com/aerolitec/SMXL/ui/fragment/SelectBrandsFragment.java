package com.aerolitec.SMXL.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.adapter.FavoriteCheckableBrandAdapter;

import java.util.ArrayList;


public class SelectBrandsFragment extends Fragment {

    private static User user;
    private ArrayList<Brand> brands;
    private ArrayList<Brand> brandsSelected;
    private ArrayList<String> brandsCategory;

    private GridView gridViewBrands;
    private Spinner spinnerBrandsCategory;
    private FavoriteCheckableBrandAdapter gridViewBrandsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        user = UserManager.get().getUser();

        if (user == null) {
            getActivity().finish();
            Log.d("Warning","user null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_brands, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        brands=new ArrayList<>();
        brandsSelected=new ArrayList<>();

        brandsCategory=(SMXL.getBrandDBManager().getAllBrandCategory());
        brandsCategory.add(0, getResources().getString(R.string.select_category));

        gridViewBrands = (GridView) view.findViewById(R.id.gridViewBrands);
        spinnerBrandsCategory = (Spinner) view.findViewById(R.id.spinnerBrandsCategory);
        brands = SMXL.getBrandDBManager().getAllBrands();

        final ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.item_spinner_brand_category, brandsCategory);
        spinnerBrandsCategory.setAdapter(adapterSpinner);

        gridViewBrandsAdapter = new FavoriteCheckableBrandAdapter(getActivity(), R.layout.item_favorite_brand, brands);
        gridViewBrands.setAdapter(gridViewBrandsAdapter);

        gridViewBrands.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);

        brandsSelected.addAll(user.getBrands());

        gridViewBrands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long mylng) {
                Brand selectedBrand = (Brand) gridViewBrandsAdapter.getItem(position);
                brandsSelected.add(selectedBrand);

                if (user.getBrands().contains(selectedBrand)) {
                    user.getBrands().remove(selectedBrand);
                    SMXL.getUserBrandDBManager().deleteUserBrand(user, selectedBrand);
                } else {
                    user.getBrands().add(selectedBrand);
                    SMXL.getUserBrandDBManager().addUserBrand(user, selectedBrand);
                }
            }
        });

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
                for (Brand b : user.getBrands()) {
                    gridViewBrands.setItemChecked(brands.indexOf(b), true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        (view.findViewById(R.id.buttonValidationBrands)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<Brand> brandUser = user.getBrands();
        if(gridViewBrandsAdapter==null){
            gridViewBrandsAdapter = new FavoriteCheckableBrandAdapter(getActivity(), R.layout.item_favorite_brand, brands);
            gridViewBrands.setAdapter(gridViewBrandsAdapter);
            spinnerBrandsCategory.setSelection(0);
        }

        //Log.d("isempty", Boolean.toString(gridViewBrandsAdapter.isEmpty()));
        //Log.d("viewtypecount", Integer.toString(gridViewBrandsAdapter.getViewTypeCount()));

        for(Brand b : user.getBrands()) {
            gridViewBrands.setItemChecked(brands.indexOf(b), true);
        }
    }


    public void save(){
        getActivity().finish();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.create_profil, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.validate){
            save();
        }
        if (id == (android.R.id.home)){
            getActivity().finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
