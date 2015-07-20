package com.aerolitec.SMXL.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.BrandSizeGuideMeasuresRow;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.SuperNavigationActivity;
import com.aerolitec.SMXL.ui.adapter.FavoriteCheckableBrandAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class QuickMeasureCategoryFragmentTopBottom extends Fragment {

    private SuperNavigationActivity superNavigationActivity;
    private User user;
    private GarmentType garmentType;

    //Spinner spinnerBrand;
    private Spinner spinnerCountry;
    private Spinner spinnerSize ;
    private List<String> spinnerCountryChoices;
    private List<String> spinnerSizeChoices;
    private ListView listView;
    private Brand selectedBrand;

    public static QuickMeasureCategoryFragmentTopBottom newInstance(GarmentType garmentType) {
        QuickMeasureCategoryFragmentTopBottom fragment = new QuickMeasureCategoryFragmentTopBottom();
        Bundle args = new Bundle();
        args.putSerializable("garmentType",garmentType);
        fragment.setArguments(args);
        return fragment;
    }

    public QuickMeasureCategoryFragmentTopBottom() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        user = UserManager.get().getUser();
        spinnerCountryChoices = new ArrayList<>();
        spinnerCountryChoices.add("Country");
        spinnerSizeChoices = new ArrayList<>();
        spinnerSizeChoices.add("Select Size");
        Bundle args = getArguments();
        if(args !=null) {
            garmentType = (GarmentType) args.getSerializable("garmentType");
        }else {
            Log.d(Constants.TAG, "CategoryGarment Vide");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view ;
        view = inflater.inflate(R.layout.fragment_category_quick_measure, container, false);

        spinnerCountry = (Spinner) view.findViewById(R.id.countrySpinner);
        spinnerSize = (Spinner) view.findViewById(R.id.sizeSpinner);
        ArrayList<Brand> brands = new ArrayList<>(user.getBrands());
        List<Brand> allBrands = SMXL.getBrandSizeGuideDBManager().getAllBrandsByGarment(garmentType);

        for (Iterator<Brand> brandIterator = brands.iterator(); brandIterator.hasNext(); ) {
            Brand brand = brandIterator.next();
            if (!allBrands.contains(brand)) {
                brandIterator.remove();
            }
        }
        spinnerCountry = (Spinner) view.findViewById(R.id.countrySpinner);
        spinnerCountry.setAdapter(new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.item_spinner_brand_category, spinnerCountryChoices));
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        setSpinnerEnabled(spinnerSize, true);
                        String selectedCountry = (String) spinnerCountry.getSelectedItem();

                        spinnerSizeChoices.clear();
                        spinnerSizeChoices.add("Select Size");
                        spinnerSizeChoices.addAll(SMXL.getBrandSizeGuideDBManager().getSizeListByBrandAndGarmentTypeAndCountry(selectedBrand, garmentType, selectedCountry));

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
       /* spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    onMeasureSelected();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/
        setSpinnerEnabled(spinnerCountry, false);
        setSpinnerEnabled(spinnerSize, false);

        listView = (ListView) view.findViewById(R.id.listViewBrands);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        FavoriteCheckableBrandAdapter brandAdapter = new FavoriteCheckableBrandAdapter(getActivity(), R.layout.item_favorite_brand, brands);
        listView.setAdapter(brandAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedBrand = (Brand) parent.getItemAtPosition(position);
                setSpinnerEnabled(spinnerCountry, true);
                BrandSizeGuideMeasuresRow brandSizeGuideMeasuresRow = SMXL.getBrandSizeGuideDBManager().getBrandSizeGuideMeasureRowByBrandAndGarmentType(selectedBrand, garmentType);
                checkValidCountries(brandSizeGuideMeasuresRow);
            }
        });
        //UtilityMethodsv2.setListViewHeightBasedOnChildren(listView);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageGarment);
        imageView.setImageDrawable(getResources().getDrawable(garmentType.getCategoryGarment().getIcon()));

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

    public HashMap<String,Double> onMeasureSelected(){

        HashMap<String,Double> result= new HashMap<>();
        if(spinnerCountry.getSelectedItemPosition()!=0 && spinnerSize.getSelectedItemPosition()!=0) {
            String selectedCountry = (String) spinnerCountry.getSelectedItem();
            String selectedSize = (String) spinnerSize.getSelectedItem();
            List<BrandSizeGuideMeasuresRow> brandSizeGuideMeasuresRowList = SMXL.getBrandSizeGuideDBManager().getBrandSizeGuideMeasureRowsByBrandAndGarmentTypeAndCountryAndSize(selectedBrand, garmentType, selectedCountry, selectedSize);

            Log.d(Constants.TAG, "Size of brandSizeGuideMeasure : " + brandSizeGuideMeasuresRowList.size());
            switch (garmentType.getCategoryGarment().getCategory_garment_name()) {
                case "Tshirt":
                    double totalChest = 0.0;
                    int nb_measureChest = 0;
                    for (BrandSizeGuideMeasuresRow row : brandSizeGuideMeasuresRowList) {
                        if (row.getChest() != 0) {
                            totalChest += row.getChest();
                            nb_measureChest++;
                        }
                    }
                    if (nb_measureChest != 0) {
                        totalChest /= nb_measureChest;
                    }
                    result.put("chest", totalChest);
                    Log.d(Constants.TAG, "Chest : " + totalChest);
                    break;
                case "Pants_Shorts":
                    double totalHips = 0.0;
                    double totalWaist = 0.0;
                    int nb_measureHips = 0;
                    int nb_measureWaist = 0;
                    for (BrandSizeGuideMeasuresRow row : brandSizeGuideMeasuresRowList) {
                        if (row.getHips() != 0.0) {
                            totalHips += row.getHips();
                            nb_measureHips++;
                        }
                        if (row.getWaist() != 0.0) {
                            totalWaist += row.getWaist();
                            nb_measureWaist++;
                        }
                    }
                    if (nb_measureHips != 0) {
                        totalHips /= nb_measureHips;
                    }
                    if (nb_measureWaist != 0) {
                        totalWaist /= nb_measureWaist;
                    }
                    result.put("hips", totalHips);
                    result.put("waist", totalWaist);

                    Log.d(Constants.TAG, "Hips : " + totalHips);
                    Log.d(Constants.TAG, "Waist: " + totalWaist);
                    break;
                default:
                    break;
            }
        }
        return result;
    }
}
