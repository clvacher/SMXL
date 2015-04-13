package com.aerolitec.SMXL.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.SizeConvert;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.fragment.SizeGuideFragment;

import java.util.ArrayList;

/**
 * Created by Jerome on 13/04/2015.
 */
public class SizeGuideAdapter extends FragmentStatePagerAdapter {

    private static ArrayList<GarmentType> al_garments = SMXL.getDataBase().getGarmentsSizeGuide();

    private static final int NUM_PAGES = al_garments.size();


    public SizeGuideAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        GarmentType garment = al_garments.get(position);
        ArrayList<SizeConvert> al_size = SMXL.getDataBase().getConvertSizesByGarment(garment.getType());

        return new SizeGuideFragment().newInstance(garment, al_size);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(al_garments.get(position)!=null){
            return al_garments.get(position).getType();
        }
        return null;
    }

}
