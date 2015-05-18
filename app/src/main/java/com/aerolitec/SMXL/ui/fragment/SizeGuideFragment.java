package com.aerolitec.SMXL.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.tools.ZoomOutPageTransformer;
import com.aerolitec.SMXL.ui.adapter.SizeGuideAdapter;


public class SizeGuideFragment extends Fragment {


    private View view;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    public static SizeGuideFragment newInstance(String param1, String param2) {
        SizeGuideFragment fragment = new SizeGuideFragment();
        return fragment;
    }

    public SizeGuideFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_size_guide, container, false);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) view.findViewById(R.id.viewPagerSizeGuide);
        mPagerAdapter = new SizeGuideAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mPager.setPageTransformer(true, new ZoomOutPageTransformer());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restauration des données du contexte utilisateur
            Log.d("coucou ma bite", "test");
            mPager.setCurrentItem(5);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pagerPosition", mPager.getCurrentItem());
    }


}
