package com.aerolitec.SMXL.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.dummy.DummyContent;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.BrandSizeGuideMeasuresRow;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.adapter.BrandSizeGuideMeasureRowAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SelectSizeFragment extends Fragment implements AbsListView.OnItemClickListener {


    private static final String ARG_GARMENT = "garmentType";
    private static final String ARG_BRAND = "brand";
    private static final String ARG_SIZE = "size";

    private HashMap<String,String> size;

    private OnFragmentInteractionListener mListener;
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    private String selectedColumn;
    private ListAdapter mAdapter;

    public static SelectSizeFragment newInstance(GarmentType garmentType,Brand brand,HashMap<String,String> size) {
        SelectSizeFragment fragment = new SelectSizeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_GARMENT, garmentType);
        args.putSerializable(ARG_BRAND, brand);
        args.putSerializable(ARG_SIZE, size);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SelectSizeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if (extras!= null) {
            size = (HashMap<String,String>) extras.getSerializable(ARG_SIZE);
            Brand brand = (Brand) extras.getSerializable(ARG_BRAND);
            GarmentType garmentType = (GarmentType) extras.getSerializable(ARG_GARMENT);
            ArrayList<BrandSizeGuideMeasuresRow> brandSizeGuideMeasuresRows = SMXL.getBrandSizeGuideDBManager().getBrandSizeGuideMeasureRowsByBrandAndGarmentType(brand, garmentType);
            selectedColumn =findSizeType();
            mAdapter = new BrandSizeGuideMeasureRowAdapter(getActivity(),
                             android.R.layout.simple_list_item_1, android.R.id.text1, brandSizeGuideMeasuresRows,selectedColumn);
        }
        else {
            Log.d(Constants.TAG,"extras should not be null in SelectSizeFragment");
        }
        //mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
        //                 android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_size, container, false);
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            mListener.onFragmentInteraction((BrandSizeGuideMeasuresRow)parent.getItemAtPosition(position),selectedColumn);
            //mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    private String findSizeType() {
        if (size.containsKey("SMXL")  && !size.get("SMXL").equals("")) {
            return "SMXL";
        } else if (size.containsKey("UE") && !size.get("UE").equals("")) {
            return "UE";
        } else {
            for (Map.Entry<String, String> entry : size.entrySet()) {
                if (size.get(entry.getKey()).equals("")) {
                    return entry.getKey();
                }
            }
            //Ne devrait jamais passer par la
            return null;
        }
    }

    public interface OnFragmentInteractionListener {
         void onFragmentInteraction(BrandSizeGuideMeasuresRow measuresRow,String selectedColumn);
    }

}
