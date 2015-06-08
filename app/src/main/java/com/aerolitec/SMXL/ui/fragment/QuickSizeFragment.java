package com.aerolitec.SMXL.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.CategoryGarment;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.model.UserClothes;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.activity.AddGarmentActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuickSizeFragment extends Fragment {
    AddGarmentActivity activity;

    private User user;

    public User getUser() {
        return user;
    }


    private GarmentType selectedGarmentType;
    private Brand selectedBrand;

    private int selectedIdUserClothes=-1;
    private UserClothes userClothes;

    private TextView tvBrand, tvGarment;
    private ImageView ivGarment;

    public GarmentType getSelectedGarmentType() {
        return selectedGarmentType;
    }
    public void setSelectedGarmentType(GarmentType selectedGarmentType) {
        this.selectedGarmentType = selectedGarmentType;
        tvGarment.setText(getResources().getIdentifier(selectedGarmentType.getType(),"string",getActivity().getPackageName()));
        ivGarment.setImageResource(selectedGarmentType.getCategoryGarment().getIcon());
    }

    public Brand getSelectedBrand() {
        return selectedBrand;
    }
    public void setSelectedBrand(Brand selectedBrand) {
        this.selectedBrand = selectedBrand;
        tvBrand.setText(selectedBrand.getBrand_name());
    }



    public QuickSizeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = UserManager.get().getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quick_size, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivGarment = (ImageView) view.findViewById(R.id.garmentIcon);
        tvGarment = (TextView) view.findViewById(R.id.garmentType);
        tvBrand = (TextView) view.findViewById(R.id.garmentBrand);

        getChildFragmentManager().beginTransaction().add(R.id.containerQuickSizeFragment, new QuickSizeSelectGarmentFragmentv2()).commit();
    }
}