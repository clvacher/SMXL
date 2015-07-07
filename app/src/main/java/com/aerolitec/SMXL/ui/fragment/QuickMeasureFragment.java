package com.aerolitec.SMXL.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.ui.activity.CreateUpdateProfileActivity;
import com.aerolitec.SMXL.ui.activity.SuperNavigationActivity;


public class QuickMeasureFragment extends Fragment {

    private SuperNavigationActivity superNavigationActivity;

    public static QuickMeasureFragment newInstance() {
        QuickMeasureFragment fragment = new QuickMeasureFragment();
        return fragment;
    }

    public QuickMeasureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        superNavigationActivity = (SuperNavigationActivity) getActivity();
        superNavigationActivity.setBarAsNextFragment();
        superNavigationActivity.updateTitle(R.string.title_quickmeasurefragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_measure, container, false);

        Button buttonValidationMeasure = (Button) view.findViewById(R.id.buttonValidationMeasure);
        buttonValidationMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = ( InputMethodManager ) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)){
                    getActivity().onBackPressed();
                }
                if(superNavigationActivity instanceof CreateUpdateProfileActivity) {
                    superNavigationActivity.finish();
                }
                else{
                    getActivity().onBackPressed();
                }
                superNavigationActivity.restoreDefaultTitleCurrentSection();
            }
        });
        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

}
