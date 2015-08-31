package com.aerolitec.SMXL.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.CreateUpdateProfileActivity;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by Nelson on 20/07/2015.
 */
public class SelectBrandsCreateProfileFragment extends SelectBrandsFragment{

    boolean validBrand = false;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridViewBrands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long mylng) {
                Brand selectedBrand = gridViewBrandsAdapter.getItem(position);
                brands.remove(selectedBrand);
                gridViewBrandsAdapter.getBrands().remove(selectedBrand);
                gridViewBrandsAdapter.notifyDataSetChanged();
                if (!user.getBrands().contains(selectedBrand)) {
                    user.getBrands().add(selectedBrand);
                    SMXL.getUserBrandDBManager().addUserBrand(user, selectedBrand);
                } else {
                    Toast.makeText(getActivity(), R.string.brand_already_selected, Toast.LENGTH_SHORT).show();
                }
                gridViewBrands.clearChoices();
                if (!validBrand) {
                    validBrand = hasUserSelectedOneTopAndBottomBrand();
                    buttonValidate.setClickable(validBrand);
                }
            }
        });

        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validBrand) {
                    save();
                } else {

                    Toast.makeText(getActivity(), "Selectionner une marque pour un haut et une marque pour un bas", Toast.LENGTH_LONG).show();
                    v.startAnimation(
                            AnimationUtils.loadAnimation(getActivity(),
                                    R.anim.horizontal));

                }
            }
        });


        if(((SMXL) getActivity().getApplication()).getFirstLaunch()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getResources().getString(R.string.step) + " 1/2")
                    .setMessage(R.string.select_brand_popup_message)
                    .setIcon(R.drawable.ic_launcher)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }
    }

    private boolean hasUserSelectedOneTopAndBottomBrand() {
        ArrayList<Brand> brands = user.getBrands();
        boolean top = false,bottom = false;
        for(Brand brand : brands){
            if(top && bottom){
                return true;
            }
            else {
                ArrayList<Integer> integers = SMXL.getGarmentTypeDBManager().getTop_Middle_BottomFromBrandAndUser(brand,user);
                if(integers.contains(Constants.TOP_GARMENT_TYPE)){
                    top = true;
                }
                if(integers.contains(Constants.BOTTOM_GARMENT_TYPE)){
                    bottom = true;
                }
            }
        }
        return false;
    }

    @Override
    protected void updateTopMenu() {
    }

    @Override
    protected void save() {
        hideKeyboardIfNeeded();
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame_container, new QuickMeasureFragment()).commit();
    }
}
