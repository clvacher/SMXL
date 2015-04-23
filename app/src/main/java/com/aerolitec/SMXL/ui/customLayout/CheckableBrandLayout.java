package com.aerolitec.SMXL.ui.customLayout;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
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

    private boolean mChecked;
    private TextView textView;

    public Brand getBrand() {
        return brand;
    }

    private Brand brand;

    public CheckableBrandLayout(Context context, Brand brand) {
        super(context);
        textView = new TextView(context);
        textView.setPadding(5, 30, 5, 30);
        textView.setSingleLine(true);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(17);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        this.brand=brand;
        textView.setText(this.brand.getBrand());


        this.addView(textView);
    }

    public void setChecked(boolean checked) {
        mChecked = checked;

        if(checked){
            //Log.d("brand check :", brand.toString());
            setBackgroundResource(R.drawable.item_brand_checkable_check);
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            if(!selectedBrands.contains(brand)) {
                selectedBrands.add(brand);
            }
        }
        else{
            //Log.d("brand pas check :", brand.toString());
            setBackgroundResource(R.drawable.item_brand_checkable);
            textView.setTextColor(Color.parseColor("#000000"));
            selectedBrands.remove(brand);
        }


        //Log.d("mChecked", Boolean.toString(mChecked));
        //refreshDrawableState();
        //invalidate();

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


    @Override
    public String toString() {
        return "CheckableBrandLayout{" +
                "mChecked=" + mChecked +
                ", brand=" + brand +
                '}';
    }
}
