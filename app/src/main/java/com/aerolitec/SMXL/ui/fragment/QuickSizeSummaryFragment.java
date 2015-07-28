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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.BrandSizeGuideMeasuresRow;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.SizeConvert;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.BrowserActivity;
import com.aerolitec.SMXL.ui.activity.MainNavigationActivity;
import com.aerolitec.SMXL.ui.activity.SuperNavigationActivity;
import com.aerolitec.SMXL.ui.customLayout.CustomSizeGuideTableRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuickSizeSummaryFragment extends Fragment implements MainNavigationActivity.OnBackPressedListener{
    private QuickSizeFragment quickSizeFragment;

    Button shopButton;
    TextView tvSizeType,tvSizeValue;
    TableLayout tableLayoutLeft,tableLayoutRight;
    HashMap<String,String> resultQuickSize ;
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
        
        resultQuickSize = computeMeasures();

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

        tableLayoutLeft = (TableLayout)view.findViewById(R.id.tableSizeConvertLeft);
        tableLayoutRight = (TableLayout)view.findViewById(R.id.tableSizeConvertRight);
        fillSizeConvertTable();

        tvSizeType = (TextView)view.findViewById(R.id.labelTagType);
        tvSizeValue = (TextView)view.findViewById(R.id.labelTagValue);
        fillLabelTag();

        super.onViewCreated(view, savedInstanceState);
    }

    private void fillLabelTag() {
        String sizeType = findSizeType();
        tvSizeType.setText(sizeType);
        tvSizeValue.setText(resultQuickSize.get(sizeType));
    }

    private void fillSizeConvertTable() {
        HashMap<String,String> sizeConvertResult = getSizeConvert();
        TableLayout currentTableLayout = tableLayoutLeft;
        if(sizeConvertResult.size()!=0) {
           //Remplis les 2 Table Layout
            for (Map.Entry<String, String> entry : sizeConvertResult.entrySet()) {
                if (resultQuickSize.get(entry.getKey()) != null && !resultQuickSize.get(entry.getKey()).isEmpty()){
                    currentTableLayout.addView(new CustomSizeGuideTableRow(getActivity(), entry.getKey(), resultQuickSize.get(entry.getKey())));
                }
                else {
                    currentTableLayout.addView(new CustomSizeGuideTableRow(getActivity(), entry.getKey(), entry.getValue()));
                }
                // Permet d'avoir 2 tableaux les plus equilibre possible en hauteur
                int nbEntry = sizeConvertResult.size();
                if(currentTableLayout.getChildCount()>=Math.ceil(nbEntry / 2.0)){
                    currentTableLayout = tableLayoutRight;
                }
            }
        }
        else {
            // Hide the TableLayout
            tableLayoutLeft.setVisibility(View.GONE);
        }
    }

    private HashMap<String, String> getSizeConvert() {
        HashMap<String, String> sizeConvertRow = new HashMap<>();
        ArrayList<SizeConvert> list ;
        String sizeType = findSizeType();
        list = SMXL.getSizeConvertDBManager().getConvertSizesByGarmentTypeAndSize(quickSizeFragment.getSelectedGarmentType(), sizeType, resultQuickSize.get(sizeType));
        if(list.size()== 0){
            GarmentType maleGarmentType = SMXL.getGarmentTypeDBManager().getGarmentTypeByNameAndSex(quickSizeFragment.getSelectedGarmentType().getType(), Constants.MALE);
            list = SMXL.getSizeConvertDBManager().getConvertSizesByGarmentTypeAndSize(maleGarmentType, sizeType, resultQuickSize.get(sizeType));
        }
        if (list.size()!=0){
            sizeConvertRow =  list.get(0).getCorrespondingSizes();
        }
        return sizeConvertRow;
    }

    private String findSizeType() {
        if (resultQuickSize.get("SMXL") != null && !resultQuickSize.get("SMXL").equals("")){
            return "SMXL";
        }
        else if (resultQuickSize.get("UE") != null && !resultQuickSize.get("UE").equals("")){
            return "UE";
        }
        else {
            for (Map.Entry<String, String> entry : resultQuickSize.entrySet()) {
                if (resultQuickSize.get(entry.getKey()) != null && !resultQuickSize.get(entry.getKey()).equals("")) {
                    return entry.getKey();
                }
            }
            //Ne devrait jamais passer par la
            return null;
        }
    }


    private HashMap<String,String> computeMeasures(){
        ArrayList<BrandSizeGuideMeasuresRow> brandSizeGuideMeasuresRows;

        brandSizeGuideMeasuresRows = SMXL.getBrandSizeGuideDBManager().getBrandSizeGuideMeasureRowsByBrandAndGarmentType(quickSizeFragment.getSelectedBrand(), quickSizeFragment.getSelectedGarmentType());

        BrandSizeGuideMeasuresRow brandSizeGuideMeasuresRow = BrandSizeGuideMeasuresRow.getClosestRowToMeasures(brandSizeGuideMeasuresRows, UserManager.get().getUser());

        return brandSizeGuideMeasuresRow.getCorrespondingSizes();
    }

    @Override
    public void backPressed() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.containerQuickSizeFragment,new QuickSizeSelectBrandFragment());
        fragmentTransaction.commit();
        quickSizeFragment.clearSelectedBrand();
    }

    @Override
    public void onDestroyView() {
        ((SuperNavigationActivity) getActivity()).setOnBackPressedListener(null);
        super.onDestroyView();
    }
}
