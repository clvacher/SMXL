package com.aerolitec.SMXL.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.SizeConvert;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.fragment.PageSizeGuideFragment;

import java.util.ArrayList;

/**
 * Created by Jerome on 13/04/2015.
 */
public class SizeGuideAdapter extends FragmentPagerAdapter {

    private static ArrayList<GarmentType> al_garments = SMXL.getSizeConvertDBManager().getGarmentsSizeGuideGroupBySexAndGarment();
    private static final int NUM_PAGES = al_garments.size();
    private Context context;


    public SizeGuideAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        GarmentType garment = al_garments.get(position);
        ArrayList<SizeConvert> al_size = new ArrayList<>();

        if(garment.getSex().contains("F") || garment.getSex().contains("H")){
            al_size = SMXL.getSizeConvertDBManager().getConvertSizesByGarmentAndSex(garment.getType(), garment.getSex());
        }
        else{
            al_size = SMXL.getSizeConvertDBManager().getConvertSizesByGarment(garment);
        }
        return new PageSizeGuideFragment().newInstance(garment, al_size);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(al_garments.get(position)!=null){
            if(al_garments.get(position).getSex().contains("F") || al_garments.get(position).getSex().contains("H")) {
                return context.getResources().getString(context.getResources().getIdentifier(al_garments.get(position).getType(), "string", context.getPackageName())) + " - " + al_garments.get(position).getSex();//TODO getResources.getIdentifier
            }
            return context.getResources().getString(context.getResources().getIdentifier(al_garments.get(position).getType(), "string", context.getPackageName()));
        }
        return null;
    }

}
