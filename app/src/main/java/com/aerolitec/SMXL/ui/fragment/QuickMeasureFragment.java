package com.aerolitec.SMXL.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aerolitec.SMXL.R;


public class QuickMeasureFragment extends Fragment {

    public static QuickMeasureFragment newInstance() {
        QuickMeasureFragment fragment = new QuickMeasureFragment();
        return fragment;
    }

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
        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

}
