package com.aerolitec.SMXL.ui.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.CategoryGarment;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.AddGarmentActivity;
import com.aerolitec.SMXL.ui.adapter.TypeGarmentAdapter;

import java.util.ArrayList;

/**
 * Created by Clément on 5/4/2015.
 */
public class SelectGarmentTypeFragment extends Fragment {
    private AddGarmentActivity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("onCreateView", inflater.toString());
        return inflater.inflate(R.layout.fragment_add_garment_type, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity=(AddGarmentActivity)getActivity();

        ListView listViewGarments = (ListView) view.findViewById(R.id.listViewGarments);

        ArrayList<GarmentType> garmentItems = getGarmentsFromCategory(activity.getAddGarmentFragment().getSelectedCategory());//C'est pas fou, changer dans le intent?
        if(garmentItems.size()==1){
            activity.getAddGarmentFragment().setSelectedGarmentType(garmentItems.get(0));
            activity.getAddGarmentFragment().getChildFragmentManager().beginTransaction()
                    .replace(R.id.containerAddGarmentFragment, new SelectGarmentBrandFragment(), "brand")
                    .commit();
        }



        TypeGarmentAdapter adapterGarments = new TypeGarmentAdapter(activity, R.layout.type_garment_item, garmentItems);
        listViewGarments.setAdapter(adapterGarments);

        listViewGarments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                activity.getAddGarmentFragment().setSelectedGarmentType((GarmentType) adapterView.getItemAtPosition(position));
                activity.getAddGarmentFragment().getChildFragmentManager().beginTransaction()
                        .replace(R.id.containerAddGarmentFragment, new SelectGarmentBrandFragment(), "brand")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.getAddGarmentFragment().setSelectedBrand(null);
        activity.getAddGarmentFragment().setSelectedGarmentType(null);
    }

    private ArrayList<GarmentType> getGarmentsFromCategory(CategoryGarment cg) {
        return SMXL.getGarmentTypeDBManager().getAllGarmentTypeByCategory(cg,activity.getUser().getSexe());
    }
}
