package com.aerolitec.SMXL.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.UserWishList;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.adapter.UserWishListAdapter;

import java.util.ArrayList;


public class UserWishListFragment extends ListFragment {



    //private OnFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserWishListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        // TODO: Change Adapter to display your content
        setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS));
   */
        ArrayList<UserWishList> arrayList = new ArrayList<>();
        GarmentType garmentType = SMXL.getGarmentTypeDBManager().getGarmentType(72);
        Brand brand = SMXL.getBrandDBManager().getBrand(23);
        arrayList.add(new UserWishList(1, UserManager.get().getUser(),"SMXL","XXS","test",garmentType,brand));
        arrayList.add(new UserWishList(2, UserManager.get().getUser(),"SMXL","XXS","test",garmentType,brand));
        arrayList.add(new UserWishList(3, UserManager.get().getUser(),"SMXL","XXS","test",garmentType,brand));
        arrayList.add(new UserWishList(4, UserManager.get().getUser(),"SMXL","XXS","test",garmentType,brand));
        setListAdapter(new UserWishListAdapter(getActivity(), R.layout.item_wishlist, arrayList));

    }

/*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }
*/
}
