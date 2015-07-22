package com.aerolitec.SMXL.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;
import com.aerolitec.SMXL.ui.adapter.FavoriteCheckableBrandAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class QuickMeasureCategoryShoes extends Fragment {

    private EditText et_Pointure;
    private GarmentType garmentType;

    public static QuickMeasureCategoryShoes newInstance(GarmentType garmentType) {
        QuickMeasureCategoryShoes fragment = new QuickMeasureCategoryShoes();
        Bundle args = new Bundle();
        args.putSerializable("garmentType",garmentType);
        fragment.setArguments(args);
        return fragment;
    }

    public QuickMeasureCategoryShoes() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        View view = inflater.inflate(R.layout.fragment_category_quick_measure_shoes, container, false);
        et_Pointure =(EditText) view.findViewById(R.id.editSize);
        ListView listView = (ListView) view.findViewById(R.id.listViewPointure);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayList<Brand> brands = new ArrayList<>();
        brands.add(new Brand(0, "POINTURE"));
        FavoriteCheckableBrandAdapter brandAdapter = new FavoriteCheckableBrandAdapter(getActivity(), R.layout.item_favorite_brand, brands);
        listView.setAdapter(brandAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                et_Pointure.requestFocus();
                mgr.showSoftInput(et_Pointure, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        ImageView imageView = (ImageView) view.findViewById(R.id.imageGarment);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                et_Pointure.requestFocus();
                mgr.showSoftInput(et_Pointure, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        return view;

    }

    public double calculateFeetUsingSize(){
        double feet=0.0,size=0.0;
        if (!et_Pointure.getText().toString().isEmpty()){
            size = Double.valueOf(et_Pointure.getText().toString());
            feet = UtilityMethodsv2.convertFeetSizeToCm(size);
        }
        return feet;
    }
}
