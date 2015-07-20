package com.aerolitec.SMXL.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.ui.SMXL;

/**
 * Created by Nelson on 20/07/2015.
 */
public class SelectBrandsCreateProfileFragment extends SelectBrandsFragment{
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

        builder.setTitle(R.string.step + "1/2")
                .setMessage(R.string.title_activity_select_brands)
                .setIcon(R.drawable.ic_launcher)
                .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
}
