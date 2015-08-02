package com.aerolitec.SMXL.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.CreateUpdateProfileActivity;

/**
 * Created by Nelson on 20/07/2015.
 */
public class SelectBrandsCreateProfileFragment extends SelectBrandsFragment{
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridViewBrands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long mylng) {
                if (user.getBrands().size() < 5) {
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
                } else {
                    Toast.makeText(getActivity(), R.string.max_brands_selected, Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getBrands().size() >= 2) {
                    save();
                } else {
                    Toast.makeText(getActivity(), R.string.not_enough_brands_selected, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void updateTopMenu() {
    }

    @Override
    protected void save() {
        hideKeyboardIfNeeded();
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame_container, new QuickMeasureFragment()).commit();
    }
}
