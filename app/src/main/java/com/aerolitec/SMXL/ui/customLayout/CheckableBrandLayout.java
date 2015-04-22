package com.aerolitec.SMXL.ui.customLayout;

import android.content.Context;
import android.graphics.Color;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;

import java.util.ArrayList;

/**
 * Created by Jerome on 17/04/2015.
 */
public class CheckableBrandLayout extends FrameLayout implements Checkable {

    public static ArrayList<Brand> selectedBrands = new ArrayList<>();
    public static ArrayList<CheckableBrandLayout> checkableBrands = new ArrayList<>();

    private boolean mChecked;
    private TextView textView;
    private Brand brand;

    public CheckableBrandLayout(Context context, TextView tv, Brand brand) {
        super(context);
        textView=tv;
        this.brand=brand;
        tv.setText(this.brand.getBrand());
        setBackgroundResource(R.drawable.cintre_inverse_hd);
        checkableBrands.add(this);
    }

    @SuppressWarnings("deprecation")
    public void setChecked(boolean checked) {
        mChecked = checked;

        if(checked){
            setBackgroundResource(R.drawable.item_brand_checkable_check);
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            if(!selectedBrands.contains(brand)) {
                selectedBrands.add(brand);
            }
        }
        else{
            setBackgroundResource(R.drawable.item_brand_checkable);
            textView.setTextColor(Color.parseColor("#000000"));
            selectedBrands.remove(brand);
        }


        /*setBackgroundDrawable(checked ? getResources().getDrawable(
                R.drawable.item_brand_checkable_check) : getResources().getDrawable(
                R.drawable.item_brand_checkable));*/

    }

    public boolean isChecked() {
        return mChecked;
    }

    public void toggle() {
        setChecked(!mChecked);
    }
}
