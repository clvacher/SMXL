package com.aerolitec.SMXL.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.SuperNavigationActivity;
import com.aerolitec.SMXL.ui.adapter.FavoriteCheckableBrandAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuickSizeSelectBrandFragment extends Fragment implements SuperNavigationActivity.OnBackPressedListener{
    private QuickSizeFragment quickSizeFragment;

    private GridView gvBrands;
    private Spinner spinnerBrandsCategory;
    private View favoriteLeft,favoriteRight,allRight,allLeft;

    private RelativeLayout rlFavoriteBrands, rlBrands;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((SuperNavigationActivity) getActivity()).setOnBackPressedListener(this);
        return inflater.inflate(R.layout.fragment_add_garment_brand, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        quickSizeFragment = (QuickSizeFragment)getParentFragment();

        gvBrands = (GridView) view.findViewById(R.id.gridViewBrands);
        spinnerBrandsCategory = (Spinner) view.findViewById(R.id.spinnerBrandCategory);

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
                spinnerBrandsCategory.setVisibility(View.GONE);
                fillGridView(gvBrands, getFavoriteBrands());
            }
        });

        gvBrands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                quickSizeFragment.setSelectedBrand((Brand) adapterView.getItemAtPosition(i));
                quickSizeFragment.getChildFragmentManager().beginTransaction()
                        .replace(R.id.containerQuickSizeFragment, new QuickSizeSummaryFragment(), "summary")
                        .addToBackStack(null)
                        .commit();
            }
        });

        spinnerBrandsCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                ArrayList<Brand> brands;
                if (position > 0) {
                    brands = getAllBrandsByCategory(position);
                } else {
                    brands = getAllbrands();
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
        fillSpinner(spinnerBrandsCategory, getSpinnerCategories());
    }

    private boolean fillGridView(GridView gv, ArrayList<Brand> alBrands){
        if(alBrands.size()==0){
            rlBrands.callOnClick();
            rlFavoriteBrands.setVisibility(View.GONE);
            return false;
        }
        gv.setAdapter(new FavoriteCheckableBrandAdapter(getActivity(), R.layout.item_favorite_brand, alBrands));
        gv.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        return true;
    }

    private void fillSpinner(Spinner s,ArrayList<String> categories){
        s.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_spinner_brand_category, getSpinnerCategories()));
    }

    private ArrayList<Brand> getFavoriteBrands(){
        return SMXL.getUserBrandDBManager().getUserBrandByGarment(UserManager.get().getUser(), quickSizeFragment.getSelectedGarmentType());
    }

    private ArrayList<Brand> getAllbrands(){
        return SMXL.getBrandSizeGuideDBManager().getAllBrandsByGarment(quickSizeFragment.getSelectedGarmentType());
    }

    private ArrayList<Brand> getAllBrandsByCategory(int position){
        ArrayList<Brand> brands = SMXL.getBrandDBManager().getBrandsByBrandCategory(SMXL.getBrandDBManager().getAllBrandCategory().get(position - 1));//-1 car on a rajoute l'item d'en tete
        ArrayList<Brand> allBrands = getAllbrands();
        brands.retainAll(allBrands);
        return brands;
    }

    private ArrayList<String> getSpinnerCategories(){
        ArrayList<String> brandsCategory=SMXL.getBrandDBManager().getAllBrandCategory();
        brandsCategory.add(0,getResources().getString(R.string.select_category));
        return brandsCategory;
    }
    @Override
    public void backPressed() {
        //TODO De la merde a changer
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentManager currentFragmentManager;
        Fragment fragment = fragmentManager.findFragmentById(R.id.frame_container);

        // Pour le quicksize dans l'acceuil
        if( fragment instanceof TabsFragmentHomeDressingQuicksize) {
            Fragment currentFragment = fragment.getChildFragmentManager().findFragmentByTag("quicksize");
            currentFragmentManager = currentFragment.getFragmentManager();
        }
        // Pour le quicksize dans l'onglet
        else {
            currentFragmentManager = getActivity().getSupportFragmentManager();
        }
        FragmentTransaction fragmentTransaction = currentFragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_container, new QuickSizeFragment(),"quicksize");
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        ((SuperNavigationActivity) getActivity()).setOnBackPressedListener(null);
        super.onDestroyView();
    }
}
