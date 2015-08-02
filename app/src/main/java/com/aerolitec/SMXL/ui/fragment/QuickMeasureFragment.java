package com.aerolitec.SMXL.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.MainNavigationActivity;
import com.aerolitec.SMXL.ui.activity.SuperNavigationActivity;


import java.util.ArrayList;
import java.util.HashMap;


public class QuickMeasureFragment extends Fragment {

    private SuperNavigationActivity superNavigationActivity;


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

        ArrayList<GarmentType> allGarmentTypeByCategory = SMXL.getGarmentTypeDBManager().getAllGarmentTypeByCategory(SMXL.getCategoryGarmentDBManager().getCategoryGarment(Constants.TSHIRT_CATEGORYGARMENT), sex);
        quickMeasureCategoryFragmentTop = QuickMeasureCategoryFragmentTopBottom.newInstance(allGarmentTypeByCategory.get(0));
        getChildFragmentManager().beginTransaction().addToBackStack(null).add(R.id.containerQuickMeasureTop,quickMeasureCategoryFragmentTop ).commit();

        allGarmentTypeByCategory = SMXL.getGarmentTypeDBManager().getAllGarmentTypeByCategory(SMXL.getCategoryGarmentDBManager().getCategoryGarment(Constants.PANTS_SHORTS_CATEGORYGARMENT), sex);
        quickMeasureCategoryFragmentBottom = QuickMeasureCategoryFragmentTopBottom.newInstance(allGarmentTypeByCategory.get(1));
        getChildFragmentManager().beginTransaction().addToBackStack(null).add(R.id.containerQuickMeasureBottom, quickMeasureCategoryFragmentBottom).commit();

        quickMeasureCategoryFragmentShoes = QuickMeasureCategoryShoes.newInstance();
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
                double feet = quickMeasureCategoryFragmentShoes.calculateFeetUsingSize();
                if(feet != 0.0){
                    user.setFeet(feet);
                }
                SMXL.getUserDBManager().updateUser(user);
                if (getActivity() instanceof MainNavigationActivity){
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                else{
                    superNavigationActivity.setResult(Activity.RESULT_OK);
                    superNavigationActivity.finish();
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(((SMXL) getActivity().getApplication()).getFirstLaunch()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getResources().getString(R.string.step) + " 2/2")
                    .setMessage(R.string.quickmeasure_popup_message)
                    .setIcon(R.drawable.ic_logo_quicksize)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
            .setNeutralButton(R.string.dont_show, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ((SMXL) getActivity().getApplication()).setFirstLaunch();
                    dialog.dismiss();
                }
            });
            builder.show();
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

}
