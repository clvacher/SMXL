package com.aerolitec.SMXL.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aerolitec.SMXL.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabsFragment extends Fragment {

    private FragmentTabHost fragmentTabHost;

    public TabsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentTabHost = new FragmentTabHost(getActivity());
        fragmentTabHost.setup(getActivity(), getChildFragmentManager(), R.id.frame_container);

        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("profile").setIndicator("Profil"),
                ProfilesDetailFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("wardrobe").setIndicator("Dressing"),
                WardrobeDetailFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("measures").setIndicator("Mesures"),
                MeasureDetailFragment.class, null);


        fragmentTabHost.setBackgroundColor(getResources().getColor(R.color.SectionTitle));


        for (int i = 0; i < fragmentTabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) fragmentTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.WHITE);
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tabs_state);
        }
        return fragmentTabHost;
    }


}
