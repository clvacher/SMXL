package com.aerolitec.SMXL.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.customLayout.CustomGlobalGarmentWardrobeLayout;


public class WardrobeDetailFragment extends Fragment {

    private User user;
    private View view;

    LinearLayout linearLayout;

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
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(1)));
        if(user.getSexe()==2){
            linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(2)));
        }
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(3)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(4)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(5)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(6)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(7)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(8)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(9)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(10)));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
