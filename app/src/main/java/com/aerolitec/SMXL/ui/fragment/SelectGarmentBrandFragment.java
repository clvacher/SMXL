package com.aerolitec.SMXL.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.AddGarmentActivity;
import com.aerolitec.SMXL.ui.adapter.FavoriteCheckableBrandAdapter;

import java.util.ArrayList;

/**
 * Created by Clement on 5/5/2015.
 */
public class SelectGarmentBrandFragment extends Fragment {

    private AddGarmentActivity activity;

    private GridView gvBrands;
    private Spinner spinnerBrandsCategory;
    private View favoriteLeft,favoriteRight,allRight,allLeft;

    private ListView listViewBrands;
    private TextView textGarment;
    private ArrayList<String> brandItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_garment_brand, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity=(AddGarmentActivity)getActivity();

        gvBrands = (GridView) view.findViewById(R.id.gridViewBrands);
        spinnerBrandsCategory = (Spinner) view.findViewById(R.id.spinnerBrandCategory);

        RelativeLayout rlFavoriteBrands, rlBrands;
        rlBrands =(RelativeLayout) view.findViewById(R.id.allBrandsLayout);
        rlFavoriteBrands = (RelativeLayout) view.findViewById(R.id.favoriteBrandLayout);


        favoriteLeft=view.findViewById(R.id.selectedFavoriteLeftArrow);
        favoriteRight=view.findViewById(R.id.selectedFavoriteRightArrow);
        allLeft=view.findViewById(R.id.selectedAllLeftArrow);
        allRight=view.findViewById(R.id.selectedAllRightArrow);

        rlBrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteRight.setVisibility(View.GONE);
                favoriteLeft.setVisibility(View.GONE);
                allLeft.setVisibility(View.VISIBLE);
                allRight.setVisibility(View.VISIBLE);
                fillGridView(gvBrands, getAllbrands());
                fillSpinner(spinnerBrandsCategory, getSpinnerCategories());
                spinnerBrandsCategory.setVisibility(View.VISIBLE);
            }
        });

        rlFavoriteBrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteRight.setVisibility(View.VISIBLE);
                favoriteLeft.setVisibility(View.VISIBLE);
                allLeft.setVisibility(View.GONE);
                allRight.setVisibility(View.GONE);
                fillGridView(gvBrands, getFavoriteBrands());
                spinnerBrandsCategory.setVisibility(View.GONE);
            }
        });

        gvBrands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                activity.setSelectedBrand((Brand) adapterView.getItemAtPosition(i));
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new SelectGarmentSummaryFragment(), "summary")
                        .addToBackStack(null)
                        .commit();
            }
        });

        spinnerBrandsCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                ArrayList<Brand> brands;
                if (position > 0) {
                    brands = SMXL.getBrandDBManager().getBrandsByBrandCategory(SMXL.getBrandDBManager().getAllBrandCategory().get(position - 1));//-1 car on a rajouté l'item d'en tete
                } else {
                    brands = SMXL.getBrandDBManager().getAllBrands();
                }
                fillGridView(gvBrands, brands);
                gvBrands.clearChoices();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fillGridView(gvBrands, getFavoriteBrands());
        fillSpinner(spinnerBrandsCategory,getSpinnerCategories());
    }

    private void fillGridView(GridView gv, ArrayList<Brand> alBrands){
        gv.setAdapter(new FavoriteCheckableBrandAdapter(getActivity(), R.layout.item_favorite_brand, alBrands));
        gv.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
    }

    private void fillSpinner(Spinner s,ArrayList<String> categories){
        s.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.item_spinner_brand_category,getSpinnerCategories()));
    }

    private ArrayList<Brand> getFavoriteBrands(){
        return SMXL.getUserBrandDBManager().getAllUserBrands(((AddGarmentActivity)getActivity()).getUser());
    }

    private ArrayList<Brand> getAllbrands(){
        return SMXL.getBrandDBManager().getAllBrands();
    }

    private ArrayList<String> getSpinnerCategories(){
        ArrayList<String> brandsCategory=SMXL.getBrandDBManager().getAllBrandCategory();
        brandsCategory.add(0,getResources().getString(R.string.select_category));
        return brandsCategory;
    }
}
