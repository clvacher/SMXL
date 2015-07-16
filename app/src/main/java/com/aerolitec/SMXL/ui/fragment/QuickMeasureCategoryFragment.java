package com.aerolitec.SMXL.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.BrandSizeGuideMeasuresRow;
import com.aerolitec.SMXL.model.CategoryGarment;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.SuperNavigationActivity;
import com.aerolitec.SMXL.ui.adapter.BrandAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class QuickMeasureCategoryFragment extends Fragment {

    private SuperNavigationActivity superNavigationActivity;
    private User user;
    private CategoryGarment categoryGarment;
    private List<GarmentType> allGarmentType;
    Spinner spinnerBrand;
    Spinner spinnerCountry;
    Spinner spinnerSize ;
    List<String> spinnerCountryChoices;
    List<String> spinnerSizeChoices;

    public static QuickMeasureCategoryFragment newInstance(CategoryGarment categoryGarment) {
        QuickMeasureCategoryFragment fragment = new QuickMeasureCategoryFragment();
        Bundle args = new Bundle();
        args.putSerializable("categoryGarment",categoryGarment);
        fragment.setArguments(args);
        return fragment;
    }

    public QuickMeasureCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        superNavigationActivity = (SuperNavigationActivity) getActivity();
        superNavigationActivity.setBarAsNextFragment();
        superNavigationActivity.updateTitle(R.string.title_quickmeasurefragment);

        user = UserManager.get().getUser();
        spinnerCountryChoices = new ArrayList<>();
        spinnerCountryChoices.add("Country");
        spinnerSizeChoices = new ArrayList<>();
        spinnerSizeChoices.add("Select Size");
        Bundle args = getArguments();
        if(args !=null) {
            categoryGarment = (CategoryGarment) args.getSerializable("categoryGarment");
            allGarmentType = SMXL.getGarmentTypeDBManager().getAllGarmentTypeByCategory(categoryGarment,user.getSexe());
        }else {
            Log.d(Constants.TAG, "CategoryGarment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_quick_measure, container, false);

        spinnerBrand = (Spinner) view.findViewById(R.id.brandSpinner);
        spinnerCountry = (Spinner) view.findViewById(R.id.countrySpinner);
        spinnerSize = (Spinner) view.findViewById(R.id.sizeSpinner);

        if(!categoryGarment.getCategory_garment_name().equals("Shoes")) {
            List<Brand> brands = new ArrayList<>(user.getBrands());
            List<Brand> allBrands = SMXL.getBrandSizeGuideDBManager().getAllBrandsByGarment(allGarmentType.get(0));

            for (Iterator<Brand> brandIterator = brands.iterator(); brandIterator.hasNext();){
                Brand brand = brandIterator.next();
                if(!allBrands.contains(brand)){
                    brandIterator.remove();
                }
            }
            brands.add(0, new Brand(0, "Brand"));
            spinnerBrand.setAdapter(new BrandAdapter(getActivity(), R.layout.item_spinner_brand_category, brands));
            spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        Brand selectedItem = (Brand) parent.getSelectedItem();
                        setSpinnerEnabled(spinnerCountry, true);
                        // fillSpinner with data
                        BrandSizeGuideMeasuresRow brandSizeGuideMeasuresRow = SMXL.getBrandSizeGuideDBManager().getBrandSizeGuideMeasureRowByBrandAndGarmentType(selectedItem, allGarmentType.get(0));
                        checkValidCountries(brandSizeGuideMeasuresRow);
                    } else {
                        setSpinnerEnabled(spinnerCountry, false);
                        setSpinnerEnabled(spinnerSize, false);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerCountry = (Spinner) view.findViewById(R.id.countrySpinner);
            spinnerCountry.setAdapter(new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.item_spinner_brand_category, spinnerCountryChoices));
            spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        setSpinnerEnabled(spinnerSize, true);
                        Brand selectedItem = (Brand) spinnerBrand.getSelectedItem();
                        ArrayList<BrandSizeGuideMeasuresRow> brandSizeGuideMeasuresRows = SMXL.getBrandSizeGuideDBManager().getBrandSizeGuideMeasureRowsByBrandAndGarmentType(selectedItem, allGarmentType.get(0));
                        spinnerSizeChoices.clear();
                        spinnerSizeChoices.add("Select Size");
                        Log.d(Constants.TAG, parent.getSelectedItem().toString());
                        switch (parent.getSelectedItem().toString().toUpperCase()) {
                            case "UE":
                                for (BrandSizeGuideMeasuresRow row : brandSizeGuideMeasuresRows) {
                                    spinnerSizeChoices.add(row.getSizeUE());
                                }
                                break;
                            case "UK":
                                for (BrandSizeGuideMeasuresRow row : brandSizeGuideMeasuresRows) {
                                    spinnerSizeChoices.add(row.getSizeUK());
                                }
                                break;
                            case "SMXL":
                                for (BrandSizeGuideMeasuresRow row : brandSizeGuideMeasuresRows) {
                                    if (!spinnerCountryChoices.contains(row.getSizeSMXL()))
                                        spinnerSizeChoices.add(row.getSizeSMXL());
                                }
                                break;
                            case "US":
                                for (BrandSizeGuideMeasuresRow row : brandSizeGuideMeasuresRows) {
                                    spinnerSizeChoices.add(row.getSizeUS());
                                }
                                break;
                            case "FR":
                                for (BrandSizeGuideMeasuresRow row : brandSizeGuideMeasuresRows) {
                                    spinnerSizeChoices.add(row.getSizeFR());
                                }
                                break;
                        }

                    } else {
                        setSpinnerEnabled(spinnerSize, false);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerSize = (Spinner) view.findViewById(R.id.sizeSpinner);
            spinnerSize.setAdapter(new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.item_spinner_brand_category, spinnerSizeChoices));
        }
        else {
            spinnerBrand.setVisibility(View.GONE);
            spinnerCountry.setVisibility(View.GONE);

            //SMXL.getSizeConvertDBManager().getConvertSizesByGarmentAndSex()
        }
        ImageView imageView = (ImageView)view.findViewById(R.id.imageGarment);
        imageView.setImageDrawable(getResources().getDrawable(categoryGarment.getIcon()) );



        return view;
    }
    private void setSpinnerEnabled(Spinner spinner, boolean enabled) {
        spinner.setEnabled(enabled);
        spinner.setAlpha(enabled ? 1.0f : 0.4f);
        spinner.setSelection(0,true);
    }

    private void checkValidCountries(BrandSizeGuideMeasuresRow brandSizeGuideMeasuresRow){
        spinnerCountryChoices.clear();
        spinnerCountryChoices.add("Country");

        if(!brandSizeGuideMeasuresRow.getSizeSMXL().isEmpty()){
            spinnerCountryChoices.add("SMXL");
        }
        if(!brandSizeGuideMeasuresRow.getSizeFR().isEmpty()){
            spinnerCountryChoices.add("fr");
        }
        if(!brandSizeGuideMeasuresRow.getSizeUE().isEmpty()){
            spinnerCountryChoices.add("ue");
        }
        if(!brandSizeGuideMeasuresRow.getSizeUK().isEmpty()){
            spinnerCountryChoices.add("uk");
        }
        if(!brandSizeGuideMeasuresRow.getSizeUS().isEmpty()){
            spinnerCountryChoices.add("fr");
        }
    }
}
