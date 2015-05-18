package com.aerolitec.SMXL.ui.fragment;


import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.SizeConvert;

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
            Log.d("garment", getArguments().get(ARG_PARAM1).toString());
            Log.d("liste", getArguments().get(ARG_PARAM2).toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_page_size_guide, container, false);

        TableLayout tableSizeConvert = ((TableLayout)rootView.findViewById(R.id.tableSizeGarment));

        int i=0;
        //TODO Foutre un adapteur pour éviter que ça lague
        for(SizeConvert sc : al_size ){

            TableRow tableRow = new TableRow(getActivity());

            if(i%2==0)
                tableRow.setBackgroundColor(Color.WHITE);
            i++;

/*
            TextView textViewCH = new TextView(getActivity());
            TextView textViewJAP = new TextView(getActivity());
            TextView textViewRUS = new TextView(getActivity());

            textViewCH.setText(sc.getValueCH());
            textViewJAP.setText(sc.getValueJAP());
            textViewRUS.setText(sc.getValueRUS());

            textViewCH.setGravity(Gravity.CENTER);
            textViewJAP.setGravity(Gravity.CENTER);
            textViewRUS.setGravity(Gravity.CENTER);

            textViewCH.setTextSize(20);
            textViewJAP.setTextSize(20);
            textViewRUS.setTextSize(20);

            tableRow.addView(textViewCH);
            tableRow.addView(textViewJAP);
            tableRow.addView(textViewRUS);
*/

            TextView textViewUE = new TextView(getActivity());
            TextView textViewUS = new TextView(getActivity());
            TextView textViewUK = new TextView(getActivity());
            TextView textViewFR = new TextView(getActivity());
            TextView textViewSMXL = new TextView(getActivity());

            textViewUE.setText(sc.getValueUE());
            textViewUS.setText(sc.getValueUS());
            textViewUK.setText(sc.getValueUK());
            textViewFR.setText(sc.getValueFR());
            textViewSMXL.setText(sc.getValueSMXL());

            textViewUE.setGravity(Gravity.CENTER);
            textViewUS.setGravity(Gravity.CENTER);
            textViewUK.setGravity(Gravity.CENTER);
            textViewFR.setGravity(Gravity.CENTER);
            textViewSMXL.setGravity(Gravity.CENTER);

            textViewUE.setTextSize(20);
            textViewUS.setTextSize(20);
            textViewUK.setTextSize(20);
            textViewFR.setTextSize(20);
            textViewSMXL.setTextSize(20);


            tableRow.addView(textViewUE);
            tableRow.addView(textViewUS);
            tableRow.addView(textViewUK);
            tableRow.addView(textViewFR);
            tableRow.addView(textViewSMXL);

            tableSizeConvert.addView(tableRow);
        }

        return rootView;
    }


}
