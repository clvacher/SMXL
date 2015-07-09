package com.aerolitec.SMXL.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.customLayout.CustomGlobalGarmentWardrobeLayout;


public class WardrobeDetailFragment extends Fragment {

    private User user;
    private View view;

    LinearLayout linearLayout;

    private static final int TSHIRT = 1;
    private static final int ROBE = 2;
    private static final int PANTALON = 3;
    private static final int CHEMISE = 4;
    private static final int BLOUSON = 5;
    private static final int CHAUSSURE = 6;
    private static final int PULL = 7;
    private static final int VESTE = 8;
    private static final int COSTUME = 9;
    private static final int SOUSVET= 10;


    public WardrobeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user= UserManager.get().getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wardrobe_detail, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutWardrobe);
        return view;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        user=UserManager.get().getUser();
        initLinearLayout();
    }

    private void initLinearLayout(){
        linearLayout.removeAllViews();
        for(GarmentType gt : SMXL.getGarmentTypeDBManager().getAllGarmentTypesBySexOrderByPosition(user.getSexe())){
            linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), gt));
        }
        /*
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(TSHIRT)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(CHEMISE)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(PULL)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(BLOUSON)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(VESTE)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(PANTALON)));
        if(user.getSexe()==2){
            linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(ROBE)));
        }
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(CHAUSSURE)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(COSTUME)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(SOUSVET)));
        */
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
