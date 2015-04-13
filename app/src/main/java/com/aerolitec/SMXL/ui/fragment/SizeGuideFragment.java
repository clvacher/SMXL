package com.aerolitec.SMXL.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class SizeGuideFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "garment";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private GarmentType garment;
    private ArrayList<SizeConvert> al_size;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SizeGuideFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SizeGuideFragment newInstance(GarmentType param1, ArrayList<SizeConvert> param2) {
        SizeGuideFragment fragment = new SizeGuideFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public SizeGuideFragment() {
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_size_guide, container, false);
        ((TextView)rootView.findViewById(R.id.tvGarmentType)).setText(garment.getType());

        TableLayout tableSizeConvert = ((TableLayout)rootView.findViewById(R.id.tableSize));

        for(SizeConvert sc : al_size ){
            TableRow tableRow = new TableRow(getActivity());

            TextView textViewUE = new TextView(getActivity());
            TextView textViewUS = new TextView(getActivity());
            TextView textViewUK = new TextView(getActivity());
            TextView textViewFR = new TextView(getActivity());
            TextView textViewUniversal = new TextView(getActivity());

            textViewUE.setText(sc.getValueUE());
            textViewUS.setText(sc.getValueUS());
            textViewUK.setText(sc.getValueUK());
            textViewFR.setText(sc.getValueFR());
            textViewUniversal.setText(sc.getType());


            tableRow.addView(textViewUE);
            tableRow.addView(textViewUS);
            tableRow.addView(textViewUK);
            tableRow.addView(textViewFR);
            tableRow.addView(textViewUniversal);

            tableSizeConvert.addView(tableRow);
        }

        return rootView;
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }


}