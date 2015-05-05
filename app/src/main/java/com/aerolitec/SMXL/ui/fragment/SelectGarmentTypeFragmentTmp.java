package com.aerolitec.SMXL.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.CategoryGarment;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.tmp;
import com.aerolitec.SMXL.ui.adapter.TypeGarmentAdapter;

import java.util.ArrayList;

/**
 * Created by Clément on 5/4/2015.
 */
public class SelectGarmentTypeFragmentTmp extends Fragment {
    private tmp activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_garment_type, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity=(tmp)getActivity();

        ListView listViewGarments = (ListView) view.findViewById(R.id.listViewGarments);

        ArrayList<GarmentType> garmentItems = getGarmentsFromCategory(activity.getSelectedCategory());//C'est pas fou, changer dans le intent?
        if(garmentItems.size()==1){
            activity.setSelectedGarmentType(garmentItems.get(0));
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new Fragment(), "brand")
                    .addToBackStack(null)
                    .commit();
        }



        TypeGarmentAdapter adapterGarments = new TypeGarmentAdapter(activity, R.layout.type_garment_item, garmentItems);
        listViewGarments.setAdapter(adapterGarments);

        listViewGarments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                activity.setSelectedGarmentType((GarmentType)adapterView.getItemAtPosition(position));
                Log.d("TestSelection", activity.getSelectedGarmentType().toString()+"");
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new Fragment(), "brand")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private ArrayList<GarmentType> getGarmentsFromCategory(CategoryGarment cg) {
        return SMXL.getGarmentTypeDBManager().getAllGarmentTypeByCategory(cg,activity.getUser().getSexe());
    }
}
