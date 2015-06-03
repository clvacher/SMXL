package com.aerolitec.SMXL.ui.fragment;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TabHost;
import android.widget.TextView;

import com.aerolitec.SMXL.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabsProfileDetailFragment extends Fragment {

    private FragmentTabHost fragmentTabHost;

    public TabsProfileDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentTabHost = new FragmentTabHost(getActivity());
        fragmentTabHost.setup(getActivity(), getChildFragmentManager(), R.id.frame_container);

        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("profile").setIndicator(getString(R.string.profile)),
                ProfilesDetailFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("wardrobe").setIndicator(getString(R.string.garments)),
                WardrobeDetailFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("measures").setIndicator(getString(R.string.measurements)),
                MeasureDetailFragment.class, null);


        fragmentTabHost.setBackgroundColor(getResources().getColor(R.color.SectionTitle));

        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                InputMethodManager inputManager = ( InputMethodManager ) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        for (int i = 0; i < fragmentTabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) fragmentTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.WHITE);
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tabs_state);
        }

        return fragmentTabHost;
    }


}
