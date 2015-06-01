package com.aerolitec.SMXL.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.ui.activity.MainNavigationActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnChantierFragment extends Fragment {


    public EnChantierFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_en_chantier, container, false);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // update the actionbar to show the up carat/affordance
        ((MainNavigationActivity)getActivity()).getActionBarToggle().setDrawerIndicatorEnabled(false);
        ((MainNavigationActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // your code for order here
                ((MainNavigationActivity)getActivity()).onBackPressed();
                return true;
        }
        return true;
    }

}
