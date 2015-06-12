package com.aerolitec.SMXL.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.BrandSizeGuideMeasuresRow;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuickSizeSummaryFragment extends Fragment {
    private QuickSizeFragment quickSizeFragment;


    public QuickSizeSummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_quick_size_summary, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        quickSizeFragment = (QuickSizeFragment)getParentFragment();

        TextView tvSMXL =(TextView) view.findViewById(R.id.valueSMXL);
        TextView tvFR =(TextView) view.findViewById(R.id.valueFR);
        TextView tvUE =(TextView) view.findViewById(R.id.valueUE);
        TextView tvUS =(TextView) view.findViewById(R.id.valueUS);
        TextView tvUK =(TextView) view.findViewById(R.id.valueUK);


        HashMap<String,String> results = computeMeasures();
        tvSMXL.setText(results.get("SMXL"));
        tvFR.setText(results.get("FR"));
        tvUE.setText(results.get("UE"));
        tvUS.setText(results.get("US"));
        tvUK.setText(results.get("UK"));


        super.onViewCreated(view, savedInstanceState);
    }

    private HashMap<String,String> computeMeasures(){
        ArrayList<BrandSizeGuideMeasuresRow> brandSizeGuideMeasuresRows;

        brandSizeGuideMeasuresRows = SMXL.getBrandSizeGuideDBManager().getBrandSizeGuideMeasureRowsByBrandAndGarmentType(quickSizeFragment.getSelectedBrand(), quickSizeFragment.getSelectedGarmentType());

        BrandSizeGuideMeasuresRow brandSizeGuideMeasuresRow = BrandSizeGuideMeasuresRow.getClosestRowToMeasures(brandSizeGuideMeasuresRows, UserManager.get().getUser());

        return brandSizeGuideMeasuresRow.getCorrespondingSizes();
    }
}
