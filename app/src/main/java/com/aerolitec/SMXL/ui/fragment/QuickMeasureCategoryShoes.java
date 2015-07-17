package com.aerolitec.SMXL.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.tools.Constants;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
        ImageView imageView = (ImageView)view.findViewById(R.id.imageGarment);
        imageView.setImageDrawable(getResources().getDrawable(garmentType.getCategoryGarment().getIcon()));

        return view;
    }

    public double calculateFeetUsingSize(){
        double feet=0.0,size=0.0;
        if (!et_Pointure.getText().toString().isEmpty()){
            size = Double.valueOf(et_Pointure.getText().toString());
            Locale defaultLocale = Locale.getDefault();
            Locale.setDefault(Locale.US);
            NumberFormat format = new DecimalFormat("#0.0");
            feet = Double.parseDouble(format.format(((2f / 3f) * size) - 1f));
            Locale.setDefault(defaultLocale);
            Log.d(Constants.TAG,"Feet (cm) : "+ feet);
        }
        return feet;
    }
}
