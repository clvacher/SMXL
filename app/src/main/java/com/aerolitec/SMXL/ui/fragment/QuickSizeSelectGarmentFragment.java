package com.aerolitec.SMXL.ui.fragment;


import android.app.AlertDialog;
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
import com.aerolitec.SMXL.tools.Constants;
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
        if(user == null)
        {
            user = UserManager.get().getUser();
        }
        fillGridView(gvGarmentTypes, getAllGarmentTypes());
        super.onResume();
    }


    private void fillGridView(GridView lv, ArrayList<GarmentType> garmentItems){
        lv.setAdapter(new TypeGarmentAdapter(getActivity(), R.layout.item_garment_with_icon, garmentItems));
        lv.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GarmentType selectedGarmentType = (GarmentType) parent.getItemAtPosition(position);
                String missingMeasure = checkValidMeasure(selectedGarmentType);
                //TODO Temporaire pour tester
                missingMeasure="";
                if (missingMeasure.isEmpty()) {
                    quickSizeFragment.setSelectedGarmentType(selectedGarmentType);
                    quickSizeFragment.getChildFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.containerQuickSizeFragment, new QuickSizeSelectBrandFragment(), "brand")
                            .commit();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(getString(R.string.message_enter_measures) +"\n" +missingMeasure)
                            .setTitle(R.string.missingMeasures).setNeutralButton(R.string.ok, null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    private ArrayList<GarmentType> getAllGarmentTypes(){
        ArrayList<GarmentType> result = SMXL.getGarmentTypeDBManager().getAllGarmentTypesBySexOrderByPosition(user.getSexe());
        //TODO A changer
        switch (user.getSexe()){
            case 1 :
                result.remove(12);
                break;
            default:
                result.remove(14);
                break;
        }
        return result;
    }

    private String checkValidMeasure(GarmentType garmentType){
        int topBottomShoe = SMXL.getGarmentTypeDBManager().getOrderByCategoryGarment(garmentType);
        switch (topBottomShoe){
            case 1 :
                if(user.getChest()==0.0) {
                    return getString(R.string.libChest);
                }
                break;
            case 2 :
                if(user.getHips() == 0.0 || user.getWaist() == 0.0) {
                    if (user.getHips() == 0.0 && user.getWaist() == 0.0) {
                        return getString(R.string.libHips) + "\n" + getString(R.string.libWaist);
                    } else if (user.getHips() == 0.0){
                        return getString(R.string.libHips);
                    } else {
                        return getString(R.string.libWaist);
                    }
                }
                break;
            case 3 :
                if(user.getFeet() <= 0.0 ) {
                    return getString(R.string.libFeet);
                }
                break;
            default:
                break;
        }
        if(garmentType.getType().equalsIgnoreCase("CHEMISE")){
            if(user.getCollar()==0.0) {
                return getString(R.string.libCollar);
            }
        }
        return "";
    }


}
