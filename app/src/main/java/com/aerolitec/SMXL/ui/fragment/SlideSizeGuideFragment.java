package com.aerolitec.SMXL.ui.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.ui.adapter.SizeGuideAdapter;
import com.wunderlist.slidinglayer.SlidingLayer;


public class SlideSizeGuideFragment extends Fragment {


    private View view;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private SlidingLayer slidingLayer;

    private Button buttonSizeGuide;

    public static SlideSizeGuideFragment newInstance(String param1, String param2) {
        SlideSizeGuideFragment fragment = new SlideSizeGuideFragment();
        return fragment;
    }

    public SlideSizeGuideFragment() {
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
        view = inflater.inflate(R.layout.fragment_slide_size_guide, container, false);

        slidingLayer = (SlidingLayer)view.findViewById(R.id.slidingLayerSizeGuide);
        slidingLayer.setSlidingFromShadowEnabled(false);
        slidingLayer.setSlidingEnabled(false);


        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) view.findViewById(R.id.viewPagerSizeGuide);
        mPagerAdapter = new SizeGuideAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        //mPager.setPageTransformer(true, new DepthPageTransformer());

        buttonSizeGuide = (Button) view.findViewById(R.id.buttonSizeGuide);
        buttonSizeGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(slidingLayer.isOpened()){
                    slidingLayer.closeLayer(true);
                }
                else{
                    slidingLayer.openLayer(true);
                }
            }
        });
        return view;
    }
}
