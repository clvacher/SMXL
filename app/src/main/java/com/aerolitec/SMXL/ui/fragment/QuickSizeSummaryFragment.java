package com.aerolitec.SMXL.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.BrandSizeGuideMeasuresRow;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.BrowserActivity;
import com.aerolitec.SMXL.ui.activity.MainNavigationActivity;
import com.aerolitec.SMXL.ui.activity.SuperNavigationActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuickSizeSummaryFragment extends Fragment implements MainNavigationActivity.OnBackPressedListener{
    private QuickSizeFragment quickSizeFragment;

    Button shopButton;

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

        shopButton = (Button)view.findViewById(R.id.buttonShopQuickSizeSummary);

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


        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlBrand = quickSizeFragment.getSelectedBrand().getBrandWebsite();
                if (urlBrand != null) {
                    if (!urlBrand.startsWith("http://") && !urlBrand.startsWith("https://")) {
                        urlBrand = "http://" + urlBrand;
                    }
                    Intent browserIntent = new Intent(getActivity(), BrowserActivity.class);
                    browserIntent.putExtra("URL", urlBrand);
                    browserIntent.putExtra("TITLE", quickSizeFragment.getSelectedBrand().getBrand_name());
                    startActivity(browserIntent);
                }
            }
        });
        ((SuperNavigationActivity) getActivity()).setOnBackPressedListener(this);
        super.onViewCreated(view, savedInstanceState);
    }


    private HashMap<String,String> computeMeasures(){
        ArrayList<BrandSizeGuideMeasuresRow> brandSizeGuideMeasuresRows;

        brandSizeGuideMeasuresRows = SMXL.getBrandSizeGuideDBManager().getBrandSizeGuideMeasureRowsByBrandAndGarmentType(quickSizeFragment.getSelectedBrand(), quickSizeFragment.getSelectedGarmentType());

        BrandSizeGuideMeasuresRow brandSizeGuideMeasuresRow = BrandSizeGuideMeasuresRow.getClosestRowToMeasures(brandSizeGuideMeasuresRows, UserManager.get().getUser());

        return brandSizeGuideMeasuresRow.getCorrespondingSizes();
    }

    @Override
    public void backPressed() {
        //TODO De la merde a changer
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentManager currentFragmentManager;
        Fragment fragment = fragmentManager.findFragmentById(R.id.frame_container);

        // Pour le quicksize dans l'acceuil
        if( fragment instanceof TabsFragmentHomeDressingQuicksize) {
            Fragment currentFragment = fragment.getChildFragmentManager().findFragmentByTag("quicksize");
            currentFragmentManager = currentFragment.getFragmentManager();
        }
        // Pour le quicksize dans l'onglet
        else {
            currentFragmentManager = getActivity().getSupportFragmentManager();
        }
        FragmentTransaction fragmentTransaction = currentFragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_container, new QuickSizeFragment(),"quicksize");
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        ((SuperNavigationActivity) getActivity()).setOnBackPressedListener(null);
        super.onDestroyView();
    }
}
