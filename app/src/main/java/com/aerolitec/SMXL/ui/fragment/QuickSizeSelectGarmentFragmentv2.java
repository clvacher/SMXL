package com.aerolitec.SMXL.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.adapter.FavoriteCheckableBrandAdapter;
import com.aerolitec.SMXL.ui.adapter.TypeGarmentAdapter;

import java.util.ArrayList;

public class QuickSizeSelectGarmentFragmentv2 extends Fragment {

    private ListView lvGarmentTypes;
    private User user;

    public QuickSizeSelectGarmentFragmentv2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = UserManager.get().getUser();
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
        lvGarmentTypes = (ListView) view.findViewById(R.id.listViewGarmentTypes);
    }

    @Override
    public void onResume() {
        fillGridView(lvGarmentTypes,getAllGarmentTypes());
        super.onResume();
    }


    private void fillGridView(ListView lv, ArrayList<GarmentType> garmentItems){
        lv.setAdapter(new TypeGarmentAdapter(getActivity(), R.layout.item_favorite_brand, garmentItems));
        lv.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
    }

    private ArrayList<GarmentType> getAllGarmentTypes(){
        return SMXL.getGarmentTypeDBManager().getAllGarmentTypesBySex(user.getSexe());
    }

}
