package com.aerolitec.SMXL.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.adapter.TypeGarmentAdapter;

import java.util.ArrayList;

public class QuickSizeSelectGarmentFragment extends Fragment {

    private QuickSizeFragment quickSizeFragment;

    private GridView gvGarmentTypes;
    private User user;

    public QuickSizeSelectGarmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = UserManager.get().getUser();
        quickSizeFragment = (QuickSizeFragment)getParentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quicksize_select_garment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gvGarmentTypes = (GridView) view.findViewById(R.id.gridViewGarmentTypes);
    }

    @Override
    public void onResume() {
        fillGridView(gvGarmentTypes,getAllGarmentTypes());
        super.onResume();
    }


    private void fillGridView(GridView lv, ArrayList<GarmentType> garmentItems){
        lv.setAdapter(new TypeGarmentAdapter(getActivity(), R.layout.item_garment_with_icon, garmentItems));
        lv.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                quickSizeFragment.setSelectedGarmentType((GarmentType) parent.getItemAtPosition(position));
                quickSizeFragment.getChildFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.containerQuickSizeFragment, new QuickSizeSelectBrandFragment(), "brand")
                        .commit();
            }
        });
    }

    private ArrayList<GarmentType> getAllGarmentTypes(){
        return SMXL.getGarmentTypeDBManager().getAllGarmentTypesBySex(user.getSexe());
    }

}
