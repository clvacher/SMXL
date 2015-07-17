package com.aerolitec.SMXL.ui.fragment;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.SuperNavigationActivity;

import java.util.ArrayList;
import java.util.HashMap;


public class QuickMeasureFragment extends Fragment {

    private SuperNavigationActivity superNavigationActivity;
    private static final int TSHIRT_CATEGORYGARMENT = 1;
    private static final int PANTS_SHORTS_CATEGORYGARMENT = 3;
    private static final int SHOES_CATEGORYGARMENT = 6;

    QuickMeasureCategoryFragmentTopBottom quickMeasureCategoryFragmentTop;
    QuickMeasureCategoryFragmentTopBottom quickMeasureCategoryFragmentBottom;
    QuickMeasureCategoryShoes quickMeasureCategoryFragmentShoes;

    public QuickMeasureFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        superNavigationActivity = (SuperNavigationActivity) getActivity();
        superNavigationActivity.setBarAsNextFragment();
        superNavigationActivity.updateTitle(R.string.title_quickmeasurefragment);
        superNavigationActivity.updateHamburger();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_measure, container, false);
        int sex = UserManager.get().getUser().getSexe();

        ArrayList<GarmentType> allGarmentTypeByCategory = SMXL.getGarmentTypeDBManager().getAllGarmentTypeByCategory(SMXL.getCategoryGarmentDBManager().getCategoryGarment(TSHIRT_CATEGORYGARMENT), sex);
        quickMeasureCategoryFragmentTop = QuickMeasureCategoryFragmentTopBottom.newInstance(allGarmentTypeByCategory.get(0));
        getChildFragmentManager().beginTransaction().addToBackStack(null).add(R.id.containerQuickMeasureTop,quickMeasureCategoryFragmentTop ).commit();

        allGarmentTypeByCategory = SMXL.getGarmentTypeDBManager().getAllGarmentTypeByCategory(SMXL.getCategoryGarmentDBManager().getCategoryGarment(PANTS_SHORTS_CATEGORYGARMENT), sex);
        quickMeasureCategoryFragmentBottom = QuickMeasureCategoryFragmentTopBottom.newInstance(allGarmentTypeByCategory.get(1));
        getChildFragmentManager().beginTransaction().addToBackStack(null).add(R.id.containerQuickMeasureBottom, quickMeasureCategoryFragmentBottom).commit();

        allGarmentTypeByCategory = SMXL.getGarmentTypeDBManager().getAllGarmentTypeByCategory(SMXL.getCategoryGarmentDBManager().getCategoryGarment(SHOES_CATEGORYGARMENT), sex);
        quickMeasureCategoryFragmentShoes = QuickMeasureCategoryShoes.newInstance(allGarmentTypeByCategory.get(0));
        getChildFragmentManager().beginTransaction().addToBackStack(null).add(R.id.containerQuickMeasureShoes, quickMeasureCategoryFragmentShoes).commit();

        Button buttonContinue = (Button)view.findViewById(R.id.buttonValidationQuickMeasure);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = UserManager.get().getUser();
                HashMap<String,Double> topMeasures = quickMeasureCategoryFragmentTop.onMeasureSelected();
                HashMap<String,Double> bottomMeasures = quickMeasureCategoryFragmentBottom.onMeasureSelected();
                if(topMeasures.containsKey("chest")){
                    user.setChest(topMeasures.get("chest"));
                }
                if(bottomMeasures.containsKey("hips")) {
                    user.setHips(bottomMeasures.get("hips"));
                }
                if(bottomMeasures.containsKey("waist")) {
                    user.setWaist(bottomMeasures.get("waist"));
                }
                user.setFeet(quickMeasureCategoryFragmentShoes.calculateFeetUsingSize());
                SMXL.getUserDBManager().updateUser(user);
                superNavigationActivity.finish();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
