package com.aerolitec.SMXL.ui.fragment;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.fragment.QuickMeasureCategoryFragment;


public class QuickMeasureFragment extends Fragment {


    public QuickMeasureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_measure, container, false);
        getChildFragmentManager().beginTransaction().addToBackStack(null).add(R.id.containerQuickMeasureTop, QuickMeasureCategoryFragment.newInstance(SMXL.getCategoryGarmentDBManager().getCategoryGarment(1))).commit();
        getChildFragmentManager().beginTransaction().addToBackStack(null).add(R.id.containerQuickMeasureBottom, QuickMeasureCategoryFragment.newInstance(SMXL.getCategoryGarmentDBManager().getCategoryGarment(3))).commit();
        getChildFragmentManager().beginTransaction().addToBackStack(null).add(R.id.containerQuickMeasureShoes, QuickMeasureCategoryFragment.newInstance(SMXL.getCategoryGarmentDBManager().getCategoryGarment(6))).commit();

        return view;
    }
}
