package com.aerolitec.SMXL.ui.fragment;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.SizeConvert;
import com.aerolitec.SMXL.ui.adapter.PageSizeGuideAdapter;

import java.util.ArrayList;


public class PageSizeGuideFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "garment";
    private static final String ARG_PARAM2 = "al_size";

    private GarmentType garment;
    private ArrayList<SizeConvert> al_size;


    public static PageSizeGuideFragment newInstance(GarmentType param1, ArrayList<SizeConvert> param2) {
        PageSizeGuideFragment fragment = new PageSizeGuideFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public PageSizeGuideFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            garment = (GarmentType) getArguments().get(ARG_PARAM1);
            al_size = (ArrayList<SizeConvert>)getArguments().get(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_page_size_guide, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listViewTableSizeConvert);

        TextView textViewUE = (TextView) rootView.findViewById(R.id.textViewUE);
        TextView textViewUS = (TextView) rootView.findViewById(R.id.textViewUS);
        TextView textViewUK = (TextView) rootView.findViewById(R.id.textViewUK);
        TextView textViewFR = (TextView) rootView.findViewById(R.id.textViewFR);
        TextView textViewSMXL = (TextView) rootView.findViewById(R.id.textViewSMXL);

        Point size = new Point();
        ((WindowManager)getActivity().getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        int width=0, height=0;
        size.equals(width, height);

        textViewUE.setWidth(width / 5);
        textViewUS.setWidth(width/5);
        textViewUK.setWidth(width/5);
        textViewFR.setWidth(width/5);
        textViewSMXL.setWidth(width / 5);

        PageSizeGuideAdapter pageSizeGuideAdapter = new PageSizeGuideAdapter(this.getActivity(), al_size);
        listView.setAdapter(pageSizeGuideAdapter);

        return rootView;
    }



}
